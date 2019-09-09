package com.NativeTech.rehla.model.data.dto.Models.AcceptOrReject;

import java.util.List;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class AcceptOrRejectResponse {
    private Metas metas;

    private List<AcceptOrRejectModel> model;

    private ErrorsModel errors;


    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }

    public List<AcceptOrRejectModel> getModel() {
        return model;
    }

    public void setModel(List<AcceptOrRejectModel> model) {
        this.model = model;
    }

    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
