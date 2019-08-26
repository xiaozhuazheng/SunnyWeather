package com.azheng.sunnyweather.data.net;

import com.azheng.sunnyweather.data.model.Heweather6;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("weather")
    Observable<Heweather6> mWeatherAPI(@Query("location") String city, @Query("key") String key);
}
