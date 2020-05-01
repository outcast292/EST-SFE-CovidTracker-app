package com.example.covidtracker.models;

public class User {
    public String phone;
    public String status;
    //public String token;

    public User() {

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

    public User(String phone, String status) {
        this.phone = phone;
        this.status = status;
        //this.token = token;
    }
}
