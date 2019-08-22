package com.azheng.sunnyweather.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.azheng.sunnyweather.R;
import com.azheng.sunnyweather.util.ToolUnit;

import static com.azheng.sunnyweather.R.id.toolbar;

public class MainActivity extends BaseActivity {
    private DrawerLayout mDrawer;
    private NavigationView mNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = findViewById(toolbar);

        //避免toolbar被状态栏覆盖
        int l = mToolBar.getPaddingLeft();
        int h = mToolBar.getPaddingTop();
        int r = mToolBar.getPaddingRight();
        int b = mToolBar.getPaddingBottom();
        int statusBarH = ToolUnit.getStatusBarHeight(this);
        mToolBar.setPadding(l,h + statusBarH,r,b);

        mToolBar.setTitle("北京");
        mToolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolBar);

        mToolBar.setNavigationIcon(R.drawable.icon_nav);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.icon_nav);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawer = findViewById(R.id.drawer);
        //将侧边栏顶部延伸至status bar
        mDrawer.setFitsSystemWindows(true);
        //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
        mDrawer.setClipToPadding(false);

        mNav = findViewById(R.id.nav_view);
        mNav.setItemIconTintList(null);
        setupDrawerContent(mNav);
    }

    private void setupDrawerContent(NavigationView mNav) {
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Toast.makeText(MainActivity.this,menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                menuItem.setChecked(true);
                mDrawer.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            mDrawer.openDrawer(GravityCompat.START);
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }
}
