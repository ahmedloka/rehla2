package com.NativeTech.rehla.model.data.dto.Models.GetDriverProfile;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class DriverProfileResponseModel {
    private DriverProfileModel   model;
    private Metas           metas;
    private ErrorsModel     errors;


    public DriverProfileModel getModel() {
        return model;
    }

    public void setModel(DriverProfileModel model) {
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
