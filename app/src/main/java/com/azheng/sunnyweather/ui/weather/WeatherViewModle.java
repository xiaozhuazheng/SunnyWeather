package com.azheng.sunnyweather.ui.weather;

import com.azheng.sunnyweather.data.WeatherRepository;
import com.azheng.sunnyweather.data.model.Weather;
import com.azheng.sunnyweather.util.NetCallback;
import com.azheng.sunnyweather.util.ToolUnit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeatherViewModle extends ViewModel implements NetCallback<Weather> {
    private WeatherRepository mRespository;

    public MutableLiveData<Weather> weather;
    public MutableLiveData<Boolean> refreshing;
    public MutableLiveData<Boolean> weatherSucess;

    public WeatherViewModle(WeatherRepository repository) {
        this.mRespository = repository;
        weather = new MutableLiveData<>();
        refreshing = new MutableLiveData<>();
        weatherSucess = new MutableLiveData<>();
    }

    public void getWeather() {
        refreshing.setValue(true);
        mRespository.getWeather(this);
    }

    public void onRefresh(){
        refreshing.setValue(true);
        mRespository.getWeather(this);
    }

    @Override
    public void onSucess(Weather data) {
        refreshing.setValue(false);
        weatherSucess.setValue(true);
        weather.setValue(data);
    }

    @Override
    public void onFailure() {
        refreshing.setValue(false);
        weatherSucess.setValue(false);
        ToolUnit.toast("获取天气数据失败");
    }
}