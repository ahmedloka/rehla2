package com.NativeTech.rehla.model.data.dto.Models.TripDetails;

import com.NativeTech.rehla.model.data.dto.Models.CarsModels.CarModel;

public class CarModelTripDetails {
    private String PlateNumber;

    /*private null User;

    private null InsurancePhoto;

    private null LicencePhoto;*/

    private com.NativeTech.rehla.model.data.dto.Models.CarsModels.CarModel CarModel;

    //private null ProductionYear;

    private String Verified;

    private String SeatCount;

    private String CarColorId;

   // private null CarPhoto;

    private String UserId;

    private String CarModelId;

    /*private null CarPaperPhoto;

    private null CarType;

    private null CarColor;*/

    private String Id;

    private String CarTypeId;


    public String getPlateNumber() {
        return PlateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        PlateNumber = plateNumber;
    }

    public CarModel getCarModel() {
        return CarModel;
    }

    public void setCarModel(CarModel carModel) {
        CarModel = carModel;
    }

    public String getVerified() {
        return Verified;
    }

    public void setVerified(String verified) {
        Verified = verified;
    }

    public String getSeatCount() {
        return SeatCount;
    }

    public void setSeatCount(String seatCount) {
        SeatCount = seatCount;
    }

    public String getCarColorId() {
        return CarColorId;
    }

    public void setCarColorId(String carColorId) {
        CarColorId = carColorId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCarModelId() {
        return CarModelId;
    }

    public void setCarModelId(String carModelId) {
        CarModelId = carModelId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCarTypeId() {
        return CarTypeId;
    }

    public void setCarTypeId(String carTypeId) {
        CarTypeId = carTypeId;
    }
}
