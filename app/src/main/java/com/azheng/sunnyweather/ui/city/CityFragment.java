package com.azheng.sunnyweather.ui.city;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azheng.sunnyweather.R;
import com.azheng.sunnyweather.databinding.FragmentCityBinding;
import com.azheng.sunnyweather.ui.weather.WeatherViewModle;
import com.azheng.sunnyweather.util.InjectorUtil;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class CityFragment extends Fragment {
    private Activity activity;
    private FragmentCityBinding binding;
    private CityViewModle viewModel;

    public CityFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 1、对布局需要绑定的内容进行加载
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_city, container, false);
        // 2、获取到视图
        View view = binding.getRoot();

        viewModel = ViewModelProviders.of(this, InjectorUtil.getCityModelFactory()).get(CityViewModle.class);

        binding.setViewModel(viewModel);

        viewModel.getAddCity();
        return view;
    }
}
