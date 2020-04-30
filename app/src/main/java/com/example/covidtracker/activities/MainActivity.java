package com.example.covidtracker.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.covidtracker.R;
import com.example.covidtracker.Utils;

public class MainActivity extends AppCompatActivity {

    CheckBox termscb;
    boolean printedEtoiles = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        termscb = findViewById(R.id.check_box);


    }


    public void inscrireBtn(View v){

        if(termscb.isChecked()){
            Intent intent = new Intent(this, registerActivity.class);
            startActivity(intent);
            Utils.checkPermission(this);
        }else{

            if(printedEtoiles = false){
                printedEtoiles = true;
                termscb.setText(termscb.getText() + "**");
            }

        }
    }

    public void openAgreements(View v){
        new AlertDialog.Builder(this)
                .setTitle("Termes et conditions")
                .setMessage(getString(R.string.conditions));

    }

}
