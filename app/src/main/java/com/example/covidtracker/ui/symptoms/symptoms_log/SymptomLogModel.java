package com.example.covidtracker.ui.symptoms.symptoms_log;

import java.util.ArrayList;

public class SymptomLogModel {

    String date;
    private ArrayList<String> symptoms;

    public SymptomLogModel(String date, ArrayList<String> symptoms) {
        this.date = date;
        this.symptoms = symptoms;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSymptoms(ArrayList<String> symptoms) {
        this.symptoms = symptoms;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<String> getSymptoms() {
        return symptoms;
    }

    @Override
    public String toString() {
        return "SymptomLogModel{" +
                "date='" + date + '\'' +
                ", symptoms=" + symptoms +
                '}';
    }
}
