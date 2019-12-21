package com.azheng.sunnyweather.data;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.azheng.sunnyweather.data.db.City;
import com.azheng.sunnyweather.data.db.DBData;
import com.azheng.sunnyweather.data.db.DBManager;
import com.azheng.sunnyweather.data.model.CityModel;
import com.azheng.sunnyweather.data.model.HeWeather6;
import com.azheng.sunnyweather.data.model.Weather;
import com.azheng.sunnyweather.data.net.WeatherApi;
import com.azheng.sunnyweather.data.net.WeatherNetIns;
import com.azheng.sunnyweather.util.Config;
import com.azheng.sunnyweather.util.NetCallback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
* 获取城市仓库
 * 1、定位
 * 2、数据库查找
 * 3、历史添加城市
*/
public class CityRepository {
    private static CityRepository mIns;
    private String mLocalCity = "北京";
    private WeatherApi mWeatherApi;

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private CityRepository() {
        mWeatherApi = WeatherNetIns.getWeatherApi();
    }

    public static CityRepository getCityIns(){
        if (mIns == null){
            synchronized (CityRepository.class){
                if (mIns == null){
                    mIns = new CityRepository();
                }
            }
        }
        return mIns;
    }

    public void init(Context context){
        mlocationClient = new AMapLocationClient(context);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(10000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);

        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除

        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0 && !amapLocation.getCity().equals("")) {
                        //定位成功回调信息，设置相关消息
                        mLocalCity = amapLocation.getCity();
                        //定位后停止定位服务
                        mlocationClient.stopLocation();
                        //更新天气数据
                        WeatherRepository.getInsances().locationSucess(mLocalCity);
                        //加入数据库
                        //DBManager.getIns().putCity(mLocalCity);
                    } else {
                        //定位失败
                        WeatherRepository.getInsances().locationFaile(mLocalCity);
                    }
                }
            }
        });
        //启动定位
        mlocationClient.startLocation();
    }

    public void getAddCity(NetCallback callback){
        ArrayList<CityModel> list = new ArrayList<>();

        Observer<HeWeather6> observer = new Observer<HeWeather6>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(HeWeather6 heWeather6) {
                Weather weather = heWeather6.HeWeather6.get(0);
                CityModel cityModel = new CityModel();
                if (weather.basic != null) {
                    cityModel.setCityName(weather.basic.location);
                }
                if (weather.now != null) {
                    cityModel.setCityWeather(weather.now.cond_txt);
                    cityModel.setCityTmp(weather.now.getTmp());
                }
                list.add(cityModel);
            }

            @Override
            public void onError(Throwable e) {
                callback.onFailure("获取已添加城市天气失败");
            }

            @Override
            public void onComplete() {
                if (list.size() < 1){
                    callback.onFailure("无添加城市");
                } else {
                    callback.onSucess(list);
                }
            }
        };

        Observable.defer(() -> {
            List<City> citys = DBManager.getIns().getAllCity();
            return Observable.fromIterable(citys);
        }).map(city -> city.getName())
                .flatMap(new Function<String, Observable<HeWeather6>>() {
                    @Override
                    public Observable<HeWeather6> apply(String s) throws Exception {
                        return mWeatherApi.mWeatherAPI(s,Config.KEY);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer);
    }

    /**
     * DB provicens
     */
    public void getProvince(NetCallback callback){
        Observable.defer(() -> {
            return Observable.fromIterable(DBManager.getIns().loadProvinces());
        }).toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new SingleObserver<List<DBData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<DBData> list) {
                        callback.onSucess(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    /**
     * DB city
     */
    public void getCity(int Pid , NetCallback callback){
        Observable.defer(() -> {
            return Observable.fromIterable(DBManager.getIns().loadCities(Pid));
        }).toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new SingleObserver<List<DBData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<DBData> list) {
                        callback.onSucess(list);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

}
