package com.NativeTech.rehla.model.data.dto.Models.RateDriver;

public class RateDriverModel {
    private String RateFactorId;

    private String Comment;

    private String IsRateForDriver;

    private String ReservationId;

    private String Id;

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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
