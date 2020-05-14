package com.example.covidtracker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class cgu extends Fragment {

    private WebView mWebview ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_cgu, container, false);


        mWebview = rootview.findViewById(R.id.wv);

        mWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebview.getSettings().setJavaScriptEnabled(true);

        mWebview.loadUrl("https://firebasestorage.googleapis.com/v0/b/covid-tracer-274905.appspot.com/o/null.html?alt=media&token=8116f715-dc5f-4338-8be2-991bd97ab302");


        return rootview;
    }
}
