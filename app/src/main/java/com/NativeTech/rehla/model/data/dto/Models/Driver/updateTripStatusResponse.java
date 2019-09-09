package com.NativeTech.rehla.model.data.dto.Models.Driver;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;

public class updateTripStatusResponse {
    /*private getDriverCurrentTrip model;
    private Metas metas;*/
    private ErrorsModel errors;


    /*public getDriverCurrentTrip getModel() {
        return model;
    }

    public void setModel(getDriverCurrentTrip model) {
        this.model = model;
    }

    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }
*/
    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
