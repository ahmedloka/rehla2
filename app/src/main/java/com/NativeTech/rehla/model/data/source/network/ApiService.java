package com.NativeTech.rehla.model.data.source.network;


import com.NativeTech.rehla.model.data.dto.Models.LoginModel.LoginResponseModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("Users/GetUserProfile")
    Observable<LoginResponseModel> getCustomerProfile();

}
