package com.example.covidtracker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

import com.example.covidtracker.activities.LoggedInActivity;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.example.covidtracker.dbhelpers.FirebaseDatabaseHelper;
import com.example.covidtracker.models.Meet;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.firebase.firestore.FieldValue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.covidtracker.Utils.writeToStorage;

public class NearbyTrackingService extends Service {
    private final String TAG = "MessageService";
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private MessageListener messageListener;
    private Message myUserUIDMessage;
    private String myUserUID;
    private Context context = this;
    private long onFoundStart = -1;
    private long contactDuration = -1;
    private String serviceStatus = "stopped";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "created nearbysrvc ");

        super.onCreate();
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        myUserUID = prefs.getString(getString(R.string.UID), "None");

        myUserUIDMessage = new Message(myUserUID.getBytes());
        messageListener = new MessageListener() {
            private String currentMeeting ;
            @Override
            public void onFound(Message message) {
                currentMeeting =  "meeting_" + Utils.getAlphaNumericString(20);

                final String metUserUID = new String(message.getContent());

                Log.d(TAG, "Found user: " + metUserUID);
                Toast.makeText(context,"Found user :" + metUserUID, Toast.LENGTH_LONG).show();

                final Meet meet = new Meet(FieldValue.serverTimestamp(), FieldValue.serverTimestamp(), "ongoing");

                onFoundStart = System.currentTimeMillis();
                Date time = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String formattedDate = df.format(time);

                LocationRequest mLocationRequest = new LocationRequest();

                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                getFusedLocationProviderClient(getApplicationContext()).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        String filePath = getApplicationContext().getFilesDir().toString() + "/meetings" + "/" + metUserUID;
                        writeToStorage(filePath , "latitude.txt", String.valueOf(location.getLatitude()) );
                        writeToStorage(filePath , "longitude.txt", String.valueOf(location.getLongitude()));
                    }
                });

                String filePath = getApplicationContext().getFilesDir().toString() + "/meetings" + "/" + metUserUID;
                writeToStorage(filePath , "date.txt", formattedDate);

                FirebaseDatabaseHelper.getInstance().addMeeting(myUserUID, metUserUID, meet, currentMeeting, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void Success() {
                        Log.d(TAG, "Inserted user");
                        Log.d(TAG, meet.toString());
                        Toast.makeText(context, "Inserted meeting", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void Fail() {
                        Log.d(TAG, "Failed inserting user");
                        Toast.makeText(context, "Failed to insert meeting", Toast.LENGTH_LONG).show();

                    }
                });
            }

            @Override
            public void onLost(Message message) {
                Log.d(TAG, "Closed application");
                String metUserUID = new String(message.getContent());
                Log.d(TAG, "Lost sight of user: " + metUserUID);

                long onFoundStop = System.currentTimeMillis();

                if (onFoundStart != -1)
                    contactDuration = onFoundStop - onFoundStart;

                String filePath = getApplicationContext().getFilesDir().toString() + "/meetings" + "/" + metUserUID;
                writeToStorage(filePath, "duration.txt", String.valueOf(contactDuration));

                FirebaseDatabaseHelper.getInstance().updateMeetingEnding(myUserUID, metUserUID, currentMeeting, FieldValue.serverTimestamp(), new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void Success() {
                        Log.d(TAG, "Updated meeting");
                        Toast.makeText(context, "Updated meeting", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void Fail() {
                        Log.d(TAG, "Failed to update meeting");
                        Toast.makeText(context, "Failed to update meeting", Toast.LENGTH_LONG).show();

                    }
                });
            }
        };

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Entrain de détecter ... ", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Entrain de détecter ... ");

        serviceStatus = "running";
        sendMessageToActivity(serviceStatus, "msg" , context);


        Nearby.getMessagesClient(this).publish(myUserUIDMessage);
        Nearby.getMessagesClient(this).subscribe(messageListener);

        createNotificationChannel();
        Intent notificationIntent = new Intent(this, LoggedInActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Entrain de détecter ... ")
                .setSmallIcon(R.drawable.ic_coronavirus)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceStatus = "stopped";
        sendMessageToActivity(serviceStatus, "msg" , context);
        Log.d(TAG, "onDestroy: called");
        Nearby.getMessagesClient(this).unpublish(myUserUIDMessage);
        Nearby.getMessagesClient(this).unsubscribe(messageListener);
    }

    @Override
    public boolean stopService(Intent name) {

        Log.d(TAG, "onDestroy: called");
        Nearby.getMessagesClient(this).unpublish(myUserUIDMessage);
        Nearby.getMessagesClient(this).unsubscribe(messageListener);

        return super.stopService(name);

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


    private static void sendMessageToActivity(String l, String msg, Context context) {
        Intent intent = new Intent("NearbyTrackingService");
        // You can also include some extra data.
        intent.putExtra("Status", msg);
        Bundle b = new Bundle();
        b.putString("serviceStatus", l);
        intent.putExtra("serviceStatus", b);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


}