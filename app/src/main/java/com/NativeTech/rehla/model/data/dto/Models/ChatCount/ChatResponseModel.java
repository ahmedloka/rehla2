package com.NativeTech.rehla.model.data.dto.Models.ChatCount;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class ChatResponseModel {
    private String      model;
    private Metas       metas;
    private ErrorsModel errors;


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
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
