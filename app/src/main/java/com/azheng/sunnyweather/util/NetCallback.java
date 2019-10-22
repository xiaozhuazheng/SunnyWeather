package com.azheng.sunnyweather.util;

public interface NetCallback<T> {
    void onSucess(T data);

    void onFailure();
}
