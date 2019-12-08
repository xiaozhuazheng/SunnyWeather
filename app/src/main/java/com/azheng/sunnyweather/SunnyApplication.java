package com.azheng.sunnyweather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.azheng.sunnyweather.data.CityRepository;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

public class SunnyApplication extends LitePalApplication {
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        init();
    }

    private void init() {
        LitePal.initialize(this);
        SQLiteDatabase db = Connector.getDatabase();
        CityRepository.getCityIns().init(mAppContext);
    }


    public static Context getAppContext() {
        return mAppContext;
    }
}
