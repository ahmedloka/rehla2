package com.NativeTech.rehla.model.data.dto.Models.getCityModels;

import java.util.List;

public class SearchResponseModel {
    private List<Results> results;

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public List<Results> getResults() {
        return results;
    }
}
