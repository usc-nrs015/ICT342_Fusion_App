package com.team2.android.fusionapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Profile {

    private String mDisplayName;
    private String mUserName;

    public Profile(String accessToken, AsyncResponse delegate) {
        FetchUserData fetchData = new FetchUserData();
        fetchData.delegate = delegate;
        fetchData.execute(accessToken);
    }

    private class FetchUserData extends AsyncTask<String, Void, String> {
        public AsyncResponse delegate = null;

        @Override
        protected String doInBackground(String... params) {
            String accessToken = params[0];
            try {
                URL url = new URL("https://api.spotify.com/v1/me" + "?access_token=" + accessToken);
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
            Log.d("DEBUG", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                mDisplayName = jsonObject.getString("display_name");
                mUserName = jsonObject.getString("id");
                delegate.processFinish(mDisplayName, mUserName);
            }catch (JSONException err){
                Log.d("Error", err.toString());
            }
        }
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public String getUserName() {
        return mUserName;
    }

   public void setDisplayName(String displayName) {
       mDisplayName = displayName;
   }

    public void setUserName(String userName) {
        mUserName = userName;
    }
}
