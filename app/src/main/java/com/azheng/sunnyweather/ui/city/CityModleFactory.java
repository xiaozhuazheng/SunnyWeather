package com.azheng.sunnyweather.ui.city;

import android.content.Context;

import com.azheng.sunnyweather.data.CityRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CityModleFactory extends ViewModelProvider.NewInstanceFactory{
    private CityRepository repository;
    private Context context;

    public  CityModleFactory(CityRepository repository,Context context){
        this.repository = repository;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CityViewModle(repository,context);
    }
}
