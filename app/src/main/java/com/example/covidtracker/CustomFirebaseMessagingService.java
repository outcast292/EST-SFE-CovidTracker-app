package com.example.covidtracker;

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
import com.example.covidtracker.activities.LoggedInActivity;
import com.example.covidtracker.dbhelpers.FirebaseDatabaseHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.example.covidtracker.Utils.readFromStorage;

public class CustomFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "CustomFMService";

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d(TAG, "newToken : " + s);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        final String strUserUID = sharedPreferences.getString(getString(R.string.UID), "None");
        String token = sharedPreferences.getString(getString(R.string.token), "None");

        Log.d(TAG, "currentToken : " + token);
        /*
        FirebaseDatabaseHelper.getInstance().updateDeviceToken(strUserUID, token, new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void Success() {
                Log.d(TAG, "Updated");
            }

            @Override
            public void Fail() {
                Log.d(TAG, "Failed");
            }
        });*/
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getData());

        sendNotification("recieved notif");

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

}