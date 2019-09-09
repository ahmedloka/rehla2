package com.NativeTech.rehla.Network;


import retrofit2.Retrofit;

import static com.NativeTech.rehla.Utills.Constant.BASE_URL_HTTP;


class RetrofitClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_HTTP)
                    .build();
        }
        return retrofit;
    }
}