package com.azheng.sunnyweather.ui.weather;

import androidx.lifecycle.ViewModel;

import com.azheng.sunnyweather.data.WeatherRepository;

public class WeatherViewModle extends ViewModel {
    private WeatherRepository mRespository;

    public WeatherViewModle(WeatherRepository repository) {
        this.mRespository = repository;
    }

    public void getWeather() {

    }
}