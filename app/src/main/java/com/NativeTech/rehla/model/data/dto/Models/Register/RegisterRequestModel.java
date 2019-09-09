package com.NativeTech.rehla.model.data.dto.Models.Register;

public class RegisterRequestModel {

    private String Name;

    private String DateOfBirth;

    private int CityId;

    private String Email;

    private boolean Gender;
    private boolean IsSaudi;

    private String Password;

    private String ProfilePhoto;

    private String PhoneKey;

    private String PhoneNumber;
    private String IdentityNumber;

    public boolean isSaudi() {
        return IsSaudi;
    }

    public void setSaudi(boolean saudi) {
        IsSaudi = saudi;
    }

    public boolean isGender() {
        return Gender;
    }

    public String getIdentityNumber() {
        return IdentityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        IdentityNumber = identityNumber;
    }

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

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean getGender() {
        return Gender;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
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
