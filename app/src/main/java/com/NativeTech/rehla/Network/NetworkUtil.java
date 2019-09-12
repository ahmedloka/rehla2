package com.NativeTech.rehla.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

import static com.NativeTech.rehla.Utills.Constant.BASE_URL_HTTP;
import static com.NativeTech.rehla.Utills.Constant.BASE_URL_HTTP_Get_Address;


public class NetworkUtil {

//    public static RetrofitInterface getRetrofit(){
//
//
//
//            return RetrofitClient.getClient().create(RetrofitInterface.class);
//
//
//
//      /*  RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
//
//       return new Retrofit.Builder()
//                .baseUrl(Constant.BASE_URL_HTTP)
//                .addCallAdapterFactory(rxAdapter)
//                .addConverterFactory(SimpleXmlConverterFactory.create())
//                .build().create(RetrofitInterface.class);*/
//
//    }
    /*public static RetrofitInterface getRetrofit2(){

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_HTTP)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(RetrofitInterface.class);

    }
   */ public static RetrofitInterface getRetrofit_Get_Address(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .method(original.method(),original.body());
            return  chain.proceed(builder.build());

        });

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_HTTP_Get_Address)
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface.class);


    }
    public static RetrofitInterface getRetrofit(String lang, String Content_Type) {

        //String credentials = email + ":" + password;
        //UserLogin user =new UserLogin() ;
        //user.setEmailEncoded(Base64.encodeToString(email.getBytes(),Base64.NO_WRAP));
        //user.setPasswordEncoded(Base64.encodeToString(password.getBytes(),Base64.NO_WRAP));
        //String emailEn = Base64.encodeToString(lang.getBytes(), Base64.NO_WRAP);
        //String passwordEn = Base64.encodeToString(password.getBytes(), Base64.NO_WRAP);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .addHeader("Lang",lang)
                    .addHeader("Content-Type",Content_Type)
                    .method(original.method(),original.body());
            return  chain.proceed(builder.build());

        });

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_HTTP)
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface.class);
    }

    public static RetrofitInterface getRetrofit(String lang,String Token, String Content_Type) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .addHeader("Lang",lang)
                    .addHeader("Token",Token)
                    .addHeader("Content-Type",Content_Type)
                    .method(original.method(),original.body());
            return  chain.proceed(builder.build());

        });

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_HTTP)
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface.class);
    }

    public static RetrofitInterface getRetrofit5(String lang,String Token, String Last_Index,String Paginate,String Direction) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .addHeader("Lang",lang)
                    .addHeader("Token",Token)
                    .addHeader("Last-Index",Last_Index)
                    .addHeader("Paginate",Paginate)
                    .addHeader("Direction",Direction)
                    .method(original.method(),original.body());
            return  chain.proceed(builder.build());

        });

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_HTTP)
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface.class);
    }


    public static RetrofitInterface getRetrofit(String lang) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .addHeader("Lang",lang)
                    .method(original.method(),original.body());
            return  chain.proceed(builder.build());

        });

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_HTTP)
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface.class);
    }
    public static RetrofitInterface getRetrofitByToken(String token) {

        //String credentials = email + ":" + password;
        //UserLogin user =new UserLogin() ;
        //user.setEmailEncoded(Base64.encodeToString(email.getBytes(),Base64.NO_WRAP));
        //user.setPasswordEncoded(Base64.encodeToString(password.getBytes(),Base64.NO_WRAP));
        //String emailEn = Base64.encodeToString(lang.getBytes(), Base64.NO_WRAP);
        //String passwordEn = Base64.encodeToString(password.getBytes(), Base64.NO_WRAP);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        HttpLoggingInterceptor interceptor2 = new HttpLoggingInterceptor();
        interceptor2.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        //System.setProperty("http.keepAlive", "false");

        String tokenValue="Bearer "+token;
        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()

                    .addHeader("Authorization",tokenValue)
                    .method(original.method(),original.body());
            return  chain.proceed(builder.build());

        }).connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_HTTP)
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(RetrofitInterface.class);
    }
    public static RetrofitInterface getRetrofit3(String lang,String Token) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .addHeader("Lang",lang)
                    .addHeader("Token",Token)
                    .method(original.method(),original.body());
            return  chain.proceed(builder.build());

        });

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_HTTP)
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface.class);
    }

    public static RetrofitInterface getRetrofit2(String ContentType,String Lang,String Token) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
                    .addHeader("Content-Type",ContentType)
                    .addHeader("Lang",Lang)
                    .addHeader("Token",Token)
                    .method(original.method(),original.body());
            return  chain.proceed(builder.build());

        });

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_HTTP)
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface.class);
    }
//    public static RetrofitInterface getRetrofit2(){
//
//        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
//
//        return new Retrofit.Builder()
//                .baseUrl(BASE_URL_HTTP)
//                .addCallAdapterFactory(rxAdapter)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build().create(RetrofitInterface.class);
//
//    }


}