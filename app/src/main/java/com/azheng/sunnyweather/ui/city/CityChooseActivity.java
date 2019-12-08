package com.azheng.sunnyweather.ui.city;

import android.app.Activity;
import android.os.Bundle;

import com.azheng.sunnyweather.R;
import com.azheng.sunnyweather.ui.BaseActivity;

public class CityChooseActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        setToolBar("添加",true);
    }
}
