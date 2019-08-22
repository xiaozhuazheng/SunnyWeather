package com.azheng.sunnyweather.ui.weather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<String> myTitle = new ArrayList<>();
    private List<Fragment> myFragment = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        myTitle.add("天气");
        myTitle.add("城市");
    }

    public void setData(List<Fragment> data){
        this.myFragment = data;
    }

    @Override
    public Fragment getItem(int i) {
        if (myFragment != null && myFragment.size()>0){
            return myFragment.get(i);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (myFragment != null){
            return myFragment.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return myTitle.get(position);
    }
}
