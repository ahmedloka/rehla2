package com.NativeTech.rehla.adapters;

public class CarsRecyclerModel {


    private String id;
    private String img;
    private String name;
    private String car_type;
    private String car_color;
    private String seat_num;

    public CarsRecyclerModel(String id, String img, String name, String car_type, String car_color, String seat_num) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.car_type = car_type;
        this.car_color = car_color;
        this.seat_num = seat_num;
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
}

