package com.azheng.sunnyweather.ui.weather;

import android.content.Context;

import com.azheng.sunnyweather.data.WeatherRepository;
import com.azheng.sunnyweather.data.model.Weather;
import com.azheng.sunnyweather.ui.MainActivity;
import com.azheng.sunnyweather.util.NetCallback;
import com.azheng.sunnyweather.util.ToolUnit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeatherViewModle extends ViewModel implements NetCallback<Weather> {
    private WeatherRepository mRespository;
    private Context context;

    public MutableLiveData<Weather> weather;
    public MutableLiveData<Boolean> refreshing;
    public MutableLiveData<Boolean> weatherSucess;

    public WeatherViewModle(WeatherRepository repository,Context context) {
        this.mRespository = repository;
        weather = new MutableLiveData<>();
        refreshing = new MutableLiveData<>();
        weatherSucess = new MutableLiveData<>();
        this.context = context;
    }

    public void getWeather() {
        refreshing.setValue(true);
        mRespository.getWeatherbyLocation(this);
    }

    public void onRefresh(){
        refreshing.setValue(true);
        mRespository.refreshWeather();
    }

    @Override
    public void onSucess(Weather data) {
        refreshing.setValue(false);
        weatherSucess.setValue(true);
        weather.setValue(data);
        ((MainActivity)context).setToobarTitle(mRespository.getLocationCity());
    }

    @Override
    public void onFailure(String msg) {
        refreshing.setValue(false);
        weatherSucess.setValue(false);
        ToolUnit.toast(msg);
        ((MainActivity)context).setToobarTitle(mRespository.getLocationCity());
    }
}