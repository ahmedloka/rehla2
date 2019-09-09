package com.NativeTech.rehla.mvp.view;

import com.NativeTech.rehla.model.data.dto.Models.LoginModel.LoginResponseModel;
import com.NativeTech.rehla.model.data.dto.geniric.MainView;

public interface SplashView extends MainView {

   void onprofile(LoginResponseModel loginResponseModel);
}
