package com.NativeTech.rehla.model.data.dto.Models.RateDriver;

public class RateDriverRequest {
    private String RateFactorId;

    private String Comment;

    private String ReservationId;

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

    public String getReservationId() {
        return ReservationId;
    }

    public void setReservationId(String reservationId) {
        ReservationId = reservationId;
    }
}
