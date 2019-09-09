package com.NativeTech.rehla.model.data.dto.Models.GetRateSummary;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class getRateSummaryResponse {
    private Metas metas;

    private getRateSummaryModel model;

    private ErrorsModel errors;

    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }

    public getRateSummaryModel getModel() {
        return model;
    }

    public void setModel(getRateSummaryModel model) {
        this.model = model;
    }

    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
