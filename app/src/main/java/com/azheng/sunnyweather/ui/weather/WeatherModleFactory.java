package com.azheng.sunnyweather.ui.weather;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.azheng.sunnyweather.data.WeatherRepository;

public class WeatherModleFactory extends ViewModelProvider.NewInstanceFactory {
    private WeatherRepository repository;

    public  WeatherModleFactory(WeatherRepository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WeatherViewModle(repository);
    }
}
