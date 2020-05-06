package com.example.covidtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.covidtracker.ui.notifications.NotificationModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class SharedPrefsHelper {


    private static final String TAG = "SharedPrefsHelper" ;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private static final String PREF_KEY = "com.example.covidtracker";
    private static final String DEVICE_TOKEN = "token";
    private static final String DEVICE_UID = "UID";
    private static final String IS_REGISTRED = "registered";
    private static final String PHONE_NUMBER = "phone";
    private static final String HEALTH_STATUS = "Status";
    private static final String NOTIFICATIONS_STACK = "MYCHANNEL";


    public SharedPrefsHelper(Context context) {
        this.pref = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        this.editor = pref.edit();
    }

    public void setDeviceToken(String token){
        editor.putString(DEVICE_TOKEN, token);
        editor.commit();
    }
    public void setDeviceUID(String UID){
        editor.putString(DEVICE_UID, UID);
        editor.commit();
    }
    public void setIsRegistred(boolean is_registred){
        editor.putBoolean(IS_REGISTRED, is_registred);
        editor.commit();
    }

    public void setPhoneNumber(String phoneNumber){
        editor.putString(PHONE_NUMBER, phoneNumber);
        editor.commit();
    }

    public String getPhoneNumber(){
        return pref.getString(PHONE_NUMBER,"null");
    }

    public void setHealthStatus(String healthStatus){
        editor.putString(HEALTH_STATUS, healthStatus);
        editor.commit();
    }

    public String getHealthStatus(){
        return pref.getString(HEALTH_STATUS,"null");
    }

    public String getDeviceToken(){
        return pref.getString(DEVICE_TOKEN,"null");
    }

    public String getDeviceUUID(){
        return pref.getString(DEVICE_UID,"null");
    }

    public boolean isRegistred(){
        return pref.getBoolean(IS_REGISTRED,false);
    }


    public ArrayList<NotificationModel> getNotifications() {
        Gson gson = new Gson();

        String json = pref.getString(NOTIFICATIONS_STACK, "");

        Type type = new TypeToken<ArrayList<NotificationModel>>() {}.getType();

        ArrayList<NotificationModel> arrayList = gson.fromJson(json, type);

        return  arrayList;
    }


    public void addNotification(Map<String, String> data) {
        ArrayList<NotificationModel> notifs = new ArrayList<NotificationModel>(0);

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

        editor.putString(NOTIFICATIONS_STACK, json);
        editor.commit();

    }


}
