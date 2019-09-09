package com.NativeTech.rehla.model.data.dto.Models.MetaDataModels;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class MetaDataResponseModel {
    private MetaDataModel model;

    private ErrorsModel errors;

    private Metas metas;


    public MetaDataModel getModel() {
        return model;
    }

    public void setModel(MetaDataModel model) {
        this.model = model;
    }

    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }

    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }
}
