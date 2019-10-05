package com.team2.android.fusionapp;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class UserDataLab {

    private static UserDataLab sUserDataLab;

    private Profile mProfile;
    private ArrayList<TopArtist> mTopArtists = new ArrayList<>();
    private ArrayList<TopTrack> mTopTracks = new ArrayList<>();
    private HashMap<String, Integer> mTopGenres = new HashMap<String, Integer>();
    ValueComparator bvc = new ValueComparator(mTopGenres);
    TreeMap<String, Integer> mSortedTopGenres = new TreeMap<String, Integer>(bvc);

    public static UserDataLab get(Context context) {
        if (sUserDataLab == null) {
            sUserDataLab = new UserDataLab(context);
        }
        return sUserDataLab;
    }

    private UserDataLab(Context context) {
    }

    public void addProfile(String mAccessToken, AsyncResponse delegate) {
        mProfile = new Profile(mAccessToken, delegate);
    }

    public void addTopTrack(TopTrack topTrack) {
        mTopTracks.add(topTrack);
    }

    public void addTopArtist(TopArtist topArtist) {
        mTopArtists.add(topArtist);
    }

    public void addTopGenre(String genre, Integer number) {
        mTopGenres.put(genre, number);
    }

    public Profile getProfile() {
        return mProfile;
    }

    public ArrayList<TopArtist> getTopArtists() {
        return mTopArtists;
    }

    public ArrayList<TopTrack> getTopTracks() {
        return mTopTracks;
    }

    public HashMap<String, Integer> getTopGenres() {
        return mTopGenres;
    }

    public TreeMap<String, Integer> getSortedTopGenres() {
        return mSortedTopGenres;
    }

    public void sortTopGenres() {
        mSortedTopGenres.putAll(mTopGenres);

        /*---- TO print the sorted top genres use this: ------ //

        for(Map.Entry<String,Integer> entry : mSortedTopGenres.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            Log.d("DEBUG", String.format("Genre: %s, Weighting: %s \n \n", key, value));
        }
         */
    }
}
