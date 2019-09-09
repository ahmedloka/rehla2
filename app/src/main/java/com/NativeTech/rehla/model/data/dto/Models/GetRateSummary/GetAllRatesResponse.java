package com.NativeTech.rehla.model.data.dto.Models.GetRateSummary;

import java.util.List;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class GetAllRatesResponse {
    private Metas metas;

    private List<GetAllRatesModel> model;

    private ErrorsModel errors;

    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }

    public List<GetAllRatesModel> getModel() {
        return model;
    }

    public void setModel(List<GetAllRatesModel> model) {
        this.model = model;
    }

    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
