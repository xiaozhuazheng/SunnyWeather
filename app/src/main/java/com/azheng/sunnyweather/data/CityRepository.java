package com.azheng.sunnyweather.data;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.azheng.sunnyweather.data.model.CityModel;

import java.util.ArrayList;

/**
* 获取城市仓库
 * 1、定位
 * 2、数据库查找
 * 3、历史添加城市
*/
public class CityRepository {
    private static CityRepository mIns;
    private String mLocalCity;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private CityRepository() {}

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
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        mLocalCity = amapLocation.getCity();
                        //定位后停止定位服务
                        mlocationClient.stopLocation();
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        });
        //启动定位
        mlocationClient.startLocation();
    }

    public String getLocalCity(){
        if (mLocalCity == null){
            //返回默认城市
            return "深圳";
        } else {
            return mLocalCity;
        }
    }

    public ArrayList<CityModel> getAddCity(){
        //先从数据库获取添加的城市，在请求天气数据

        ArrayList<CityModel> list = new ArrayList<>();
        for (int i = 0;i< 10;i++){
            CityModel city = new CityModel();
            city.setCityName("深圳");
            city.setCityWeather("多云");
            city.setCityTmp("25");
            list.add(city);
        }
        return list;
    }

}
