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
import android.widget.TextView;

import com.example.covidtracker.CustomFirebaseMessagingService;
import com.example.covidtracker.R;
import com.google.gson.Gson;

import java.lang.reflect.Array;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        notifs =  sharedPreferences.getString(getString(R.string.notificationsStack), null);


        return inflater.inflate(R.layout.notifications_fragment, container, false);
    }
    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        // TODO: Use the ViewModel
    }
*/

    public /*ArrayList<NotificationModel>*/ void strToArray(String str/* , ArrayList<NotificationModel>*/){

    }
}
