package com.NativeTech.rehla.model.chat;

public class Model {
    private String CreationDate;

    private String ReciverId;

    private String PartnerPhoto;

    private String Message;

    private String Id;

    private String PartnerIdentityId;

    private String SenderId;

    private String Seen;

    private String PartnerName;

    public String getCreationDate ()
    {
        return CreationDate;
    }

    public void setCreationDate (String CreationDate)
    {
        this.CreationDate = CreationDate;
    }

    public String getReciverId ()
    {
        return ReciverId;
    }

    public void setReciverId (String ReciverId)
    {
        this.ReciverId = ReciverId;
    }

    public String getPartnerPhoto ()
    {
        return PartnerPhoto;
    }

    public void setPartnerPhoto (String PartnerPhoto)
    {
        this.PartnerPhoto = PartnerPhoto;
    }

    public String getMessage ()
    {
        return Message;
    }

    public void setMessage (String Message)
    {
        this.Message = Message;
    }

    public String getId ()
    {
        return Id;
    }

    public void setId (String Id)
    {
        this.Id = Id;
    }

    public String getPartnerIdentityId ()
    {
        return PartnerIdentityId;
    }

    public void setPartnerIdentityId (String PartnerIdentityId)
    {
        this.PartnerIdentityId = PartnerIdentityId;
    }

    public String getSenderId ()
    {
        return SenderId;
    }

    public void setSenderId (String SenderId)
    {
        this.SenderId = SenderId;
    }

    public String getSeen ()
    {
        return Seen;
    }

    public void setSeen (String Seen)
    {
        this.Seen = Seen;
    }

    public String getPartnerName ()
    {
        return PartnerName;
    }

    public void setPartnerName (String PartnerName)
    {
        this.PartnerName = PartnerName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CreationDate = "+CreationDate+", ReciverId = "+ReciverId+", PartnerPhoto = "+PartnerPhoto+", Message = "+Message+", Id = "+Id+", PartnerIdentityId = "+PartnerIdentityId+", SenderId = "+SenderId+", Seen = "+Seen+", PartnerName = "+PartnerName+"]";
    }
}
