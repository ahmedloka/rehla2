package com.NativeTech.rehla.adapters;

public class MyOfferedUpcomingRideItemRecyclerModel {


    private String id;
    private String img;
    private String name;
    private String price;
    private String car_type;
    private String car_color;
    private String seat_num;
    private String statusRide;
    private String from_date;
    private String from_address;
    private String to_date;
    private String to_address;
    private String date;
    private String expected_time;
    private String rateStatus;
    private String rate;
    private String reasons;
    private String phone_num;
    private String endDate;
    private String DriverId;
    private String DriverName;
    private String DriverIdentityId;
    private String DriverProfilePhoto;


    public MyOfferedUpcomingRideItemRecyclerModel(String id, String img
            , String name, String price, String car_type, String car_color
            , String seat_num, String statusRide, String from_date, String from_address
            , String to_date, String to_address, String date, String expected_time
            , String rateStatus, String rate, String reasons, String phone_num, String endDate
            , String DriverId, String DriverName, String DriverIdentityId, String DriverProfilePhoto) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        this.car_type = car_type;
        this.car_color = car_color;
        this.seat_num = seat_num;
        this.statusRide = statusRide;
        this.from_date = from_date;
        this.from_address = from_address;
        this.to_date = to_date;
        this.to_address = to_address;
        this.date = date;
        this.expected_time = expected_time;
        this.rateStatus = rateStatus;
        this.rate = rate;
        this.reasons = reasons;
        this.phone_num = phone_num;
        this.endDate = endDate;
        this.DriverId = DriverId;
        this.DriverName = DriverName;
        this.DriverIdentityId = DriverIdentityId;
        this.DriverProfilePhoto = DriverProfilePhoto;
    }


    public String getDriverId() {
        return DriverId;
    }

    public void setDriverId(String driverId) {
        DriverId = driverId;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public String getDriverIdentityId() {
        return DriverIdentityId;
    }

    public void setDriverIdentityId(String driverIdentityId) {
        DriverIdentityId = driverIdentityId;
    }

    public String getDriverProfilePhoto() {
        return DriverProfilePhoto;
    }

    public void setDriverProfilePhoto(String driverProfilePhoto) {
        DriverProfilePhoto = driverProfilePhoto;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public String getSeat_num() {
        return seat_num;
    }

    public void setSeat_num(String seat_num) {
        this.seat_num = seat_num;
    }

    public String getStatusRide() {
        return statusRide;
    }

    public void setStatusRide(String statusRide) {
        this.statusRide = statusRide;
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

    public String getExpected_time() {
        return expected_time;
    }

    public void setExpected_time(String expected_time) {
        this.expected_time = expected_time;
    }

    public String getRateStatus() {
        return rateStatus;
    }

    public void setRateStatus(String rateStatus) {
        this.rateStatus = rateStatus;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }
}

