package com.NativeTech.rehla.model.data.dto.Models.SendToBank;

public class sendToBankRequest {
    private String Amount;

    private String BankId;

    private String AccountNumber;

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getBankId() {
        return BankId;
    }

    public void setBankId(String bankId) {
        BankId = bankId;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }
}
