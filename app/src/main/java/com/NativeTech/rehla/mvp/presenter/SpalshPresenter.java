package com.NativeTech.rehla.mvp.presenter;

import android.annotation.SuppressLint;

import com.NativeTech.rehla.Utills.Utils;
import com.NativeTech.rehla.model.DataManager;
import com.NativeTech.rehla.mvp.view.SplashView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SpalshPresenter {

    SplashView view;

    public SpalshPresenter(SplashView view) {
        this.view = view;
    }
    @SuppressLint("CheckResult")
    public void getCustomerProfile() {

            DataManager.getInstance().getCustomerProfile()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> view.onShowLoader())
                    .doOnError(throwable -> Utils.showError(view,throwable))
                    .doOnComplete(() -> view.onDismissLoader())
                    .subscribe(loginDTOObjectModel ->
                            {
                                view.onprofile(loginDTOObjectModel);
                                view.onDismissLoader();
                            }
                            , throwable -> {});

        }

}
