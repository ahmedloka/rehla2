package com.NativeTech.rehla.application;

import android.app.Application;

/**
 * Created by AwesomePC on 14-Jul-18.
 */
class CalendarApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppManager.getInstance(this);
    }
}
