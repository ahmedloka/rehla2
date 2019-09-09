package com.NativeTech.rehla.model.data.dto.Models.LoginModel;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;
import com.NativeTech.rehla.model.data.dto.Models.ModelResponse;

public class LoginResponseModel {
    private ModelResponse   model;
    private Metas           metas;
    private ErrorsModel     errors;


    public ModelResponse getModel() {
        return model;
    }

    public void setModel(ModelResponse model) {
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
