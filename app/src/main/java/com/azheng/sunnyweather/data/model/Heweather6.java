package com.azheng.sunnyweather.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Heweather6 {
    @SerializedName("HeWeather6") @Expose
    public List<Weather> HeWeather6 = new ArrayList<>();
}
