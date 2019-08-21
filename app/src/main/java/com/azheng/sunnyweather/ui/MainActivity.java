package com.azheng.sunnyweather.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.azheng.sunnyweather.R;

import static com.azheng.sunnyweather.R.id.toolbar;

public class MainActivity extends BaseActivity {
    private Toolbar mToolBar;
    private DrawerLayout mDrawer;
    private NavigationView mNav;
    private ActionBarDrawerToggle mToogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolBar = findViewById(toolbar);
        mToolBar.setTitle("BeiJing");
        mToolBar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(mToolBar);

        mDrawer = findViewById(R.id.drawer);
        mToogle = new ActionBarDrawerToggle(this, mDrawer, mToolBar, R.string.drawer_open, R.string.drawer_close);

        mNav = findViewById(R.id.nav_view);
        mDrawer.addDrawerListener(mToogle);
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Toast.makeText(MainActivity.this,menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}
