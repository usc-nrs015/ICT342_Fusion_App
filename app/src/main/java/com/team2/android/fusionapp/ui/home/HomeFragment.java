package com.team2.android.fusionapp.ui.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.team2.android.fusionapp.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textViewArtist = root.findViewById(R.id.topArtistName);
        final TextView textViewTrack = root.findViewById(R.id.topTrackName);
        final TextView textViewGenre = root.findViewById(R.id.topGenreName);
        homeViewModel.getText().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(@Nullable ArrayList<String> s) {
                textViewArtist.setText(s.get(0));
                textViewTrack.setText(s.get(1));
                textViewGenre.setText(s.get(2));
            }
        });
        return root;
    }
}