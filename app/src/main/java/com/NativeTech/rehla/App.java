package com.NativeTech.rehla;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;


import com.NativeTech.rehla.Utills.Constant;
import com.NativeTech.rehla.Utills.LocalHelper;
import com.NativeTech.rehla.model.data.source.preferences.SharedManager;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class App extends Application {
    public static Context mContext;
    public static int dialogwidth=200;
    public static int dialogHiegt=120;

    private String font = "fonts/cairo.ttf";

    private Typeface mFontBold;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
       // Fabric.with(this, new Crashlytics());
        mContext = this;


        // String token = FirebaseInstanceId.getInstance().getToken();
       // SharedManager.newInstance().CashValue(Constants.NotificationToken,token);
    }


    @Override
    protected void attachBaseContext(Context base) {

        try
        {
            String lang = SharedManager.newInstance().getCashValue(Constant.LANG);
            if (lang == null)
                lang = Constant.ENGLISH;

            if (lang.equals(Constant.ARABIC)) {

                super.attachBaseContext(LocalHelper.onAttach(base, "ar"));

            }
            else {

                super.attachBaseContext(LocalHelper.onAttach(base, "en"));
            }

        }
        catch (Exception e){
            super.attachBaseContext(LocalHelper.onAttach(base, "en"));
        }
        MultiDex.install(this);
    }

}
