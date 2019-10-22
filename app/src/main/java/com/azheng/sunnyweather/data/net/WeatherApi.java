package com.azheng.sunnyweather.data.net;

import com.azheng.sunnyweather.data.model.HeWeather6;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("weather")
    Observable<HeWeather6> mWeatherAPI(@Query("location") String city, @Query("key") String key);
}
