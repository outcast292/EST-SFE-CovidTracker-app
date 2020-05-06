package com.example.covidtracker.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.covidtracker.R;
import com.example.covidtracker.Utils;

public class MainActivity extends AppCompatActivity {

    CheckBox termscb;
    boolean printedEtoiles = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        termscb = findViewById(R.id.check_box);


    }


    public void inscrireBtn(View v){
       Intent intent = new Intent(this, registerActivity.class);
       startActivity(intent);
       Utils.checkPermission(this);
    }


}
