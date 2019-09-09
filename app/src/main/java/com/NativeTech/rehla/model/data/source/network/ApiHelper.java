package com.NativeTech.rehla.model.data.source.network;

/**
 * Created by moon on 5/14/2018.
 */


import android.text.TextUtils;

import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.model.data.dto.TokenDTO;
import com.NativeTech.rehla.model.data.dto.geniric.MainView;
import com.NativeTech.rehla.model.data.dto.geniric.Presenter;
import com.NativeTech.rehla.model.data.source.preferences.SharedManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Ayman on 3/3/2017.
 */

public class ApiHelper implements Presenter {

    public Gson mGson;
    private CompositeDisposable mCompositeSubscribtion;

    public ApiHelper() {
        mGson = new Gson();
    }

    private static OkHttpClient get_HTTPClient() {
        final Map<String, String> headers = new HashMap<>();
        TokenDTO tokenDTO = SharedManager.newInstance().getObject(Constant.TOKEN, TokenDTO.class);
        String token = "";

        try {
            token = tokenDTO.getAccess_token();
            //NotificationToken = SharedManager.newInstance().getCashValue(Constants.NotificationToken);
        } catch (Exception e) {
        }

         if (!TextUtils.isEmpty(token) && token != null) {
            headers.put("Authorization", "Bearer " + token);
           // headers.put(Constants.NotificationMessage, NotificationToken);
        }

        headers.put("Content-Type", "application/json");

        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(14L, TimeUnit.SECONDS)
                .writeTimeout(14L, TimeUnit.SECONDS)
                .readTimeout(14L, TimeUnit.SECONDS);


        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers

                Request.Builder requestBuilder = original.newBuilder(); // <-- this is the important line

                for (Map.Entry<String, String> pairs : headers.entrySet()) {
                    if (pairs.getValue() != null) {
                        requestBuilder.header(pairs.getKey(), pairs.getValue());
                    }
                }

                requestBuilder.method(original.method(), original.body());
                Request request = requestBuilder.build();

                return chain.proceed(request);

            }
        };

        HttpLoggingInterceptor interceptor2 = new HttpLoggingInterceptor();
        interceptor2.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.interceptors().add(interceptor);
        httpClient.interceptors().add(interceptor2);
//        httpClient.addInterceptor(interceptor);

        return httpClient.build();

    }

    public static void onNext(MainView mView, retrofit2.Response<JsonObject> response) {
        ResponseBody str = response.errorBody();
        try {
            JSONObject mJson = new JSONObject(str.string());
            mView.onError(Constant.NONHTTP_EXCEPTION);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private CompositeDisposable configureComposite() {
        if (mCompositeSubscribtion == null || mCompositeSubscribtion.isDisposed()) {
            return mCompositeSubscribtion = new CompositeDisposable();
        }
        return mCompositeSubscribtion;
    }

    protected void unSubscribeAll() {
        if (mCompositeSubscribtion != null) {
            mCompositeSubscribtion.dispose();
            mCompositeSubscribtion.clear();
        }
    }

    public static ApiService getRetrofit() {
        /*if (NetworkUtils.isConnected()) {*/
            return new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL_HTTP)
                    .client(get_HTTPClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(ApiService.class);
       /* } else {
            //    Toast.makeText(App.mContext,, Toast.LENGTH_LONG).show();
             OrthoUtils.makeToast(App.mContext,App.mContext.getResources().getString(R.string.no_connection), Constants.FANCYERROR);
            return null;
        }*/
    }

    public  <T> void subscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.computation())
                .subscribe(observer);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        unSubscribeAll();
    }

    public String OnError(Throwable e) {

        if (e instanceof HttpException) {
            HttpException error = (HttpException) e;
           // DefaultModel response = ((DefaultModel) error.response().body());

        }


        return e.getMessage();
    }
}

