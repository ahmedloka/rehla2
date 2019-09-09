package com.NativeTech.rehla.model.data.dto.Models.EditProfile;

class UpdateProfileDataRequestModel {

    private String Name;

    private String DateOfBirth;

    private String CityId;

    private String Email;

    private String Password;

    private String PhoneKey;

    private String PhoneNumber;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneKey() {
        return PhoneKey;
    }

    public void setPhoneKey(String phoneKey) {
        PhoneKey = phoneKey;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
