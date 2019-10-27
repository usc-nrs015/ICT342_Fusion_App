package com.team2.android.fusionapp.ui.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.team2.android.fusionapp.TopArtist;
import com.team2.android.fusionapp.TopTrack;
import com.team2.android.fusionapp.UserDataLab;

import java.util.ArrayList;
import java.util.Map;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<String>> mText;
    private UserDataLab mUserDataLab = UserDataLab.get(getApplication().getApplicationContext());

    public HomeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        /*mText.setValue("This is home fragment");

        StringBuilder filteredText = new StringBuilder();

        filteredText.append("TOP ARTISTS:\n");
        for (int i = 0; i < mUserDataLab.getTopArtists().size(); i++) {
            TopArtist topArtist = mUserDataLab.getTopArtists().get(i);
            filteredText.append(topArtist.getName());
            filteredText.append(", ");
        }

        filteredText.append("\n\nTOP TRACKS:\n");
        for (int i = 0; i < mUserDataLab.getTopTracks().size(); i++) {
            TopTrack topTrack = mUserDataLab.getTopTracks().get(i);
            filteredText.append(topTrack.getName());
            filteredText.append(", ");
        }

        filteredText.append("\n\nTOP GENRES:\n");
        for(Map.Entry<String,Integer> entry : mUserDataLab.getSortedTopGenres().entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            filteredText.append(String.format("Genre: %s, Weighting: %s \n", key, value));
        }

        mText.setValue(filteredText.toString());*/
        ArrayList<String> temp = new ArrayList<String>();
        temp.add(mUserDataLab.getTopArtists().get(0).getName());
        temp.add(mUserDataLab.getTopTracks().get(0).getName());
        temp.add(mUserDataLab.getSortedTopGenres().firstKey());
        mText.setValue(temp);
    }

    public LiveData<ArrayList<String>> getText() {
        return mText;
    }
}