package com.NativeTech.rehla.model.data.dto.Models.AddCar;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class AddCarResponseModelFinal {
    private Metas metas;

    private AddCarModelResponseModel model;

    private ErrorsModel errors;


    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }

    public AddCarModelResponseModel getModel() {
        return model;
    }

    public void setModel(AddCarModelResponseModel model) {
        this.model = model;
    }

    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
