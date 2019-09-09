package com.NativeTech.rehla.model.data.dto.Models.AcceptOrReject;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

class AcceptOrRejectActionResponse {
    private Metas metas;

    private AcceptOrRejectActionModelResponse model;

    private ErrorsModel errors;


    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }

    public AcceptOrRejectActionModelResponse getModel() {
        return model;
    }

    public void setModel(AcceptOrRejectActionModelResponse model) {
        this.model = model;
    }

    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
