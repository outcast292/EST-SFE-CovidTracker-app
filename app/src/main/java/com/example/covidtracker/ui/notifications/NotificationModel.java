package com.example.covidtracker.ui.notifications;

public class NotificationModel {

    String  id,title,body,notifType;

    @Override
    public String toString() {
        return "NotificationModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", notifType='" + notifType + '\'' +
                '}';
    }

    public NotificationModel(String id, String notifType) {
        this.id = id;
        this.notifType = notifType;
    }

    public void setId(String id) {
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

    public String getId() {
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
