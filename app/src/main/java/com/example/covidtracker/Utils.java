package com.example.covidtracker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String TAG = "Utils";
    private final static int READ_BLOCK_SIZE = 8192;
    public static final int MULTIPLE_PERMISSIONS = 100;


    public static String serialize(Serializable obj) throws IOException {
        if (obj == null) return "";
        ByteArrayOutputStream serialObj = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(serialObj);
        objStream.writeObject(obj);
        objStream.close();
        return encodeBytes(serialObj.toByteArray());
    }

    public static Object deserialize(String str) throws IOException, ClassNotFoundException {
        if (str == null || str.length() == 0) return null;
        ByteArrayInputStream serialObj = new ByteArrayInputStream(decodeBytes(str));
        ObjectInputStream objStream = new ObjectInputStream(serialObj);
        return objStream.readObject();
    }

    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
        }

        return strBuf.toString();
    }

    public static byte[] decodeBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i+=2) {
            char c = str.charAt(i);
            bytes[i/2] = (byte) ((c - 'a') << 4);
            c = str.charAt(i+1);
            bytes[i/2] += (c - 'a');
        }
        return bytes;
    }

    public static void saveArrayList(Context context, String key, ArrayList<String> list){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public static ArrayList<String> getArrayList(String key, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static String readFromStorage(String filePath, String fileName){
        File file = new File(filePath + "/" + fileName);
        String content = "";

        if (fileName == null)
            return null;

        try {
            if(file.exists()){
                FileInputStream filein = new FileInputStream (file);  // 2nd line
                InputStreamReader inputReader = new InputStreamReader(filein);

                char[] inputBuffer= new char[READ_BLOCK_SIZE];
                int charRead;

                while ((charRead= inputReader.read(inputBuffer))>0) {
                    // char to string conversion
                    content = String.copyValueOf(inputBuffer,0,charRead);
                }
                inputReader.close();
                return content;
            }
            else {
                return null;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void writeToStorage(String filePath, String fileName, String content) {
        try {
            File file = new File(filePath);

            if(!file.exists()){
                file.mkdirs();
            }

            FileOutputStream fileout = new FileOutputStream(file + "/" + fileName);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);

            outputWriter.write(content);
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkPermission(Activity activity) {
        if (
                (ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        ) {

            Log.d(TAG, "0");

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (activity, Manifest.permission.INTERNET) &&
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (activity, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (activity, Manifest.permission.ACCESS_COARSE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (activity, Manifest.permission.BLUETOOTH))
            {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Log.d(TAG, "1");

                        activity.requestPermissions(
                                new String[]{Manifest.permission
                                        .INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MULTIPLE_PERMISSIONS);
                    }

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d(TAG, "2");

                    activity.requestPermissions(
                            new String[]{Manifest.permission
                                    .INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MULTIPLE_PERMISSIONS);
                }
            }
        }
    }

    public static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}