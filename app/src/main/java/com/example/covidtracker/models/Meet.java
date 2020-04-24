package com.example.covidtracker.models;

import com.google.firebase.firestore.FieldValue;

public class Meet {
    private FieldValue foundTimestamp;
    private FieldValue lostTimestamp;
    private String status;
    private String lat;
    private String lon;

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Meet{" +
                "foundTimestamp=" + foundTimestamp +
                ", lostTimestamp=" + lostTimestamp +
                ", status='" + status + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }

    public Meet(FieldValue foundTimestamp, FieldValue lostTimestamp, String status) {
        this.foundTimestamp = foundTimestamp;
        this.lostTimestamp = lostTimestamp;
        this.status = "ongoing";
    }

    public FieldValue getFoundTimestamp() {
        return foundTimestamp;
    }

    public void setFoundTimestamp(FieldValue foundTimestamp) {
        this.foundTimestamp = foundTimestamp;
    }

    public FieldValue getLostTimestamp() {
        return lostTimestamp;
    }

    public void setLostTimestamp(FieldValue lostTimestamp) {
        this.lostTimestamp = lostTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
