package com.example.covidtracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covidtracker.R;
import com.example.covidtracker.SharedPrefsHelper;
import com.example.covidtracker.ui.activities.LoggedInActivity;
import com.example.covidtracker.ui.activities.MainActivity;

public class HomeScreen extends AppCompatActivity {
    SharedPrefsHelper prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        prefs = new SharedPrefsHelper(getApplicationContext());

        Handler handler;
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                if (prefs.isRegistred()) {
                    intent = new Intent(HomeScreen.this, LoggedInActivity.class);
                } else {
                    intent = new Intent(HomeScreen.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },1000);




    }



}
