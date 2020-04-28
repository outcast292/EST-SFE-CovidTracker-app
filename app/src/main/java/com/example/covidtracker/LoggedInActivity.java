package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.covidtracker.dbhelpers.FirebaseDatabaseHelper;

public class LoggedInActivity extends AppCompatActivity {

    TextView uid;
    private String myUserUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        Intent serviceIntent = new Intent(getApplicationContext(), NearbyTrackingService.class);
        startService(serviceIntent);




    }

    public void meetbtn(View v){

        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        myUserUID = prefs.getString(getString(R.string.UID), "None");

        Log.e("TAG", "" + myUserUID );


    }
}
