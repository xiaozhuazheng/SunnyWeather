package com.azheng.sunnyweather.ui.city;

import com.azheng.sunnyweather.data.CityRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CityModleFactory extends ViewModelProvider.NewInstanceFactory{
    private CityRepository repository;

    public  CityModleFactory(CityRepository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CityViewModle(repository);
    }
}
