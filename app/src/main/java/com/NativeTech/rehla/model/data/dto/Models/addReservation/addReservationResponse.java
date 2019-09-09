package com.NativeTech.rehla.model.data.dto.Models.addReservation;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

class addReservationResponse {

    private Metas metas;

    private addReservationResponseModel model;

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
    public addReservationResponseModel getModel() {
        return model;
    }

    public void setModel(addReservationResponseModel model) {
        this.model = model;
    }


}
