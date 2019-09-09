package com.NativeTech.rehla.Utills;

import com.NativeTech.rehla.model.data.dto.geniric.MainView;

import org.json.JSONObject;

import java.net.SocketTimeoutException;

import retrofit2.HttpException;

public class Utils {

    public static void showError(MainView view, Throwable e) {

        try
        {
            if (e instanceof HttpException) {
                HttpException error = (HttpException) e;
                JSONObject jsonObject = new JSONObject(((HttpException) e).response().errorBody().string());
                int errorCode;
                try
                {
                    errorCode = jsonObject.getJSONObject("errors").getInt("Code");
                }
                catch (Exception e1){errorCode=Constant.GENERAL_API_EXCEPTION;}
                view.onError(errorCode);
                return;
            }
            if(e instanceof SocketTimeoutException)
            {
                view.onTimeOut();
                return;
            }
            view.onError(((HttpException) e).code());
        }catch (Exception err)
        {
            view.onError(Constant.NONHTTP_EXCEPTION);
        }

    }
}
