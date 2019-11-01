package com.azheng.sunnyweather;

import android.app.Application;
import android.content.Context;

import com.azheng.sunnyweather.data.CityRepository;

public class SunnyApplication extends Application {
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        init();
    }

    private void init() {
        CityRepository.getCityIns().init(mAppContext);
    }


    public static Context getAppContext() {
        return mAppContext;
    }
}
