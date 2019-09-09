package com.NativeTech.rehla.model.data.dto.Models.MetaDataModels;

import java.util.List;

public class MetaDataModel {

    private TermAndConditions[] termAndConditions;

    private List<Cities> cities;
    private List<Countries> countries;

    private List<CarBrands> carBrands;
    private List<CarTypes> carTypes;
    private List<CarColors> carColors;
    private List<RateFactors> rateFactors;
    private List<Settings> settings;
    private List<BankModel> banks;

    public List<BankModel> getBanks() {
        return banks;
    }

    public void setBanks(List<BankModel> banks) {
        this.banks = banks;
    }

    public List<RateFactors> getRateFactors() {
        return rateFactors;
    }

    public void setRateFactors(List<RateFactors> rateFactors) {
        this.rateFactors = rateFactors;
    }

    public List<Settings> getSettings() {
        return settings;
    }

    public void setSettings(List<Settings> settings) {
        this.settings = settings;
    }

    public List<CarBrands> getCarBrands() {
        return carBrands;
    }

    public void setCarBrands(List<CarBrands> carBrands) {
        this.carBrands = carBrands;
    }

    public List<CarTypes> getCarTypes() {
        return carTypes;
    }

    public void setCarTypes(List<CarTypes> carTypes) {
        this.carTypes = carTypes;
    }

    public List<CarColors> getCarColors() {
        return carColors;
    }

    public void setCarColors(List<CarColors> carColors) {
        this.carColors = carColors;
    }

    public TermAndConditions[] getTermAndConditions() {
        return termAndConditions;
    }

    public void setTermAndConditions(TermAndConditions[] termAndConditions) {
        this.termAndConditions = termAndConditions;
    }

    public List<Cities> getCities() {
        return cities;
    }

    public void setCities(List<Cities> cities) {
        this.cities = cities;
    }


    /*public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }
*/
    public List<Countries> getCountries() {
        return countries;
    }

    public void setCountries(List<Countries> countries) {
        this.countries = countries;
    }
}
