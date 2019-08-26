package com.azheng.sunnyweather.data.model;

import java.util.List;

public class Weather {
    /*
    * "basic":Object{...},
            "update":Object{...},
            "status":"ok",
            "now":Object{...},
            "daily_forecast":Array[3],
            "lifestyle":Array[8]*/

    public String status;

    public Basic basic;
    public Update update;
    public Now now;
    public List<DailyForecast> daily_forecast;
    public List<Lifestyle> lifestyle;
}