package com.example.covidtracker.ui.symptoms.symptoms_log;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.covidtracker.R;
import com.example.covidtracker.ui.symptoms.SymptomModel;

import java.util.ArrayList;

public class SymptomLogAdapter extends ArrayAdapter<SymptomLogModel> {

    private static final String TAG = "NotificationAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private ArrayList<SymptomLogModel> list ;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView date;
        TextView brief;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public SymptomLogAdapter(Context context, int resource, ArrayList<SymptomLogModel> objects) {
        super(context, resource, objects);
        mContext = context;
        list = objects;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //get the persons information


        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.brief = (TextView) convertView.findViewById(R.id.brief);

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


        String displaySymptoms = "";


            for (int i = 0 ; i < list.get(position).getSymptoms().size() ; i++){
                String str = list.get(position).getSymptoms().get(i);

                if(i!=list.get(position).getSymptoms().size() - 1)
                {
                    displaySymptoms = displaySymptoms + str + ", ";
                }else{
                    displaySymptoms = displaySymptoms + str + ".";
                }

            }

        holder.date.setText("Date du log : " + list.get(position).getDate());
        holder.brief.setText("Symptoms : " + displaySymptoms);
        return convertView;
    }


}
