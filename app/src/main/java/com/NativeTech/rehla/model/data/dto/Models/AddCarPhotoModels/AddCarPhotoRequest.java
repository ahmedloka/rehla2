package com.NativeTech.rehla.model.data.dto.Models.AddCarPhotoModels;

public class AddCarPhotoRequest {
    private int NumberType;
    private String Image;

    public int getNumberType() {
        return NumberType;
    }

    public void setNumberType(int numberType) {
        NumberType = numberType;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
