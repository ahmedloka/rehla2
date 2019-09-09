package com.NativeTech.rehla.model.data.dto.Models.addReservation;

public class addReservationRequest   {
    private String SeatCount;
    private String SeatsCost;

    private String PaymentType;

    private String TripId;


    public String getSeatsCost() {
        return SeatsCost;
    }

    public void setSeatsCost(String seatsCost) {
        SeatsCost = seatsCost;
    }

    public String getSeatCount() {
        return SeatCount;
    }

    public void setSeatCount(String seatCount) {
        SeatCount = seatCount;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public String getTripId() {
        return TripId;
    }

    public void setTripId(String tripId) {
        TripId = tripId;
    }
}
