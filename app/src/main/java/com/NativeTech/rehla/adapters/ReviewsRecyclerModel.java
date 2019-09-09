package com.NativeTech.rehla.adapters;

public class ReviewsRecyclerModel {


    private String id;
    private String img;
    private String name;
    private String rate;
    private String comment;

    public ReviewsRecyclerModel(String id, String img, String name, String rate, String comment) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.rate = rate;
        this.comment = comment;
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

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

