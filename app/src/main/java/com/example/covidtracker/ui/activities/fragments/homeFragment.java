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
import android.widget.ImageView;
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
    TextView serviceTitle,date,status,tiltetopbar ;
    Switch switchService;
    Date now = new Date();
    View sympReminder,stayhome_alert;
    ImageButton add;
    ImageView icon;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        SharedPrefsHelper prefs;
        prefs = new SharedPrefsHelper(getContext());

        Date alsoNow = Calendar.getInstance().getTime();
        String nowAsString = new SimpleDateFormat("yyyy-MM-dd").format(now);
        icon = view.findViewById(R.id.statusIcon);
        date = view.findViewById(R.id.date_lastcheck);
        sympReminder = view.findViewById(R.id.symptoms_reminder);
        add = view.findViewById(R.id.add);
        tiltetopbar = view.findViewById(R.id.tiltetopbar);
        tiltetopbar.setText("Home");

        status = view.findViewById(R.id.status);


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

        status.setText("Aujourd'hui : " + nowAsString );

        if(nowAsString.equals(prefs.getSymptomsLastdate())){
            add.setVisibility(View.GONE);
            date.setText("Date du dernier log : " + prefs.getSymptomsLastdate());
            icon.setImageResource(R.drawable.ic_check_circle_outline_24px);
            icon.setColorFilter(getResources().getColor(R.color.smellOfSuccess));

        }else{
            add.setVisibility(View.VISIBLE);
            if(prefs.getSymptomsLastdate() == "null"){
                date.setText("Commencer votre log de symptoms");
            }else{
                date.setText("Date du dernier log :" + prefs.getSymptomsLastdate());
            }
            icon.setImageResource(R.drawable.ic_highlight_off_24px);
            icon.setColorFilter(getResources().getColor(R.color.failureStinks));

        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AddSymptomFragment()).commit();
            }
        });


        stayhome_alert = view.findViewById(R.id.stayhome_alert);

        if(prefs.getHealthStatus().equals("Contamined") ){
            stayhome_alert.setVisibility(View.VISIBLE);
        }else{
            stayhome_alert.setVisibility(View.GONE);
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
