package com.NativeTech.rehla.model.data.dto.Models.Chats;

public class SendMessageModel {
    private String message;
    private String reciverIdentifier;
    private String myId;
    private String reciverId;


    public SendMessageModel(String message, String reciverIdentifier, String myId, String reciverId) {
        this.message = message;
        this.reciverIdentifier = reciverIdentifier;
        this.myId = myId;
        this.reciverId = reciverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReciverIdentifier() {
        return reciverIdentifier;
    }

    public void setReciverIdentifier(String reciverIdentifier) {
        this.reciverIdentifier = reciverIdentifier;
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getReciverId() {
        return reciverId;
    }

    public void setReciverId(String reciverId) {
        this.reciverId = reciverId;
    }
}
