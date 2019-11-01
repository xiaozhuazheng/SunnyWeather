package com.azheng.sunnyweather.ui.weather;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azheng.sunnyweather.R;
import com.azheng.sunnyweather.databinding.FragmentWeatherBinding;
import com.azheng.sunnyweather.util.InjectorUtil;
import com.azheng.sunnyweather.util.permissions.OnPermission;
import com.azheng.sunnyweather.util.permissions.Permission;
import com.azheng.sunnyweather.util.permissions.XXPermissions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;
    private WeatherViewModle viewModel;
    private Activity activity;

    public WeatherFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermission();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 1、对布局需要绑定的内容进行加载
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false);
        // 2、获取到视图
        View view = binding.getRoot();

        viewModel = ViewModelProviders.of(this, InjectorUtil.getWeatherModelFactory()).get(WeatherViewModle.class);

        binding.setViewModel(viewModel);
        binding.setResId(R.color.colorPrimary);
        binding.setLifecycleOwner(this);

        viewModel.getWeather();
        return view;
    }

    /**
     *权限获取
     */
    private void initPermission() {
        XXPermissions.with(getActivity())
                .permission(Permission.Group.STORAGE,Permission.Group.LOCATION)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            //权限申请通过
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        //有权限没通过
                        XXPermissions.gotoPermissionSettings(getActivity());
                    }
                });
    }
}
