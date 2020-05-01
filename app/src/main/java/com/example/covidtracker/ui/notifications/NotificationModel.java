package com.example.covidtracker.ui.notifications;

public class NotificationModel {

    int id;
    String title,body,notifType;


    public NotificationModel(int id, String title, String body, String notifType) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.notifType = notifType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setNotifType(String notifType) {
        this.notifType = notifType;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getNotifType() {
        return notifType;
    }
}
