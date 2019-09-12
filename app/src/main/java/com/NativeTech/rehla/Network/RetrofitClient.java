package com.NativeTech.rehla.Network;


import com.NativeTech.rehla.Utills.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.NativeTech.rehla.Utills.Constant.BASE_URL_HTTP;


public class RetrofitClient {

    private static final String BASE_URL = Constant.BASE_URL_HTTP;
    private static RetrofitClient mInstance;
    private Retrofit retrofit;


    private RetrofitClient(String token) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        String newToken = "Bearer " + token;
        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .addHeader("Authorization", newToken)
                    .method(original.method(), original.body());
            return chain.proceed(builder.build());

        });

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized RetrofitClient getInstance(String token) {
        if (mInstance == null) {
            mInstance = new RetrofitClient(token);
        }
        return mInstance;
    }

    public RetrofitInterface getApi() {
        return retrofit.create(RetrofitInterface.class);
    }
}