package com.azheng.sunnyweather.util;

import android.content.Context;

import com.azheng.sunnyweather.data.CityRepository;
import com.azheng.sunnyweather.data.WeatherRepository;
import com.azheng.sunnyweather.ui.city.CityModleFactory;
import com.azheng.sunnyweather.ui.weather.WeatherModleFactory;

public class InjectorUtil {

    public static WeatherModleFactory getWeatherModelFactory(Context context) {
        return new WeatherModleFactory(WeatherRepository.getInsances(),context);
    }

    public static CityModleFactory getCityModelFactory(Context context) {
        return new CityModleFactory(CityRepository.getCityIns(),context);
    }
}
