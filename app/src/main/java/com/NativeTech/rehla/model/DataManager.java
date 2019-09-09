package com.NativeTech.rehla.model;

import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.model.data.dto.Models.LoginModel.LoginResponseModel;
import com.NativeTech.rehla.model.data.dto.TokenDTO;
import com.NativeTech.rehla.model.data.source.network.ApiHelper;
import com.NativeTech.rehla.model.data.source.network.ApiService;
import com.NativeTech.rehla.model.data.source.preferences.SharedManager;

import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.Query;

public class DataManager {
    private static DataManager mInstance=null;
    private ApiService apiService;
    private SharedManager sharedManager;


    private DataManager() {
        apiService = ApiHelper.getRetrofit();
        sharedManager=SharedManager.newInstance();
    }

    public static DataManager getInstance() {
        if (mInstance == null) {
            synchronized (DataManager.class) {

                mInstance = new DataManager();

            }
        }
        else if(mInstance.apiService==null || mInstance.sharedManager==null)
        {
            mInstance = new DataManager();
        }
        return mInstance;
    }



  /*  public LoginDTO getCashedUserData() {
        return sharedManager.getObject(Constant.USER_DATA,LoginDTO.class);
    }*/

    public TokenDTO getCashedAccessToken() {
        if(sharedManager.getObject(Constant.TOKEN,TokenDTO.class)!=null)
        {
            return sharedManager.getObject(Constant.TOKEN,TokenDTO.class);
        }
        else
        {
            return new TokenDTO();
        }
    }

/*
    public void saveUserData(LoginDTO loginDTO) {

        sharedManager.saveObject(loginDTO,Constant.USER_DATA);
    }
*/

    public void saveAccessToken(TokenDTO tokenDTO) {

        sharedManager.saveObject(tokenDTO,Constant.TOKEN);
        refreshToken();
    }

    private void refreshToken() {
        apiService = ApiHelper.getRetrofit();
    }

    public void removeToken() {
        sharedManager.removeCashed(Constant.TOKEN);
        refreshToken();
    }

    public Observable<LoginResponseModel> getCustomerProfile() {
        return apiService.getCustomerProfile();
    }

   /* public void saveMetaData(MetaDataDTO metaDataDTO) {
        sharedManager.saveObject(metaDataDTO,Constant.META_DATA);
    }*/

}
