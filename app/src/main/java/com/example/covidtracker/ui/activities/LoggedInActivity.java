package com.example.covidtracker.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidtracker.services.CustomFirebaseMessagingService;
import com.example.covidtracker.services.NearbyTrackingService;
import com.example.covidtracker.R;
import com.example.covidtracker.dbhelpers.FirebaseDatabaseHelper;
import com.example.covidtracker.ui.activities.fragments.homeFragment;
import com.example.covidtracker.ui.activities.fragments.settingsFragment;
import com.example.covidtracker.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoggedInActivity extends AppCompatActivity {

    TextView uid;
    private String myUserUID;
    Switch serviceSwitch ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        final Intent serviceIntent = new Intent(getApplication(), NearbyTrackingService.class);
        final Intent FCMIntent = new Intent(getApplication(), CustomFirebaseMessagingService.class);


        startService(FCMIntent);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final String strUserUID = sharedPreferences.getString(getString(R.string.UID), "None");
        String token = sharedPreferences.getString(getString(R.string.token), "None");

        Log.d("TAG", "uid: " + strUserUID);
        Log.d("TAG", "token: " + token);

        FirebaseDatabaseHelper.getInstance().updateDeviceToken(strUserUID, token, new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void Success() {
                Log.d("TAG", "Updated");
            }

            @Override
            public void Fail() {
                Log.d("TAG", "Failed");
            }
        });

        startService(serviceIntent);


        BottomNavigationView botnav = findViewById(R.id.bottom_navigation);
        botnav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new homeFragment()).commit();
    }

    public void showSymptoms(View v){

        Toast.makeText(this, "page des symptoms", Toast.LENGTH_LONG).show();
    }

    public void sendRapport(View v){
        Toast.makeText(this, "Nous avons recu votre message!", Toast.LENGTH_LONG).show();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFrag = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFrag = new homeFragment();
                            break;
                        case R.id.nav_notifs:
                            selectedFrag = new NotificationsFragment();
                            break;
                        case R.id.nav_settings:
                            selectedFrag = new settingsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectedFrag).commit();

                    return true;
                }
            };

}
