package com.NativeTech.rehla.model.data.dto.Models.GetRateSummary;

import java.util.List;

public class getRateSummaryModel {
    private List<RateSummary> RateSummary;

    private TotalRate TotalRate;

    public List<RateSummary> getRateSummary() {
        return RateSummary;
    }

    public void setRateSummary(List<RateSummary> rateSummary) {
        RateSummary = rateSummary;
    }

    public TotalRate getTotalRate() {
        return TotalRate;
    }

    public void setTotalRate(TotalRate totalRate) {
        TotalRate = totalRate;
    }
}
