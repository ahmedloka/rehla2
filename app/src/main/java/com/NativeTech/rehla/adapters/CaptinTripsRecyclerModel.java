package com.NativeTech.rehla.adapters;

public class CaptinTripsRecyclerModel {


    private String id;
    private String from;
    private String to;
    private String date;
    private String time;
    private String distance;
    private String available;
    private String TripStatusId;
    private String endTime;


    public CaptinTripsRecyclerModel(String id, String from, String to, String date, String time
            , String distance, String available, String TripStatusId, String endTime) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.distance = distance;
        this.available = available;
        this.TripStatusId = TripStatusId;
        this.endTime = endTime;
    }


    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTripStatusId() {
        return TripStatusId;
    }

    public void setTripStatusId(String tripStatusId) {
        TripStatusId = tripStatusId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

