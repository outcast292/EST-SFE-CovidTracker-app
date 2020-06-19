package com.example.covidtracker.ui.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidtracker.R;
import com.example.covidtracker.SharedPrefsHelper;
import com.example.covidtracker.dbhelpers.FirebaseDatabaseHelper;
import com.example.covidtracker.ui.symptoms.AddSymptomFragment;
import com.example.covidtracker.ui.symptoms.symptoms_log.SymptomLogAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class healthFragment extends Fragment {
    private static final String TAG = "healthFragment" ;
    ImageButton add;
    ImageView icon,noLog;
    TextView date,status,noLogText,tiltetopbar;
    SharedPrefsHelper prefs;
    ListView lv;
    SymptomLogAdapter mAdapter;
    Button report,fragmentSympt,fragmentDiag;

    Date now = new Date();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_health, container, false);


        prefs = new SharedPrefsHelper(getContext());

        add = rootview.findViewById(R.id.add);
        status = rootview.findViewById(R.id.status);
        date = rootview.findViewById(R.id.date_lastcheck);
        icon = rootview.findViewById(R.id.statusIcon);
        lv = rootview.findViewById(R.id.lisstview);
        fragmentSympt = rootview.findViewById(R.id.fragmentSympt);
        fragmentDiag = rootview.findViewById(R.id.fragmentDiag);
        tiltetopbar = rootview.findViewById(R.id.tiltetopbar);
        tiltetopbar.setText("Health");

        Date alsoNow = Calendar.getInstance().getTime();
        String nowAsString = new SimpleDateFormat("yyyy-MM-dd").format(now);

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

        if(prefs.getSymptomsLog() != null && prefs.getSymptomsLog().size() > 0){
            mAdapter = new SymptomLogAdapter(getContext(), R.layout.symptom_log_layout , prefs.getSymptomsLog());
            lv.setAdapter(mAdapter);
            lv.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();

        }else{
            Log.d(TAG, "emptyy");
            noLog = rootview.findViewById(R.id.noLog);
            noLogText = rootview.findViewById(R.id.noLogText);
            noLog.setVisibility(View.VISIBLE);
            noLogText.setVisibility(View.VISIBLE);
        }


        add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AddSymptomFragment()).commit();
           }
       });

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



        return rootview;
    }
}
