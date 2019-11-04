package com.azheng.sunnyweather.ui.city;

import com.azheng.sunnyweather.data.CityRepository;
import com.azheng.sunnyweather.data.model.CityModel;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CityViewModle extends ViewModel {
    private CityRepository repository;

    public MutableLiveData<Boolean> hasCity;
    public MutableLiveData<ArrayList<CityModel>> mCityList;

    public CityViewModle(CityRepository repository) {
        this.repository = repository;

        hasCity = new MutableLiveData<>();
        mCityList = new MutableLiveData<>();
    }

    public void getAddCity(){
        ArrayList<CityModel> list = repository.getAddCity();
        if (list != null){
            hasCity.setValue(true);
            mCityList.setValue(list);
        }
    }
}
