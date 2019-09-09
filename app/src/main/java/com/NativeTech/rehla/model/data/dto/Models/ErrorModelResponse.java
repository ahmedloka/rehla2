package com.NativeTech.rehla.model.data.dto.Models;

class ErrorModelResponse {
    private String Code;
    private WaslModelError message;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public WaslModelError getMessage() {
        return message;
    }

    public void setMessage(WaslModelError message) {
        this.message = message;
    }
}
