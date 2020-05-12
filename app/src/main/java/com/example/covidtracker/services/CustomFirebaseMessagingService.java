package com.example.covidtracker.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.covidtracker.R;
import com.example.covidtracker.SharedPrefsHelper;
import com.example.covidtracker.Utils;
import com.example.covidtracker.ui.activities.LoggedInActivity;
import com.example.covidtracker.dbhelpers.FirebaseDatabaseHelper;
import com.example.covidtracker.ui.notifications.NotificationModel;
import com.google.common.reflect.TypeToken;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "CustomFMService";
    private ArrayList<NotificationModel> notifs = new ArrayList<NotificationModel>(0);
    SharedPrefsHelper prefs;


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "newToken : " + s);
        String strUserUID;
        String token;
        try {
            strUserUID = prefs.getDeviceUUID();
            token = prefs.getDeviceToken();

        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.d(TAG, "UID isnt Setted Yet");
            return;
        }


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

        SharedPrefsHelper prefs = new SharedPrefsHelper(getBaseContext());

        Log.d(TAG, "notifs: " + remoteMessage.getData());

        prefs.addNotification(remoteMessage.getData());
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, LoggedInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
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

    /*
    public void addNotification(Map<String, String> data) {
        SharedPreferences sharedPrefs = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();


        NotificationModel notif = new NotificationModel("notif_" + Utils.getAlphaNumericString(5) , data.get("newStatus"));


        if(getNotifications().size() != 0){
            notifs = getNotifications();
            Log.d(TAG, "addNotification: notifStack.size: " + getNotifications().size() );
        }else {
            Log.d(TAG, "addNotification: notifStack.size: " + getNotifications().size() );
        }


        notifs.add(notif);

        String json = gson.toJson(notifs);
        editor.putString(getString(R.string.notificationsStack), json);
        editor.commit();

    }*/

    public ArrayList<NotificationModel> getNotifications() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(getString(R.string.notificationsStack), "");
        Type type = new TypeToken<ArrayList<NotificationModel>>() {
        }.getType();
        ArrayList<NotificationModel> arrayList = gson.fromJson(json, type);

        return arrayList;
    }

}