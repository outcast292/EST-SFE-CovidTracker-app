package com.example.covidtracker.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.covidtracker.R;
import com.example.covidtracker.Utils;

public class conditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditions);
    }

    public void inscrireBtn(View v){
        Intent intent = new Intent(this, registerActivity.class);
        startActivity(intent);
        Utils.checkPermission(this);
    }

}
