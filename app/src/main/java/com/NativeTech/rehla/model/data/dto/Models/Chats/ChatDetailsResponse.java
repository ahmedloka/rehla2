package com.NativeTech.rehla.model.data.dto.Models.Chats;

import java.util.List;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class ChatDetailsResponse {

    private Metas metas;

    private List<ChatDetailsModel> model;

    private ErrorsModel errors;

    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }

    public List<ChatDetailsModel> getModel() {
        return model;
    }

    public void setModel(List<ChatDetailsModel> model) {
        this.model = model;
    }

    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
