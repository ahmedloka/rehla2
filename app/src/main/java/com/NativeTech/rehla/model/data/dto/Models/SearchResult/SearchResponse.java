package com.NativeTech.rehla.model.data.dto.Models.SearchResult;

import java.util.List;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class SearchResponse {

    private Metas metas;

    private List<SearchResultModel> model;

    private ErrorsModel errors;


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
    public List<SearchResultModel> getModel() {
        return model;
    }

    public void setModel(List<SearchResultModel> model) {
        this.model = model;
    }


}
