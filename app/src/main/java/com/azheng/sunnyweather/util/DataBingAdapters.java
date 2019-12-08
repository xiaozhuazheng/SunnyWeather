package com.azheng.sunnyweather.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.azheng.sunnyweather.R;
import com.azheng.sunnyweather.SunnyApplication;
import com.azheng.sunnyweather.data.db.DBManager;
import com.azheng.sunnyweather.data.model.CityModel;
import com.azheng.sunnyweather.data.model.DailyForecast;
import com.azheng.sunnyweather.data.model.Weather;
import com.azheng.sunnyweather.databinding.CityItemBinding;
import com.azheng.sunnyweather.databinding.DbCityBinding;
import com.azheng.sunnyweather.databinding.ForecastItemBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

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
        if (weather == null || weather.daily_forecast == null){
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

    @BindingAdapter("bind:showAddCity")
    public static void showAddCity(LinearLayout linearLayout , ArrayList<CityModel> cityList){
        if (cityList == null){
            return;
        }
        linearLayout.removeAllViews();
        for (CityModel item: cityList) {
            View view = LayoutInflater.from(linearLayout.getContext()).inflate(R.layout.city_item,linearLayout,false);
            CityItemBinding binding = DataBindingUtil.bind(view);
            binding.setCity(item);
            linearLayout.addView(view);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext())
                            .setTitle("确定删除该城市?")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CityModel city = binding.getCity();
                                    DBManager.getIns().deleteCity(city.getCityName(), new Handler(){
                                        @Override
                                        public void handleMessage(Message msg) {
                                            super.handleMessage(msg);
                                            linearLayout.removeView(view);
                                            ToolUnit.toast("删除成功");
                                        }
                                    });
                                }
                            });
                    dialog.show();
                    return true;
                }
            });
        }
    }

    @BindingAdapter("bind:selectCity")
    public static void selectCity(LinearLayout linearLayout , ArrayList<CityModel> cityList){
        if (cityList == null){
            return;
        }
        linearLayout.removeAllViews();
        for (CityModel item: cityList) {
            View view = LayoutInflater.from(linearLayout.getContext()).inflate(R.layout.db_city,linearLayout,false);
            DbCityBinding binding = DataBindingUtil.bind(view);
            binding.setCity(item);
            linearLayout.addView(view);
        }
    }
}
