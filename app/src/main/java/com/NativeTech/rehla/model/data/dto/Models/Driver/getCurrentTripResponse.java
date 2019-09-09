package com.NativeTech.rehla.model.data.dto.Models.Driver;

import java.util.List;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class getCurrentTripResponse {
    private List<getDriverCurrentTrip> model;
    private Metas metas;
    private ErrorsModel errors;


    public List<getDriverCurrentTrip> getModel() {
        return model;
    }

    public void setModel(List<getDriverCurrentTrip> model) {
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
