package com.team2.android.fusionapp.ui.slideshow;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.team2.android.fusionapp.TopTrack;
import com.team2.android.fusionapp.UserDataLab;

public class SlideshowViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private UserDataLab mUserDataLab = UserDataLab.get(getApplication().getApplicationContext());

    public SlideshowViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        StringBuilder filteredText = new StringBuilder();
        filteredText.append("");
        for (int i = 0; i < mUserDataLab.getTopTracks().size(); i++) {
            TopTrack topTrack = mUserDataLab.getTopTracks().get(i);
            filteredText.append(i+1 + ": ");
            filteredText.append(topTrack.getName());
            filteredText.append("\n");
        }
        mText.setValue(filteredText.toString());
    }

    public LiveData<String> getText() {
        return mText;
    }
}