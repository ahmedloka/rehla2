package com.NativeTech.rehla.model.data.dto.Models.Chats;

public class ChatUserModel {
    private String CreationDate;

    private String ReciverId;

    private String PartnerPhoto;

    private String Message;

    private String Id;

    private String PartnerIdentityId;

    private String SenderId;

    private String Seen;

    private String PartnerName;

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public String getReciverId() {
        return ReciverId;
    }

    public void setReciverId(String reciverId) {
        ReciverId = reciverId;
    }

    public String getPartnerPhoto() {
        return PartnerPhoto;
    }

    public void setPartnerPhoto(String partnerPhoto) {
        PartnerPhoto = partnerPhoto;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getSeen() {
        return Seen;
    }

    public void setSeen(String seen) {
        Seen = seen;
    }

    public String getPartnerName() {
        return PartnerName;
    }

    public void setPartnerName(String partnerName) {
        PartnerName = partnerName;
    }
}
