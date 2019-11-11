package com.azheng.sunnyweather.ui.city;

import com.azheng.sunnyweather.data.CityRepository;
import com.azheng.sunnyweather.data.model.CityModel;
import com.azheng.sunnyweather.util.NetCallback;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CityViewModle extends ViewModel implements NetCallback<ArrayList<CityModel>> {
    private CityRepository repository;

    public MutableLiveData<Boolean> hasCity;
    public MutableLiveData<ArrayList<CityModel>> mCityList;

    public CityViewModle(CityRepository repository) {
        this.repository = repository;

        hasCity = new MutableLiveData<>();
        mCityList = new MutableLiveData<>();
    }

    public void getAddCity(){
        repository.getAddCity(this);
    }

    @Override
    public void onSucess(ArrayList<CityModel> data) {
        if (data != null && data.size() > 0){
            hasCity.setValue(true);
            mCityList.setValue(data);
        }
    }

    @Override
    public void onFailure() {

    }

    public void onFloatClick(){
        //去添加城市界面

    }
}
