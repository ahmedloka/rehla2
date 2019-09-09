package com.NativeTech.rehla.model.data.dto.Models;

public class Metas {
    private String message;

    private String result;
    private String Wasl;
    private String WaslRequest;

    public String getWasl() {
        return Wasl;
    }

    public void setWasl(String wasl) {
        Wasl = wasl;
    }

    public String getWaslRequest() {
        return WaslRequest;
    }

    public void setWaslRequest(String waslRequest) {
        WaslRequest = waslRequest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
