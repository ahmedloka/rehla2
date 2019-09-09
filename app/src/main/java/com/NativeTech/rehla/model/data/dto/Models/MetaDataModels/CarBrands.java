package com.NativeTech.rehla.model.data.dto.Models.MetaDataModels;

import java.util.List;

public class CarBrands {

    private String NameLT;
    private String Id;
    private String Name;
    private List<CarModels> CarModels;

    public String getNameLT() {
        return NameLT;
    }

    public void setNameLT(String nameLT) {
        NameLT = nameLT;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.CarModels> getCarModels() {
        return CarModels;
    }

    public void setCarModels(List<com.NativeTech.rehla.model.data.dto.Models.MetaDataModels.CarModels> carModels) {
        CarModels = carModels;
    }
}
