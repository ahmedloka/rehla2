package com.NativeTech.rehla.model.data.dto.Models.CarsModels;

import java.util.List;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class getAllCarsResponse {

    private Metas metas;

    private List<ModelCar> model;

    private ErrorsModel errors;

    public List<ModelCar> getModel() {
        return model;
    }

    public void setModel(List<ModelCar> model) {
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
