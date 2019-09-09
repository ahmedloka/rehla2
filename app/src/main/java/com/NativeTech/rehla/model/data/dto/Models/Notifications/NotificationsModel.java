package com.NativeTech.rehla.model.data.dto.Models.Notifications;

public class NotificationsModel {
    private String ReceiverId;

    private String CreationDate;

    private String MessageLT;

    private String Message;

    private String SenderName;

    private String Id;

    private String SenderId;

    private String SenderPhoto;

    public String getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(String receiverId) {
        ReceiverId = receiverId;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public String getMessageLT() {
        return MessageLT;
    }

    public void setMessageLT(String messageLT) {
        MessageLT = messageLT;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getSenderPhoto() {
        return SenderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        SenderPhoto = senderPhoto;
    }
}
