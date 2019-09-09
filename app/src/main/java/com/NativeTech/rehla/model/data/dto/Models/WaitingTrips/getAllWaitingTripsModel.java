package com.NativeTech.rehla.model.data.dto.Models.WaitingTrips;

public class getAllWaitingTripsModel {
    private String DestinationCity;

    private String UserId;

    private String Id;

    private String SourceCity;

    public String getDestinationCity() {
        return DestinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        DestinationCity = destinationCity;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSourceCity() {
        return SourceCity;
    }

    public void setSourceCity(String sourceCity) {
        SourceCity = sourceCity;
    }
}
