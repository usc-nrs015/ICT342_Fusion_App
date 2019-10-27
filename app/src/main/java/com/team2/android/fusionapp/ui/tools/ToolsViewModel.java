package com.team2.android.fusionapp.ui.tools;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.team2.android.fusionapp.UserDataLab;

import java.util.Map;

public class ToolsViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private UserDataLab mUserDataLab = UserDataLab.get(getApplication().getApplicationContext());

    public ToolsViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        StringBuilder filteredText = new StringBuilder();
        filteredText.append("");
        int i = 0;
        for(Map.Entry<String,Integer> entry : mUserDataLab.getSortedTopGenres().entrySet()) {
            i += 1;
            if (i > 10) {
                break;
            }
            String key = entry.getKey();
            Integer value = entry.getValue();
            filteredText.append(i + ": " + key + "\n");
            //filteredText.append(String.format("Genre: %s, Weighting: %s \n", key, value));
        }
        mText.setValue(filteredText.toString());
    }

    public LiveData<String> getText() {
        return mText;
    }
}