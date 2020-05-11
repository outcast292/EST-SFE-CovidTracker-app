package com.example.covidtracker.ui.symptoms;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.covidtracker.R;
import com.example.covidtracker.SharedPrefsHelper;
import com.example.covidtracker.ui.activities.fragments.healthFragment;
import com.example.covidtracker.ui.activities.fragments.homeFragment;
import com.example.covidtracker.ui.notifications.NotificationAdapter;
import com.example.covidtracker.ui.notifications.NotificationModel;
import com.example.covidtracker.ui.symptoms.symptoms_log.SymptomLogModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class AddSymptomFragment extends Fragment {


    private String TAG = "AddSymptomFragment";

    SymptomAdapter mAdapter;
    android.widget.ListView ListView;
    ArrayList<SymptomModel> symptoms = new ArrayList<SymptomModel>();
    SymptomLogModel log;
    Button save,cancel;
    SharedPrefsHelper prefs;
    AlertDialog.Builder builder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_add_symptom, container, false);


        symptoms.add(new SymptomModel("0 ","Fièvre","Une température élevée supérieure à 37°C. Vous vous sentez trop chaud pour toucher sur votre poitrine ou le dos."));
        symptoms.add(new SymptomModel("1","Douleur abdominale","Douleur de l'intérieur de l'abdomen ou de la paroi musculaire externe, allant de légère et temporaire à sévère."));
        symptoms.add(new SymptomModel("2","Frissons","La sensation d'avoir froid; mais pas nécessairement dans un environnement froid, souvent accompagné de tremblements "));
        symptoms.add(new SymptomModel("3","Toux","Un son soudain et puissant pour évacuer l'air et éliminer une irritation de la gorge ou des voies respiratoires"));
        symptoms.add(new SymptomModel("4","Diarrhée","Selles molles et liquides qui peuvent survenir fréquemment avec un sentiment d'urgence"));
        symptoms.add(new SymptomModel("5","Difficulté à respirer","l'essoufflement, ou dyspnée, est une condition inconfortable qui rend difficile la pénétration complète de l'air dans les poumons"));
        symptoms.add(new SymptomModel("6","Migraine","Une sensation douloureuse dans n'importe quelle partie de la tête, allant de forte à terne, qui peut survenir avec d'autres symptômes"));
        symptoms.add(new SymptomModel("7","Gorge irritée","Douleur ou irritation de la gorge pouvant survenir avec ou sans déglutition, accompagne souvent les infections "));
        symptoms.add(new SymptomModel("8","Vomissement","Expulser avec force le contenu de l'estomac de la bouche"));

        ViewGroup footer = (ViewGroup)inflater.inflate(R.layout.symptoms_footer,ListView,false);

        mAdapter = new SymptomAdapter(getContext(), R.layout.symptom_check , symptoms);
        prefs = new SharedPrefsHelper(getContext());
        ListView  = rootview.findViewById(R.id.lvSympt);

        ListView.addFooterView(footer,null,false);

        ListView.setAdapter(mAdapter);

        save = footer.findViewById(R.id.save);
        cancel = footer.findViewById(R.id.cancel);

        mAdapter.notifyDataSetChanged();

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mAdapter.getLog() != null){

                    if(!mAdapter.getLog().getSymptoms().isEmpty() )
                    {
                        prefs.setSymptomsLog(mAdapter.getLog());
                        prefs.setSymptomsLastdate(mAdapter.getLog().getDate());
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new healthFragment()).commit();
                    }else{
                        showDialog("Erreur","Veuiller selectionner des symptoms out annuler.");
                    }
                }else{
                    showDialog("Erreur","Veuiller selectionner des symptoms out annuler.");

                }
            
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new healthFragment()).commit();
            }
        });


        return rootview;
    }

    public void showDialog(String title, String message){

        builder = new AlertDialog.Builder(getContext());

        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle(title);
        alert.show();
    }
}
