package com.example.covidtracker.models;

import com.google.firebase.firestore.Exclude;

public class User {
    public String phone;
    private String uid;
    public String status;
    //public String token;

    public User() {

    }
    @Exclude
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

   /* public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }*/

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User(String uid, String phone, String status) {
        this.uid = uid;
        this.phone = phone;
        this.status = status;
        //this.token = token;
    }
}
