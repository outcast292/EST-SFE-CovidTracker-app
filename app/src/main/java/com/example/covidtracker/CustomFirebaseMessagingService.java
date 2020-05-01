package com.example.covidtracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.ArrayMap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.covidtracker.R;
import com.example.covidtracker.activities.LoggedInActivity;
import com.example.covidtracker.dbhelpers.FirebaseDatabaseHelper;
import com.example.covidtracker.ui.notifications.NotificationModel;
import com.google.common.reflect.TypeToken;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.util.Map;

import static com.example.covidtracker.Utils.readFromStorage;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "CustomFMService";
    private ArrayList<NotificationModel> notifs = new ArrayList<NotificationModel>(0); ;



    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "newToken : " + s);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        final String strUserUID = sharedPreferences.getString(getString(R.string.UID), "None");
        String token = sharedPreferences.getString(getString(R.string.token), "None");


        FirebaseDatabaseHelper.getInstance().updateDeviceToken(strUserUID, token, new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void Success() {
                Log.d(TAG, "Updated");
            }

            @Override
            public void Fail() {
                Log.d(TAG, "Failed");
            }
        });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getData());

        addNotification(remoteMessage.getData());

        Log.d(TAG, "notifs :" + getNotifications());



    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, LoggedInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Careful")
                        .setContentText("Change status")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }

    public void addNotification(Map<String, String> data) {
        SharedPreferences sharedPrefs = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        int notifStack = getNotifications().size();
        Gson gson = new Gson();


        NotificationModel notif = new NotificationModel(2,"zaeaz","aez","aze");

        if(notifStack != 0){
            notifs = getNotifications();
        }

        Log.d(TAG, "size :" + notifStack);
        Log.d(TAG, "notifs size:" + notifs.size());

        notifs.add(notif);

        Log.d(TAG, "notifs size:" + notifs.size());
        String json = gson.toJson(notifs);
        editor.putString(getString(R.string.notificationsStack), json);
        editor.commit();



    }

    public ArrayList<NotificationModel> getNotifications() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(getString(R.string.notificationsStack), "");
        Type type = new TypeToken<ArrayList<NotificationModel>>() {}.getType();
        ArrayList<NotificationModel> arrayList = gson.fromJson(json, type);

        return  arrayList;
    }

}