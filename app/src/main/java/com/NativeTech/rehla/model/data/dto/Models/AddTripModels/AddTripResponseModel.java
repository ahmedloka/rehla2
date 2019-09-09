package com.NativeTech.rehla.model.data.dto.Models.AddTripModels;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class AddTripResponseModel {
    private Metas metas;

    private OfferTripModel model;

    private ErrorsModel errors;

    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }

    public OfferTripModel getModel() {
        return model;
    }

    public void setModel(OfferTripModel model) {
        this.model = model;
    }

    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
