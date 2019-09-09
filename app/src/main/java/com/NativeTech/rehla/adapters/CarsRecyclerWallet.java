package com.NativeTech.rehla.adapters;

public class CarsRecyclerWallet {


    private String id;
    private String img;
    private String name;
    private String price;
    private String date;
    private String to;
    private String details;

    public CarsRecyclerWallet(String id, String img, String name, String price, String date, String to, String details) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        this.date = date;
        this.to = to;
        this.details = details;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}

