package com.example.covidtracker.ui.notifications;

import android.app.Notification;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class NotificationAdapter extends BaseAdapter {

    private List<NotificationModel> mObjects;
    private Context mContext;

    /**
     * Create a constructor that takes a List
     * of some Objects to use as the Adapter's
     * data
     */
    public NotificationAdapter(Context context, List<NotificationModel> objects) {
        mObjects = objects;
        mContext = context;
    }



    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
