package com.NativeTech.rehla.model.data.dto.Models.GetAllTransactions;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class GetAllTransactionsResponse {

    private Metas metas;

    private GetAllTransactionsModel model;

    private ErrorsModel errors;

    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }

    public GetAllTransactionsModel getModel() {
        return model;
    }

    public void setModel(GetAllTransactionsModel model) {
        this.model = model;
    }

    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
