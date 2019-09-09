package com.NativeTech.rehla.model.data.dto.Models.addReservation;

public class addReservationResponseModel {
    private String SeatCount;

    private String PassengerId;

    private String PaymentType;

    private String StatusId;

    private String Id;

    private String RatedByDriver;

    private String TripId;

    public String getSeatCount() {
        return SeatCount;
    }

    public void setSeatCount(String seatCount) {
        SeatCount = seatCount;
    }

    public String getPassengerId() {
        return PassengerId;
    }

    public void setPassengerId(String passengerId) {
        PassengerId = passengerId;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public String getStatusId() {
        return StatusId;
    }

    public void setStatusId(String statusId) {
        StatusId = statusId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRatedByDriver() {
        return RatedByDriver;
    }

    public void setRatedByDriver(String ratedByDriver) {
        RatedByDriver = ratedByDriver;
    }

    public String getTripId() {
        return TripId;
    }

    public void setTripId(String tripId) {
        TripId = tripId;
    }
}
