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
import android.widget.Switch;
import android.widget.TextView;

import com.example.covidtracker.R;
import com.example.covidtracker.services.NearbyTrackingService;


public class homeFragment extends Fragment {
    private String serviceStatus;
    CardView serviceState ;
    private static final String TAG = "homeFragment" ;
    TextView serviceTitle ;
    Switch switchService;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
               // mMessageReceiver, new IntentFilter("NearbyTrackingService"));


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
