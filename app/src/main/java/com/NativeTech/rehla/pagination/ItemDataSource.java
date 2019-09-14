package com.NativeTech.rehla.pagination;

import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.NativeTech.rehla.Network.RetrofitClient;
import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.model.DataManager;
import com.NativeTech.rehla.model.chat.ChateResponse;
import com.NativeTech.rehla.model.chat.Model;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Model> {


    private Context mContext;


    //the size of a page that we want
    public int PAGE_SIZE;

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 0;

    private String token;


    public ItemDataSource() {
        token = DataManager.getInstance().getCashedAccessToken().getAccess_token();

    }

    //this will be called once to load the initial data
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Model> callback) {
        RetrofitClient.getInstance(token)
                .getApi()
                .getChatMessages(FIRST_PAGE, Constant.PartnerId)
                .enqueue(new Callback<ChateResponse>() {
                    @Override
                    public void onResponse(Call<ChateResponse> call, Response<ChateResponse> response) {
                        Log.d("RESPONSE", "onFailure: " + response.body().getModel().get(1).getMessage());
                        if (response.isSuccessful()) {
                            List<Model> chatListList = response.body().getModel();
                            Collections.reverse(chatListList);
                            callback.onResult(chatListList, null, FIRST_PAGE + 1);
                        }else {
                            response.raw().close();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChateResponse> call, Throwable t) {
                        Log.d("RESPONSE", "onFailure: " + t.getCause() + t.getMessage());
                    }
                });

    }

    //this will load the previous page
    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Model> callback) {
        RetrofitClient.getInstance(token)
                .getApi()
                .getChatMessages(params.key, Constant.PartnerId)
                .enqueue(new Callback<ChateResponse>() {
                    @Override
                    public void onResponse(Call<ChateResponse> call, Response<ChateResponse> response) {
                      //  Log.d("RESPONSE", "onFailure: " + response.body().getModel().get(1).getMessage());
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        if (response.isSuccessful()) {

                            //passing the loaded data
                            //and the previous page key
                            List<Model> chatListList = response.body().getModel();
                            Collections.reverse(chatListList);
                            callback.onResult(chatListList, adjacentKey);
                        }else {
                            response.raw().close();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChateResponse> call, Throwable t) {
                        Log.d("RESPONSE", "onFailure: " + t.getCause() + t.getMessage());
                    }
                });
        ;


    }

    //this will load the next page
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Model> callback) {
        RetrofitClient.getInstance(token)
                .getApi()
                .getChatMessages(params.key, Constant.PartnerId)
                .enqueue(new Callback<ChateResponse>() {
                    @Override
                    public void onResponse(Call<ChateResponse> call, Response<ChateResponse> response) {
                      //  Log.d("RESPONSE", "onFailure: " + response.body().getModel().get(1).getMessage());
                        if (response.isSuccessful()) {
                            //if the response has next page
                            //incrementing the next page number
                            Integer key = PAGE_SIZE > params.key ? params.key + 1 : null;
                            //passing the loaded data and next page value
                            List<Model> chatListList = response.body().getModel();
                            Collections.reverse(chatListList);
                            callback.onResult(chatListList, params.key + 1);
                        }else {
                            response.raw().close();
                        }
                    }

                    @Override
                    public void onFailure(Call<ChateResponse> call, Throwable t) {
                        Log.d("RESPONSE", "onFailure: " + t.getCause() + t.getMessage());
                    }
                });
        ;

    }

}

