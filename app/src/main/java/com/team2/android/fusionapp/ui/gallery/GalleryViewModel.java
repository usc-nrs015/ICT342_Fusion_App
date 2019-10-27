package com.team2.android.fusionapp.ui.gallery;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.team2.android.fusionapp.TopArtist;
import com.team2.android.fusionapp.UserDataLab;

import java.util.ArrayList;

public class GalleryViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private UserDataLab mUserDataLab = UserDataLab.get(getApplication().getApplicationContext());

    public GalleryViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        StringBuilder filteredText = new StringBuilder();
        filteredText.append("");
        for (int i = 0; i < mUserDataLab.getTopArtists().size(); i++) {
            TopArtist topArtist = mUserDataLab.getTopArtists().get(i);
            filteredText.append(i+1 + ": ");
            filteredText.append(topArtist.getName());
            filteredText.append("\n");
        }
        mText.setValue(filteredText.toString());
    }

    public LiveData<String> getText() {
        return mText;
    }
}