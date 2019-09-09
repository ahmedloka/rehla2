package com.NativeTech.rehla.adapters;

public class NotificationsRecyclerModel {


    private String id;
    private String img;
    private String name;
    private String body;
    private String date;

    public NotificationsRecyclerModel(String id, String img, String name, String body, String date) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.body = body;
        this.date = date;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

