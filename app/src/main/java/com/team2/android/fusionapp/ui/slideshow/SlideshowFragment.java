package com.team2.android.fusionapp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.team2.android.fusionapp.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.topTracksList);
        final WebView webView = root.findViewById(R.id.topTracksGraph);
        String webContent = "<html><iframe width='100%' height='100%' src='https://app.powerbi.com/view?r=eyJrIjoiMGNjNjY4MTktOTI3Ny00M2RkLWI4ZmMtMWZmNzY1OTEwNDgxIiwidCI6IjA3NzBkOTg4LTM3NTQtNGIxNi1iOGJiLTBkMzQyMDdjNTM3YiIsImMiOjEwfQ%3D%3D' frameborder='0' allowFullScreen='true'></iframe></html>";
        //webView.loadData(webContent,  "text/html", "UTF-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        webView.loadDataWithBaseURL("https://app.powerbi.com", webContent,"text/html","UTF-8",null);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}