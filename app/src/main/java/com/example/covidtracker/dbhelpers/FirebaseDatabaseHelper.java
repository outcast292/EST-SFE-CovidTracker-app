package com.example.covidtracker.dbhelpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.covidtracker.R;
import com.example.covidtracker.Utils;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.example.covidtracker.models.Meet;
import com.example.covidtracker.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDatabaseHelper {
    private static final String TAG = "FirebaseDatabaseHelper";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseDatabaseHelper firebaseDatabaseHelper = new FirebaseDatabaseHelper();
    private CollectionReference usersCollection, meetingsCollection;

    private FirebaseDatabaseHelper() {
        usersCollection = db.collection("users");
        meetingsCollection = db.collection("users_meetings");
    }

    public static FirebaseDatabaseHelper getInstance() {
        return firebaseDatabaseHelper;
    }

    public interface DataStatus {
        void Success();

        void Fail();
    }

    public void addUser(User user, final Context context, final DataStatus status) {
        final DocumentReference newUserRef = usersCollection.document();
        newUserRef.set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(context.getString(R.string.UID), newUserRef.getId());
                        Log.d(TAG, "Added: " + newUserRef.getId());
                        editor.commit();
                        status.Success();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Failed adding: " + e);
                        status.Fail();
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        status.Fail();
                    }
                });
    }

    public void addMeeting(final String myUserUID, final String metUserUID, Meet meet, String currentMeeting, final DataStatus status ) {
        meetingsCollection.document(myUserUID).collection(metUserUID).document(currentMeeting).set(meet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getMeetingsCount(myUserUID,metUserUID);
                        status.Success();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        status.Fail();
                    }
                });
    }

    public void updateMeetingEnding(String myUserID, String metUserUID, String currentMeeting, FieldValue endingTimestamp, final DataStatus status) {
        DocumentReference meetToUpdate = meetingsCollection.document(myUserID).collection(metUserUID).document(currentMeeting);
        Map<String, Object> updatedFields = new HashMap<>();
        updatedFields.put("lostTimestamp", endingTimestamp);
        updatedFields.put("status", "ended");
        meetToUpdate.update(updatedFields)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        status.Success();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        status.Fail();
                    }
                });
    }
    public void updateDeviceToken(String myUserID, String deviceToken, final DataStatus status) {
        DocumentReference userToUpdate = usersCollection.document(myUserID);
        Map<String, Object> updatedFields = new HashMap<>();
        updatedFields.put("token", deviceToken);
        userToUpdate.update(updatedFields)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        status.Success();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        status.Fail();
                    }
                });

    }

    public void getEncounteredUserInfo(String myUserID, String metUserUID) {


    }

    public void hasEncountredInfected(){

    }

    public void getMeetingsCount(String myUserID, String metUserUID){

        meetingsCollection.document(myUserID).collection(metUserUID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int count = 0;
                    for (DocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, "" + document.getData() );
                        count++;
                    }
                    Log.d(TAG, "n meetings : " + count );
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

    }
}
