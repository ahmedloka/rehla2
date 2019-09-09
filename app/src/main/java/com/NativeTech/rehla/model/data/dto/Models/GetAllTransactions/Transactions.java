package com.NativeTech.rehla.model.data.dto.Models.GetAllTransactions;

public class Transactions {
    //private null Description;

    private String UserId;

    private String Amount;

    //private null ReferenceNumber;

    private String TransactionTypeId;

    private String Id;

    private String Reason;

    private String TransactionDate;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getTransactionTypeId() {
        return TransactionTypeId;
    }

    public void setTransactionTypeId(String transactionTypeId) {
        TransactionTypeId = transactionTypeId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        TransactionDate = transactionDate;
    }
}
