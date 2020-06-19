package com.example.covidtracker.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidtracker.SharedPrefsHelper;
import com.example.covidtracker.ui.activities.fragments.healthFragment;
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
    SharedPrefsHelper prefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        final Intent serviceIntent = new Intent(getApplication(), NearbyTrackingService.class);
        final Intent FCMIntent = new Intent(getApplication(), CustomFirebaseMessagingService.class);
        SharedPrefsHelper prefs = new SharedPrefsHelper(getApplicationContext());


        final String strUserUID = prefs.getDeviceUUID();
        final String token = prefs.getDeviceToken();

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
        FirebaseDatabaseHelper.getInstance().getStatus(strUserUID, getApplicationContext());

        startService(FCMIntent);
        startService(serviceIntent);

        if(isMyServiceRunning(NearbyTrackingService.class)){
            Log.d("Service", "Service isnt running ");
            startService(serviceIntent);
        }
        else{
            Log.d("Service", "Service  running ");


        }


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
                        case R.id.nav_health:
                            selectedFrag = new healthFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectedFrag).commit();

                    return true;
                }
            };
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
