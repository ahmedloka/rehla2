package com.NativeTech.rehla.model.data.dto.Models.TripDetails;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class getTripDetailsResponse {

    private Metas metas;

    private ModelTripDetails model;

    private ErrorsModel errors;


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
    public ModelTripDetails getModel() {
        return model;
    }

    public void setModel(ModelTripDetails model) {
        this.model = model;
    }


}
