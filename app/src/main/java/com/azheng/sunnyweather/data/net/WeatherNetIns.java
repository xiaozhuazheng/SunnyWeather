package com.azheng.sunnyweather.data.net;

import android.content.Context;

import com.azheng.sunnyweather.SunnyApplication;
import com.azheng.sunnyweather.util.Config;
import com.azheng.sunnyweather.util.ToolUnit;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherNetIns {
    public static WeatherApi mWeatherApi;
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private Context mContext;
    private static WeatherNetIns mInstances;

    public static WeatherApi getWeatherApi(){
        if (mInstances == null){
            synchronized (WeatherNetIns.class){
                if (mInstances == null){
                    mInstances = new WeatherNetIns();
                }
            }
        }
        return mWeatherApi;
    }

    private WeatherNetIns() {
        init();
    }

    public void init(){
        mContext = SunnyApplication.getAppContext();
        initOkHttp();
        initRetrofit();
        mWeatherApi = mRetrofit.create(WeatherApi.class);
    }

    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 缓存 http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(ToolUnit.getAppCacheDir(mContext), "/NetCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = chain -> {
            Request request = chain.request();
            if (!ToolUnit.isNetworkConnected(mContext)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            Response.Builder newBuilder = response.newBuilder();
            if (ToolUnit.isNetworkConnected(mContext)) {
                int maxAge = 0;
                // 有网络时 设置缓存超时时间0个小时
                newBuilder.header("Cache-Control", "public, max-age=" + maxAge);
            } else {
                // 无网络时，设置超时为4周
                int maxStale = 60 * 60 * 24 * 28;
                newBuilder.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
            return newBuilder.build();
        };
        builder.cache(cache).addInterceptor(cacheInterceptor);
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        mOkHttpClient = builder.build();
    }

    private void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Config.URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }
}
