package com.NativeTech.rehla.model.data.dto.Models.Chats;

import java.util.List;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class ChatUserResponse {

    private Metas metas;

    private List<ChatUserModel> model;

    private ErrorsModel errors;

    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }

    public List<ChatUserModel> getModel() {
        return model;
    }

    public void setModel(List<ChatUserModel> model) {
        this.model = model;
    }

    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
