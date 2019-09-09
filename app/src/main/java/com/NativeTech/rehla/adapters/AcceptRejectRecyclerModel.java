package com.NativeTech.rehla.adapters;

public class AcceptRejectRecyclerModel {


    private String id;
    private String img;
    private String name;
    private String reviews_count;
    private String rate;
    private String payment_type;
    private String trip_status;
    private String from_date;
    private String from_address;
    private String to_date;
    private String to_address;
    private String date;
    private String seat_number;
    private String passenger_id;
    private String phone_num;
    private String passengerName;
    private String passengerIdentityId;
    private String passengerProfilePhoto;

    public AcceptRejectRecyclerModel(String id, String img, String name
            , String reviews_count, String rate, String payment_type, String trip_status
            , String from_date, String from_address, String to_date
            , String to_address, String date, String seat_number, String passenger_id
            , String phone_num
            , String passengerName
            , String passengerIdentityId
            , String passengerProfilePhoto
    ) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.reviews_count = reviews_count;
        this.rate = rate;
        this.payment_type = payment_type;
        this.trip_status = trip_status;
        this.from_date = from_date;
        this.from_address = from_address;
        this.to_date = to_date;
        this.to_address = to_address;
        this.date = date;
        this.seat_number = seat_number;
        this.passenger_id = passenger_id;
        this.phone_num = phone_num;
        this.passengerName = passengerName;
        this.passengerIdentityId = passengerIdentityId;
        this.passengerProfilePhoto = passengerProfilePhoto;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerIdentityId() {
        return passengerIdentityId;
    }

    public void setPassengerIdentityId(String passengerIdentityId) {
        this.passengerIdentityId = passengerIdentityId;
    }

    public String getPassengerProfilePhoto() {
        return passengerProfilePhoto;
    }

    public void setPassengerProfilePhoto(String passengerProfilePhoto) {
        this.passengerProfilePhoto = passengerProfilePhoto;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getPassenger_id() {
        return passenger_id;
    }

    public void setPassenger_id(String passenger_id) {
        this.passenger_id = passenger_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(String reviews_count) {
        this.reviews_count = reviews_count;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }


    public String getTrip_status() {
        return trip_status;
    }

    public void setTrip_status(String trip_status) {
        this.trip_status = trip_status;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }
}

