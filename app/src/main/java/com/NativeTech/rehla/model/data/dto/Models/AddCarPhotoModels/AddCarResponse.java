package com.NativeTech.rehla.model.data.dto.Models.AddCarPhotoModels;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class AddCarResponse {

    private AddCarModelResponseModel model;
    private Metas metas;
    private ErrorsModel errors;


    public AddCarModelResponseModel getModel() {
        return model;
    }

    public void setModel(AddCarModelResponseModel model) {
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
