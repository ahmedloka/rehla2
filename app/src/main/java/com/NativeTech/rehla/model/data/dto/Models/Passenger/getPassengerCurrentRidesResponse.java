package com.NativeTech.rehla.model.data.dto.Models.Passenger;

import java.util.List;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class getPassengerCurrentRidesResponse {
    private List<getPassengerCurrentRidesModel> model;
    private Metas metas;
    private ErrorsModel errors;


    public List<getPassengerCurrentRidesModel> getModel() {
        return model;
    }
    public void setModel(List<getPassengerCurrentRidesModel> model) {
        this.model = model;
    }
    public Metas getMetas() {
        return metas;
    }
    public void setMetas(Metas metas) {
        this.metas = metas;
    }
    public ErrorsModel getErrors() {
        return errors;
    }
    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
