package com.example.covidtracker.ui.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.covidtracker.R;
import com.example.covidtracker.cgu;
import com.example.covidtracker.ui.symptoms.AddSymptomFragment;


public class settingsFragment extends Fragment {

    LinearLayout cgu;
    TextView tiltetopbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        tiltetopbar = view.findViewById(R.id.tiltetopbar);
        tiltetopbar.setText("Settings");
        cgu = view.findViewById(R.id.cgu);

        cgu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new cgu()).commit();
            }
        });


        return view;
    }

}
