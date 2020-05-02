package com.example.covidtracker.ui.activities.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.covidtracker.R;
import com.example.covidtracker.ui.notifications.NotificationsFragment;

public class notificationsFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, NotificationsFragment.newInstance())
                    .commitNow();
        }
    }
}
