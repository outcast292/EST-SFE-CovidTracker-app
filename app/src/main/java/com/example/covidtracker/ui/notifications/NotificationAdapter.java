package com.example.covidtracker.ui.notifications;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.covidtracker.R;
import com.example.covidtracker.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationAdapter extends ArrayAdapter<NotificationModel> {

    private static final String TAG = "NotificationAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private ArrayList<NotificationModel> list ;
    SharedPreferences sharedPrefs;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView title;
        TextView body;
        ImageButton close;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public NotificationAdapter(Context context, int resource, ArrayList<NotificationModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        this.list = objects;
        this.sharedPrefs = context.getSharedPreferences("com.example.covidtracker", Context.MODE_PRIVATE);

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //get the persons information
        String id = getItem(position).getId();
        String notifType = getItem(position).getNotifType();

        //Create the person object with the information
        NotificationModel notif = new NotificationModel(id,notifType);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.body = (TextView) convertView.findViewById(R.id.body);
            holder.close = convertView.findViewById(R.id.close);

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

        if(notifType.equals("Contamined")){
            holder.title.setText("title contamine");
            holder.body.setText("body contamine");
        }

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
                updateNotification();
            }
        });

        return convertView;
    }

    public void updateNotification() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(list);
        editor.putString("MYCHANNEL", json);
        editor.commit();
    }
}
