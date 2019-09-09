package com.NativeTech.rehla.model.data.dto.Models;

public class checkDriverStartedTripResponse {
    private boolean     model;
    private Metas metas;

    private ErrorsModel errors;

    public boolean isModel() {
        return model;
    }

    public void setModel(boolean model) {
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
