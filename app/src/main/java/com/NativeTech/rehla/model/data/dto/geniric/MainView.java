package com.NativeTech.rehla.model.data.dto.geniric;

/**
 * Created by moon on 5/14/2018.
 */

public interface MainView {
    void onFinished();
    void onDismissLoader();
    void onShowLoader();
    void onTimeOut();
    void onError(int code);

}