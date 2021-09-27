package com.mrgreenapps.notification.model;

public class NotificationModel {
    String title;
    String body;
    String datTime;



    public NotificationModel(String title, String body, String datTime) {
        this.title = title;
        this.body = body;
        this.datTime = datTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDatTime() {
        return datTime;
    }

    public void setDatTime(String datTime) {
        this.datTime = datTime;
    }
}
