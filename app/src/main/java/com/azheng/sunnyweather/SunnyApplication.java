package com.azheng.sunnyweather;

import android.app.Application;
import android.content.Context;

import com.azheng.sunnyweather.data.net.WeatherNetIns;

public class SunnyApplication extends Application {
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
    }


    public static Context getAppContext() {
        return mAppContext;
    }
}
