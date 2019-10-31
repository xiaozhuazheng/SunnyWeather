package com.azheng.sunnyweather.util;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.azheng.sunnyweather.R;
import com.azheng.sunnyweather.data.model.DailyForecast;
import com.azheng.sunnyweather.data.model.Weather;
import com.azheng.sunnyweather.databinding.ForecastItemBinding;
import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class DataBingAdapters {

    @BindingAdapter(value = "bind:loadBingPic")
    public static void loadBingPic(ImageView iv ,String url){
        if (url != null){
            Glide.with(iv.getContext()).load(url).into(iv);
        }
    }

    @BindingAdapter("bind:colorSchemeResources")
    public static void colorSchemeResources(SwipeRefreshLayout swip , int resId) {
        swip.setColorSchemeResources(resId);
    }

    @BindingAdapter("bind:showForecast")
    public static void showForecast(LinearLayout linearLayout , Weather weather){
        if (weather == null){
            return;
        }
        linearLayout.removeAllViews();
        for (DailyForecast forecast: weather.daily_forecast) {
            View view = LayoutInflater.from(linearLayout.getContext()).inflate(R.layout.forecast_item,linearLayout,false);
            ForecastItemBinding binding = DataBindingUtil.bind(view);
            binding.setForecast(forecast);
            linearLayout.addView(view);
        }
    }
}
