package com.NativeTech.rehla.model.data.dto.Models.GetAllTransactions;

import java.util.List;

public class GetAllTransactionsModel {
    private List<Transactions> Transactions;

    private String TotalAmount;

    public List<com.NativeTech.rehla.model.data.dto.Models.GetAllTransactions.Transactions> getTransactions() {
        return Transactions;
    }

    public void setTransactions(List<com.NativeTech.rehla.model.data.dto.Models.GetAllTransactions.Transactions> transactions) {
        Transactions = transactions;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }
}
