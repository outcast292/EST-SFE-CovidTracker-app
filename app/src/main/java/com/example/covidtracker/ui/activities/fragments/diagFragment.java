package com.example.covidtracker.ui.activities.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covidtracker.R;
import com.example.covidtracker.SharedPrefsHelper;
import com.example.covidtracker.cgu;
import com.example.covidtracker.dbhelpers.FirebaseDatabaseHelper;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class diagFragment extends Fragment {

    Button report,fragmentSympt,fragmentDiag;
    SharedPrefsHelper prefs;
    TextView tiltetopbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.diagfragment, container, false);

        prefs = new SharedPrefsHelper(getContext());

        fragmentSympt = view.findViewById(R.id.fragmentSympt);
        fragmentDiag = view.findViewById(R.id.fragmentDiag);
        report = view.findViewById(R.id.selfRep);
        tiltetopbar = view.findViewById(R.id.tiltetopbar);

        tiltetopbar.setText("Diagnostique");



        fragmentSympt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new healthFragment()).commit();
            }
        });

        fragmentDiag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new diagFragment()).commit();
            }
        });



        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabaseHelper.getInstance().updateUser(prefs.getDeviceUUID(), getContext(), new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void Success() {
                        Log.d(TAG, "updated user");
                    }

                    @Override
                    public void Fail() {
                        Log.d(TAG, "Failed updating user");

                    }
                });


            }
        });



        return view;
    }



}
