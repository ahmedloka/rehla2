package com.NativeTech.rehla.model.data.dto.Models.WaitingTrips;

import java.util.List;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class getAllWaitingTripsResponse {
    private Metas metas;

    private List<getAllWaitingTripsModel> model;

    private ErrorsModel errors;

    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }

    public List<getAllWaitingTripsModel> getModel() {
        return model;
    }

    public void setModel(List<getAllWaitingTripsModel> model) {
        this.model = model;
    }

    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
