package com.team2.android.fusionapp;

import java.util.ArrayList;

public class TopArtist {

    private String mName;
    private String mFollowers;
    private ArrayList<String> mGenres;
    private String mPopularity;

    TopArtist(String name, String followers, ArrayList<String> genres, String popularity) {
        mName = name;
        mFollowers = followers;
        mGenres = genres;
        mPopularity = popularity;
    }

    public ArrayList<String> getGenres() {
        return mGenres;
    }

    @Override
    public String toString() {

        return String.format("Artist Name: %s, Follower Count: %s, Genres: %s, Popularity: %s \n \n",
                mName, mFollowers, mGenres, mPopularity);
    }

    public String getName() {
        return mName;
    }
}
