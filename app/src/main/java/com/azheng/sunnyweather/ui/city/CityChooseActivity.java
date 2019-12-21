package com.azheng.sunnyweather.ui.city;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.azheng.sunnyweather.R;
import com.azheng.sunnyweather.data.db.DBManager;
import com.azheng.sunnyweather.databinding.ActivityChooseBinding;
import com.azheng.sunnyweather.ui.BaseActivity;
import com.azheng.sunnyweather.util.InjectorUtil;

public class CityChooseActivity extends BaseActivity {
    private CityViewModle mViewModle;
    private ActivityChooseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_choose);
        mViewModle = ViewModelProviders.of(this, InjectorUtil.getCityModelFactory(this)).get(CityViewModle.class);
        binding.setViewModel(mViewModle);
        binding.setLifecycleOwner(this);

        setToolBar("添加",true);

        DBManager.getIns().openDatabase();
        mViewModle.getProvinces();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBManager.getIns().closeDatabase();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
