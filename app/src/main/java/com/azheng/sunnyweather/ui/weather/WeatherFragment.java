package com.azheng.sunnyweather.ui.weather;

import android.app.Activity;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.azheng.sunnyweather.R;
import com.azheng.sunnyweather.databinding.FragmentWeatherBinding;
import com.azheng.sunnyweather.util.InjectorUtil;

public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;
    private WeatherViewModle viewModel;
    private Activity activity;

    public WeatherFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.fragment_weather);
        viewModel = ViewModelProviders.of(this, InjectorUtil.getWeatherModelFactory()).get(WeatherViewModle.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.getWeather();
    }
}
