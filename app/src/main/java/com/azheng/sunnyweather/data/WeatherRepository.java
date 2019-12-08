package com.azheng.sunnyweather.data;

import com.azheng.sunnyweather.data.model.HeWeather6;
import com.azheng.sunnyweather.data.net.WeatherApi;
import com.azheng.sunnyweather.data.net.WeatherNetIns;
import com.azheng.sunnyweather.util.Config;
import com.azheng.sunnyweather.util.NetCallback;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 获取天气数据的仓库，可以添加从网络/缓存获取的逻辑，
 * 这里采用实时天气数据，没有写入缓存
 */

public class WeatherRepository {
    private static WeatherRepository mIns;
    private WeatherApi mWeatherApi;
    private NetCallback mCallback;

    private String mLocationCity;
    public String getLocationCity() {
        return mLocationCity;
    }

    private WeatherRepository(){
        mWeatherApi = WeatherNetIns.getWeatherApi();
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

    /**
     * 获取定位并返回天气数据
     */
    public void getWeatherbyLocation(NetCallback callback){
        mCallback = callback;
    }

    public void locationSucess(String city){
        mLocationCity = city;
        mCallback.onFailure("定位到" + mLocationCity);
        doWeather();
    }

    public void locationFaile(String city){
        mLocationCity = city;
        mCallback.onFailure("定位失败，加载默认城市");
        doWeather();
    }

    public void refreshWeather() {
        doWeather();
    }

    private void doWeather(){
        Observable<HeWeather6> observable = mWeatherApi.mWeatherAPI(mLocationCity,Config.KEY);
        observable.observeOn(AndroidSchedulers.mainThread())//事件消费线程
                .subscribeOn(Schedulers.newThread())//事件发生的线程
                .subscribe(new Observer<HeWeather6>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HeWeather6 heweather6) {
                        mCallback.onSucess(heweather6.HeWeather6.get(0));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mCallback.onFailure("获取天气数据失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
