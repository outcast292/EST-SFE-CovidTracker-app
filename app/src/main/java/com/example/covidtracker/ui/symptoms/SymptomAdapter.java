package com.example.covidtracker.ui.symptoms;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.covidtracker.R;
import com.example.covidtracker.ui.notifications.NotificationModel;
import com.example.covidtracker.ui.symptoms.symptoms_log.SymptomLogModel;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SymptomAdapter extends ArrayAdapter<SymptomModel> {

    private static final String TAG = "NotificationAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private ArrayList<SymptomModel> list ;
    private ArrayList<SymptomLogModel> logModel = new ArrayList<SymptomLogModel>(0) ;
    Button clear;

    Date now = new Date();


    private ArrayList<String> chosen = new ArrayList<String>(0) ;
    SymptomLogModel log;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView desc;
        CheckBox cb;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public SymptomAdapter(Context context, int resource, ArrayList<SymptomModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        list = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //get the persons information
        final String id = getItem(position).getId();
        String symName = getItem(position).getName();
        String symDesc = getItem(position).getDescription();

        //Create the person object with the information
        SymptomModel sym = new SymptomModel(id,symName,symDesc);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.desc = (TextView) convertView.findViewById(R.id.desc);
            holder.cb = convertView.findViewById(R.id.cbSym);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

/*
        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;
*/

        holder.name.setText(symName);
        holder.desc.setText(symDesc);
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if(b){
                    chosen.add(list.get(position).getName());

                }else{
                    chosen.remove(list.get(position).getName());
                }

                Log.d(TAG, "onCheckedChanged: chosen " + chosen.toString());

                Date alsoNow = Calendar.getInstance().getTime();
                String nowAsString = new SimpleDateFormat("yyyy-MM-dd").format(now);



                log = new SymptomLogModel(nowAsString, chosen);
                Log.d(TAG, "onCheckedChanged: " + log.toString());


            }
        });

        return convertView;
    }


    public SymptomLogModel getLog() {
        return log;
    }




}
