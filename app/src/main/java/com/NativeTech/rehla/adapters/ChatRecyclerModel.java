package com.NativeTech.rehla.adapters;

public class ChatRecyclerModel {


    private String id;
    private String img;
    private String name;
    private String body;
    private String date;
    private String SenderId;
    private String ReciverId;
    private String PartnerIdentityId;

    public ChatRecyclerModel(String id, String img, String name, String body, String date, String SenderId, String ReciverId
            , String PartnerIdentityId) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.body = body;
        this.date = date;
        this.SenderId = SenderId;
        this.ReciverId = ReciverId;
        this.PartnerIdentityId = PartnerIdentityId;
    }

    public String getPartnerIdentityId() {
        return PartnerIdentityId;
    }

    public void setPartnerIdentityId(String partnerIdentityId) {
        PartnerIdentityId = partnerIdentityId;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String partnerIdentityId) {
        SenderId = partnerIdentityId;
    }

    public String getReciverId() {
        return ReciverId;
    }

    public void setReciverId(String reciverId) {
        ReciverId = reciverId;
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

