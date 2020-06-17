package com.example.covidtracker.ui.activities.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.covidtracker.R;
import com.example.covidtracker.SharedPrefsHelper;
import com.example.covidtracker.services.NearbyTrackingService;
import com.example.covidtracker.ui.symptoms.AddSymptomFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class homeFragment extends Fragment {
    private String serviceStatus;
    CardView serviceState ;
    private static final String TAG = "homeFragment" ;
    TextView serviceTitle,date ;
    Switch switchService;
    Date now = new Date();
    View sympReminder;
    ImageButton add;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        SharedPrefsHelper prefs;
        prefs = new SharedPrefsHelper(getContext());

        Date alsoNow = Calendar.getInstance().getTime();
        String nowAsString = new SimpleDateFormat("yyyy-MM-dd").format(now);

//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
               // mMessageReceiver, new IntentFilter("NearbyTrackingService"));
/*

        final Intent serviceIntent = new Intent(getActivity(), NearbyTrackingService.class);

        serviceTitle = view.findViewById(R.id.serviceTitle);
        switchService = view.findViewById(R.id.switchService);

        switchService.setChecked(true);

        if (switchService.isChecked()){
            getActivity().startService(serviceIntent);
            updateUI(switchService.isChecked());
        }
        switchService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    getActivity().startService(serviceIntent);
                }else{
                    getActivity().stopService(serviceIntent);
                }

                updateUI(b);
            }
        });


*/

        if(!nowAsString.equals(prefs.getSymptomsLastdate())){
            date = view.findViewById(R.id.date_lastcheck);
            sympReminder = view.findViewById(R.id.symptoms_reminder);
            add = view.findViewById(R.id.add);
            sympReminder.setVisibility(View.VISIBLE);
            if(prefs.getSymptomsLastdate() == "null"){
                date.setText("Commencer votre log de symptoms");
            }else{
                date.setText("Date du dernier log :" + prefs.getSymptomsLastdate());
            }

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AddSymptomFragment()).commit();
                }
            });
        }

        return view;
    }


    public void updateUI(boolean serviceStatus){
        if (serviceStatus){
            serviceTitle.setText("Découverte active");
        }else{
            serviceTitle.setText("Découverte inactive");

        }
    }

}
