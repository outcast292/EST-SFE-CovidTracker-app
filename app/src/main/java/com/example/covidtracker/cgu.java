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

        mWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        mWebview.getSettings().setJavaScriptEnabled(true);

        mWebview.loadUrl("https://covid-tracer-274905.web.app/index.html");


        return rootview;
    }
}
