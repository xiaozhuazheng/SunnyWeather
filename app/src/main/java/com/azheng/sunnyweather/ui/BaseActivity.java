package com.azheng.sunnyweather.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.azheng.sunnyweather.R;
import com.azheng.sunnyweather.util.ToolUnit;

public class BaseActivity extends AppCompatActivity {
    public Toolbar mToolBar;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸氏状态栏，导航栏透明
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option =/* View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | */View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //隐藏actionbar
        /*ActionBar actionBar = getSupportActionBar();
        actionBar.hide();*/
    }

    public void setToolBar(String title,boolean back){
        mToolBar = findViewById(R.id.toolbar);
        mToolbarTitle = findViewById(R.id.toolbar_title);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mToolBar.getLayoutParams();
        layoutParams.topMargin = ToolUnit.getStatusBarHeight(this);
        mToolBar.setLayoutParams(layoutParams);

        if (title != null){
            mToolbarTitle.setText(title);
        }
        if (back){
            mToolBar.setNavigationIcon(R.drawable.back);
            mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
}
