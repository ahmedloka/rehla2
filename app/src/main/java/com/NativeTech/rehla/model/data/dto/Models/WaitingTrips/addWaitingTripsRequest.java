package com.NativeTech.rehla.model.data.dto.Models.WaitingTrips;

public class addWaitingTripsRequest {
    private String DestinationCity;

    private String SourceCity;

    public String getDestinationCity() {
        return DestinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        DestinationCity = destinationCity;
    }

    public String getSourceCity() {
        return SourceCity;
    }

    public void setSourceCity(String sourceCity) {
        SourceCity = sourceCity;
    }
}
