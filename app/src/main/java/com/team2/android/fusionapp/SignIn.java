package com.team2.android.fusionapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class SignIn extends AppCompatActivity implements AsyncResponse {

    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "0281ed7b2a594e66a3b4a6c545b09000";
    private static final String REDIRECT_URI = "http://localhost/";
    private static String mAccessToken;
    private ProgressDialog dialog;

    Button mSignIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        mSignIn = findViewById(R.id.signIn);
        mSignIn.setOnClickListener(view -> {
            runLogin();
        });

    }

    private void runLogin() {
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"user-top-read"});
        AuthenticationRequest request = builder.build();

        Log.d("DEBUG", "runLogin");
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    private void setUpProfile() {
        UserDataLab.get(this).addProfile(mAccessToken, this);
    }


    @Override
    public void processFinish(String displayname, String username) {

        // Send token to Python web app and retrieve json data
        new CallPythonHeroku(this).execute(mAccessToken);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("DEBUG", "onResult");
        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    Log.d("DEBUG", "HERE");
                    Log.d("DEBUG", response.getAccessToken());
                    Log.d("DEBUG", Integer.toString(response.getExpiresIn()));
                    mAccessToken = response.getAccessToken();
                    setUpProfile();
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Log.d("DEBUG", response.getError());
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }

    private void extractData(JSONObject json) {
        try {
            JSONArray topArtists = json.getJSONObject("top_artists").getJSONArray("items");
            for (int i = 0; i < topArtists.length(); i++) {
                JSONObject item = topArtists.getJSONObject(i);
                String name = item.getString("name");
                String followers = item.getJSONObject("followers").getString("total");
                JSONArray genresJSON = item.getJSONArray("genres");
                ArrayList<String> genres = new ArrayList<>();
                for (int j = 0; j < genresJSON.length(); j++) {
                    genres.add(genresJSON.getString(j));
                }
                String popularity = item.getString("popularity");

                TopArtist newArtist = new TopArtist(name, followers, genres, popularity);
                UserDataLab.get(this).addTopArtist(newArtist);
            }

            JSONArray topTracks = json.getJSONObject("top_tracks").getJSONArray("items");
            for (int i = 0; i < topTracks.length(); i++) {
                JSONObject item = topTracks.getJSONObject(i);

                String name = item.getString("name");

                JSONArray artists = item.getJSONArray("artists");
                ArrayList<String> artistNames = new ArrayList<>();
                for (int j = 0; j < artists.length(); j++) {
                    JSONObject artistEntry = artists.getJSONObject(j);
                    artistNames.add(artistEntry.getString("name"));
                }

                JSONObject album = item.getJSONObject("album");
                String releaseDate = album.getString("release_date");

                String popularity = item.getString("popularity");

                String duration = item.getString("duration_ms");

                TopTrack newTrack = new TopTrack(name, artistNames, releaseDate, popularity, duration);
                UserDataLab.get(this).addTopTrack(newTrack);

            }

            // Now calculate most popular genres
            for (int i = 0; i < UserDataLab.get(this).getTopArtists().size(); i++) {
                ArrayList<String> genres = UserDataLab.get(this).getTopArtists().get(i).getGenres();
                for (int j = 0; j < genres.size(); j++) {
                    String genre = genres.get(j);
                    if (UserDataLab.get(this).getTopGenres().containsKey(genre)) {
                        Integer newNumber = UserDataLab.get(this).getTopGenres().get(genre) + 1;
                        UserDataLab.get(this).getTopGenres().remove(genre);
                        UserDataLab.get(this).addTopGenre(genre, newNumber);
                    } else {
                        UserDataLab.get(this).addTopGenre(genre, 1);
                    }
                }
            }
            UserDataLab.get(this).sortTopGenres();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class CallPythonHeroku extends AsyncTask<String, Void, String> {

        private Context mContext;

        public CallPythonHeroku (Context context){
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("Retrieving Spotify Data");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String accessToken = params[0];
            try {
                URL url = new URL("http://spotify-me-app.herokuapp.com/python" + "?token=" + accessToken);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } catch (IOException ioe) {
                Log.d("Error", ioe.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                Log.d("DEBUG", jsonObject.toString());
                extractData(jsonObject);
                Log.d("DEBUG", UserDataLab.get(mContext).getTopArtists().toString());
                Log.d("DEBUG", UserDataLab.get(mContext).getTopTracks().toString());
                Log.d("DEBUG", UserDataLab.get(mContext).getTopGenres().toString());
                dialog.dismiss();
                Intent myIntent = new Intent(SignIn.this, MainActivity.class);
                startActivity(myIntent);
            }catch (JSONException err){
                Log.d("Error", err.toString());
            }
        }
    }

}
