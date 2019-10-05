package com.team2.android.fusionapp;

import java.util.ArrayList;

public class TopTrack {

    private String mName;
    private ArrayList<String> mArtistNames;
    private String mReleaseDate;
    private String mPopularity;
    private String mDuration;

    TopTrack(String name, ArrayList<String> artistNames, String releaseDate, String popularity, String duration) {
        mName = name;
        mArtistNames = artistNames;
        mReleaseDate = releaseDate;
        mPopularity = popularity;
        mDuration = duration;
    }

    @Override
    public String toString() {

        return String.format("Track Name: %s, Artist Name: %s, Release Date: %s, Popularity: %s \n \n",
                mName, mArtistNames, mReleaseDate, mPopularity, mDuration);

        //return mName + " " + mArtistNames + " " + mReleaseDate + " " + mPopularity + " " + mDuration;
    }

    public String getName() {
        return mName;
    }
}
