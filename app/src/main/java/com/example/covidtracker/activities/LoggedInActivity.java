package com.example.covidtracker.activities;

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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidtracker.CustomFirebaseMessagingService;
import com.example.covidtracker.NearbyTrackingService;
import com.example.covidtracker.R;
import com.example.covidtracker.dbhelpers.FirebaseDatabaseHelper;
import com.example.covidtracker.homeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
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
        serviceSwitch = findViewById(R.id.switchService);
        if (serviceSwitch.isChecked()){
            startService(serviceIntent);
        }
        serviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    startService(serviceIntent);
                }else{
                    stopService(serviceIntent);
                }
            }
        });

        //startService(serviceIntent);


        BottomNavigationView botnav = findViewById(R.id.bottom_navigation);
        botnav.setOnNavigationItemSelectedListener(navListener);

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

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectedFrag).commit();

                    return true;
                }
            };

}
