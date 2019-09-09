package com.NativeTech.rehla.model.data.dto.Models;

public class ErrorsModel {

    private String  message;
    private int     Code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }
}
