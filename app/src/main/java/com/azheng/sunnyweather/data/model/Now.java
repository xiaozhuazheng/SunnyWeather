package com.azheng.sunnyweather.data.model;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.azheng.sunnyweather.SunnyApplication;

public class Now {
    /*"cloud":"7",
                "cond_code":"100",
                "cond_txt":"晴",
                "fl":"29",
                "hum":"34",
                "pcpn":"0.0",
                "pres":"1006",
                "tmp":"30",
                "vis":"16",
                "wind_deg":"190",
                "wind_dir":"南风",
                "wind_sc":"3",
                "wind_spd":"13"
                */

    public String cond_txt;
    public String tmp;
    public String fl;
    public String hum;
    public String cond_code;

    public String getTmp(){
        return tmp + "℃";
    }


    public Drawable getPic(){
        String pic_name = "w_" + cond_code;
        Resources resources = SunnyApplication.getAppContext().getResources();
        int resId = resources.getIdentifier(pic_name, "drawable",SunnyApplication.getAppContext().getPackageName());
        Drawable image = resources.getDrawable(resId);
        return image;
    }
}
