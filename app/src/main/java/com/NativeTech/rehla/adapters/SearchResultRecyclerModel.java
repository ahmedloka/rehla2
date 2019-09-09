package com.NativeTech.rehla.adapters;

public class SearchResultRecyclerModel {


    private String id;
    private String img;
    private String name;
    private String price;
    private String car_type;
    private String car_color;
    private String seat_num;
    private String from_date;
    private String from_address;
    private String to_date;
    private String to_address;
    private String date;
    private String rate;
    private String count_reviews;
    private String count_ride;
    private String endDate;

    public String getCount_reviews() {
        return count_reviews;
    }

    public void setCount_reviews(String count_reviews) {
        this.count_reviews = count_reviews;
    }

    public String getCount_ride() {
        return count_ride;
    }

    public void setCount_ride(String count_ride) {
        this.count_ride = count_ride;
    }

    public SearchResultRecyclerModel(String id, String img, String name, String price, String car_type, String car_color
            , String seat_num, String from_date, String from_address
            , String to_date
            , String to_address, String date, String rate
            , String count_reviews, String count_ride, String endDate) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        this.car_type = car_type;
        this.car_color = car_color;
        this.seat_num = seat_num;
        this.from_date = from_date;
        this.from_address = from_address;
        this.to_date = to_date;
        this.to_address = to_address;
        this.date = date;
        this.rate = rate;
        this.count_reviews = count_reviews;
        this.count_ride = count_ride;
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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
}

