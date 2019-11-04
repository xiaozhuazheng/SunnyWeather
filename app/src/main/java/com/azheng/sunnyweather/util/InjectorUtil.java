package com.azheng.sunnyweather.util;

import com.azheng.sunnyweather.data.CityRepository;
import com.azheng.sunnyweather.data.WeatherRepository;
import com.azheng.sunnyweather.ui.city.CityModleFactory;
import com.azheng.sunnyweather.ui.weather.WeatherModleFactory;

public class InjectorUtil {

    public static WeatherModleFactory getWeatherModelFactory() {
        return new WeatherModleFactory(WeatherRepository.getInsances());
    }

    public static CityModleFactory getCityModelFactory() {
        return new CityModleFactory(CityRepository.getCityIns());
    }
}
