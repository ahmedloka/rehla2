package com.NativeTech.rehla.model.data.dto.Models.Notifications;

import java.util.List;

import com.NativeTech.rehla.model.data.dto.Models.ErrorsModel;
import com.NativeTech.rehla.model.data.dto.Models.Metas;

public class NotificationResponse {
    private Metas metas;

    private List<NotificationsModel> model;

    private ErrorsModel errors;

    public Metas getMetas() {
        return metas;
    }

    public void setMetas(Metas metas) {
        this.metas = metas;
    }

    public List<NotificationsModel> getModel() {
        return model;
    }

    public void setModel(List<NotificationsModel> model) {
        this.model = model;
    }

    public ErrorsModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorsModel errors) {
        this.errors = errors;
    }
}
