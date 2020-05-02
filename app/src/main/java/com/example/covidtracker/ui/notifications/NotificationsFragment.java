package com.example.covidtracker.ui.notifications;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covidtracker.R;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel mViewModel;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    TextView message,noNotifs;
    String notifs;
    ArrayList<NotificationModel> notifications = new ArrayList<NotificationModel>();
    Gson g = new Gson();
    String TAG = "notifFrag";
    NotificationAdapter mAdapter;
    android.widget.ListView ListView;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.notifications_fragment, container, false);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        ArrayList<NotificationModel> list = new ArrayList<NotificationModel>();

        list = getNotifications();

        if (list != null && list.size() > 0) {
            mAdapter = new NotificationAdapter(getContext(), R.layout.notification_layout ,list);

            ListView  = rootview.findViewById(R.id.ListView);
            ListView.setVisibility(View.VISIBLE);
            ListView.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();
        }else{
            Log.d(TAG, "emptyy");
            imageView = rootview.findViewById(R.id.imageView);
            noNotifs = rootview.findViewById(R.id.noNotifs);
            imageView.setVisibility(View.VISIBLE);
            noNotifs.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "onCreateView: list" + list);
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
