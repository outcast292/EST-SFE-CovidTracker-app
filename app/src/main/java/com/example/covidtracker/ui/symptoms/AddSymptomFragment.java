package com.example.covidtracker.ui.symptoms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.covidtracker.R;
import com.example.covidtracker.SharedPrefsHelper;
import com.example.covidtracker.ui.notifications.NotificationAdapter;
import com.example.covidtracker.ui.notifications.NotificationModel;
import com.example.covidtracker.ui.symptoms.symptoms_log.SymptomLogModel;

import java.util.ArrayList;
import java.util.HashMap;

public class AddSymptomFragment extends Fragment {


    private String TAG = "AddSymptomFragment";

    SymptomAdapter mAdapter;
    android.widget.ListView ListView;
    ArrayList<SymptomModel> symptoms = new ArrayList<SymptomModel>();
    SymptomLogModel log;
    Button save;
    SharedPrefsHelper prefs;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_add_symptom, container, false);


        symptoms.add(new SymptomModel("0 ","Fièvre ","qsfqsfsfd"));
        symptoms.add(new SymptomModel("1","Douleur abdominale ","Douleur de l'intérieur de l'abdomen ou de la paroi musculaire externe, allant de légère et temporaire à sévère."));
        symptoms.add(new SymptomModel("2","Frissons ","La sensation d'avoir froid; mais pas nécessairement dans un environnement froid, souvent accompagné de tremblements "));
        symptoms.add(new SymptomModel("3","Toux ","Un son soudain et puissant pour évacuer l'air et éliminer une irritation de la gorge ou des voies respiratoires"));
        symptoms.add(new SymptomModel("4","Diarrhée ","Selles molles et liquides qui peuvent survenir fréquemment avec un sentiment d'urgence"));
        symptoms.add(new SymptomModel("5","Difficulté à respirer","l'essoufflement, ou dyspnée, est une condition inconfortable qui rend difficile la pénétration complète de l'air dans les poumons"));
        symptoms.add(new SymptomModel("6","Migraine ","Une sensation douloureuse dans n'importe quelle partie de la tête, allant de forte à terne, qui peut survenir avec d'autres symptômes"));
        symptoms.add(new SymptomModel("7","Gorge irritée","Douleur ou irritation de la gorge pouvant survenir avec ou sans déglutition, accompagne souvent les infections "));
        symptoms.add(new SymptomModel("8","Vomissement ","Expulser avec force le contenu de l'estomac de la bouche"));

        mAdapter = new SymptomAdapter(getContext(), R.layout.symptom_check , symptoms);
        prefs = new SharedPrefsHelper(getContext());
        ListView  = rootview.findViewById(R.id.lvSympt);
        ListView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        save = rootview.findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                prefs.setSymptomsLog(mAdapter.getLog());
                prefs.setSymptomsLastdate(mAdapter.getLog().getDate());

            }
        });

        return rootview;
    }
}
