package com.azheng.sunnyweather.ui.city;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azheng.sunnyweather.R;
import com.azheng.sunnyweather.databinding.FragmentCityBinding;
import com.azheng.sunnyweather.ui.weather.WeatherViewModle;
import com.azheng.sunnyweather.util.InjectorUtil;
import com.azheng.sunnyweather.util.weight.FloatView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class CityFragment extends Fragment {
    private Context context;
    private FragmentCityBinding binding;
    private CityViewModle viewModel;

    public CityFragment(Context context) {
        this.context = context;
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

        viewModel = ViewModelProviders.of(this, InjectorUtil.getCityModelFactory(context)).get(CityViewModle.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.getAddCity();

        FloatView floatView = view.findViewById(R.id.float_view);
        viewModel.setFloatView(floatView);

        return view;
    }
}
