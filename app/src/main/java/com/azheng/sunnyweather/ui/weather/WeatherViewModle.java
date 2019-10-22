package com.azheng.sunnyweather.ui.weather;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.azheng.sunnyweather.data.WeatherRepository;
import com.azheng.sunnyweather.data.model.Weather;
import com.azheng.sunnyweather.util.NetCallback;

public class WeatherViewModle extends ViewModel implements NetCallback<Weather> {
    private WeatherRepository mRespository;

    public MutableLiveData<String> picUrl;
    public MutableLiveData<Weather> weather;
    public MutableLiveData<Boolean> refreshing;

    public WeatherViewModle(WeatherRepository repository) {
        this.mRespository = repository;
    }

    public void getWeather() {
        mRespository.getWeather(this);
    }

    public void onRefresh(){
        mRespository.getWeather(this);
    }

    @Override
    public void onSucess(Weather data) {
        weather.setValue(data);
    }

    @Override
    public void onFailure() {

    }
}