package com.example.covidtracker.ui.notifications;

import androidx.lifecycle.ViewModelProviders;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.covidtracker.CustomFirebaseMessagingService;
import com.example.covidtracker.R;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel mViewModel;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    TextView message;
    String notifs;
    ArrayList<NotificationModel> notifications = new ArrayList<NotificationModel>();
    Gson g = new Gson();
    String TAG = "notifFrag";
    NotificationAdapter mAdapter;
    android.widget.ListView ListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.notifications_fragment, container, false);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

<<<<<<< HEAD
        ArrayList<NotificationModel> list = new ArrayList<NotificationModel>(0);
=======
        ArrayList<NotificationModel> list = new ArrayList<NotificationModel>();
>>>>>>> c87614dd015ec275bb03bf107cd79a9926d8c7b7

        list = getNotifications();
        Log.d(TAG, "onCreateView: list" + list);

        mAdapter = new NotificationAdapter(getContext(), R.layout.notification_layout ,list);
<<<<<<< HEAD
        ListView  = rootview.findViewById(R.id.ListView);
=======

        ListView  = rootview.findViewById(R.id.ListView);

>>>>>>> c87614dd015ec275bb03bf107cd79a9926d8c7b7
        ListView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

        return rootview;
    }
    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        // TODO: Use the ViewModel
    }
*/


    public ArrayList<NotificationModel> getNotifications() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(getString(R.string.notificationsStack), "");
        Type type = new TypeToken<ArrayList<NotificationModel>>() {}.getType();
        ArrayList<NotificationModel> arrayList = gson.fromJson(json, type);

        return  arrayList;
    }

}
