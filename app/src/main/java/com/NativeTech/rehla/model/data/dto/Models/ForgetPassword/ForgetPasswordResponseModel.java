package com.NativeTech.rehla.model.data.dto.Models.ForgetPassword;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;

public class ForgetPasswordResponseModel {
    private String                      model;
    private MetasForgetPassword         metas;
    private ErrorsModel                 errors;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    /* public ModelResponse getModel() {
        return model;
    }

    public void setModel(ModelResponse model) {
        this.model = model;
    }*/

    public MetasForgetPassword getMetas() {
        return metas;
    }

    public void setMetas(MetasForgetPassword metas) {
        this.metas = metas;
    }

    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
