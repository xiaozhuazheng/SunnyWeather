package com.azheng.sunnyweather.data;

public class WeatherRepository {
    private static WeatherRepository mIns;

    private WeatherRepository(){

    }

    public static  WeatherRepository  getInsances(){
         if (mIns == null) {
            synchronized (WeatherRepository.class){
                if (mIns == null){
                    mIns = new WeatherRepository();
                }
            }
        }
        return mIns;
    }
}
