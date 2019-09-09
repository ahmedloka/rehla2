package com.NativeTech.rehla.model.data.dto.geniric;

import com.NativeTech.rehla.model.data.dto.ErrorDTO;
import com.NativeTech.rehla.model.data.dto.MetaDTO;
import com.google.gson.annotations.SerializedName;


public class ObjectModel<T>{
        @SerializedName("model")
        private T model;
        @SerializedName("metas")
        private MetaDTO metas;
        @SerializedName("errors")
        private ErrorDTO errors;

        public ObjectModel(T model, MetaDTO metas, ErrorDTO errors) {
            this.model = model;
            this.metas = metas;
            this.errors = errors;
        }

        public T getModel() {
            return model;
        }
        public void setModel(T model) {
            this.model = model;
        }

        public MetaDTO getMetas() {
            return metas;
        }
        public void setMetas(MetaDTO metas) {
            this.metas = metas;
        }

        public ErrorDTO getErrors() {
            return errors;
        }
        public void setErrors(ErrorDTO errors) {
            this.errors = errors;
        }


}

