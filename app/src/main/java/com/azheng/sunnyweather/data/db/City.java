package com.azheng.sunnyweather.data.db;

import org.litepal.crud.LitePalSupport;

public class City extends LitePalSupport {

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
