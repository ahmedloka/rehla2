package com.NativeTech.rehla.model.data.dto.Models.GetRateSummary;

public class GetAllRatesModel {
    private String RateFactorId;

    private String Comment;

    private String IsRateForDriver;

    private String ReservationId;

    private String Value;

    private String Id;

    private String ProfilePhoto;

    private String Name;

    public String getRateFactorId() {
        return RateFactorId;
    }

    public void setRateFactorId(String rateFactorId) {
        RateFactorId = rateFactorId;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getIsRateForDriver() {
        return IsRateForDriver;
    }

    public void setIsRateForDriver(String isRateForDriver) {
        IsRateForDriver = isRateForDriver;
    }

    public String getReservationId() {
        return ReservationId;
    }

    public void setReservationId(String reservationId) {
        ReservationId = reservationId;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
