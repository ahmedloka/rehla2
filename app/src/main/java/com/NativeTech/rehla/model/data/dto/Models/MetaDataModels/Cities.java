package com.NativeTech.rehla.model.data.dto.Models.MetaDataModels;

import java.util.List;

public class Cities {

    private String Name;

    private List<String> Users;

    private String CountryId;

    private String Id;

    private String Country;

    private String NameLT;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<String> getUsers() {
        return Users;
    }

    public void setUsers(List<String> users) {
        Users = users;
    }

    public String getCountryId() {
        return CountryId;
    }

    public void setCountryId(String countryId) {
        CountryId = countryId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getNameLT() {
        return NameLT;
    }

    public void setNameLT(String nameLT) {
        NameLT = nameLT;
    }
}
