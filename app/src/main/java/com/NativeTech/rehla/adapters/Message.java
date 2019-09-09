package com.NativeTech.rehla.adapters;

public class Message {

    private String text; // message body
    private String name;
    private String photo;
    private boolean belongsToCurrentUser; // is this message sent by us?
    private String receiverId;
    private String CreationDate;
    //private MemberData data; // data of the user that sent this message


    public Message(String text, String name, String photo, boolean belongsToCurrentUser,String CreationDate) {
        this.text = text;
        this.name = name;
        this.photo = photo;
        this.belongsToCurrentUser = belongsToCurrentUser;
        this.CreationDate=CreationDate;
    }

    public Message(String text, String name, String photo, boolean belongsToCurrentUser, String receiverId,String CreationDate) {
        this.text = text;
        this.name = name;
        this.photo = photo;
        this.belongsToCurrentUser = belongsToCurrentUser;
        this.receiverId = receiverId;
        this.CreationDate=CreationDate;
    }


    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPhoto() {
        return photo;
    }


    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public void setBelongsToCurrentUser(boolean belongsToCurrentUser) {
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }


    public String getDate() {
        return CreationDate;
    }

    public void setDate(String date) {
        this.CreationDate = date;
    }
}
