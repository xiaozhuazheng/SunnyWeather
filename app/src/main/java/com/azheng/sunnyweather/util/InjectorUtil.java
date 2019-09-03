package com.azheng.sunnyweather.util;

import androidx.lifecycle.ViewModelProvider;

import com.azheng.sunnyweather.data.WeatherRepository;
import com.azheng.sunnyweather.ui.weather.WeatherModleFactory;

public class InjectorUtil {

    public static WeatherModleFactory getWeatherModelFactory() {
        return new WeatherModleFactory(WeatherRepository.getInsances());
    }
}
