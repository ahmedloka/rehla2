package com.NativeTech.rehla.model.data.dto.Models.MetaDataModels;

import java.util.List;

public class Countries {
    private String Name;

    private List<String> Cities;

    private String KMPrice;

    private String Id;

    private String NameLT;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<String> getCities() {
        return Cities;
    }

    public void setCities(List<String> cities) {
        Cities = cities;
    }

    public String getKMPrice() {
        return KMPrice;
    }

    public void setKMPrice(String KMPrice) {
        this.KMPrice = KMPrice;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNameLT() {
        return NameLT;
    }

    public void setNameLT(String nameLT) {
        NameLT = nameLT;
    }
}
