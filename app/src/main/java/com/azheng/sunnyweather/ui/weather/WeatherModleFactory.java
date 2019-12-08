package com.azheng.sunnyweather.ui.weather;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.azheng.sunnyweather.data.WeatherRepository;

public class WeatherModleFactory extends ViewModelProvider.NewInstanceFactory {
    private WeatherRepository repository;
    private Context context;

    public  WeatherModleFactory(WeatherRepository repository, Context context){
        this.repository = repository;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WeatherViewModle(repository,context);
    }
}
