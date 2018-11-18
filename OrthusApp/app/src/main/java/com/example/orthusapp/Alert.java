package com.example.orthusapp;

public class Alert {

    private String alertTime;
    private String sensorId;
    private String id;

    public Alert(){

    }

    // constructor for the adapter
    public Alert(String alertTime, String sensorId){
        this.alertTime = alertTime;
        this.sensorId = sensorId;
    }


    // this is used for identifying the key of each alert in firebase
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // getters and setters
    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }


}
