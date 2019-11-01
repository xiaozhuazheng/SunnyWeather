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

    public void getWeather(NetCallback callback) {
        String city = CityRepository.getCityIns().getLocalCity();
        Observable<HeWeather6> observable = mWeatherApi.mWeatherAPI(city,Config.KEY);
        observable.observeOn(AndroidSchedulers.mainThread())//事件消费线程
                .subscribeOn(Schedulers.newThread())//事件发生的线程
                .subscribe(new Observer<HeWeather6>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HeWeather6 heweather6) {
                        callback.onSucess(heweather6.HeWeather6.get(0));
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailure();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
