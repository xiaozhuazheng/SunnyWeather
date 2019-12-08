package com.azheng.sunnyweather.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.azheng.sunnyweather.R;
import com.azheng.sunnyweather.SunnyApplication;

import org.litepal.LitePal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static DBManager mIns = null;

    private DBManager(){}

    public static DBManager getIns(){
        if (mIns == null){
            synchronized (DBManager.class){
                if (mIns == null){
                    mIns = new DBManager();
                }
            }
        }
        return mIns;
    }

    /**
    * 删
    */
    public void deleteCity(String name, Handler handler){
        /* DataSupport.deleteAll(News.class, "title = ? and commentcount = ?", "今日iPhone6发布", "0");*/
        if (name == null){
            return;
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                LitePal.deleteAll(City.class, "name = ?", name);
                handler.handleMessage(null);
            }
        });
    }

    /**
     * 增
     */
    public void putCity(String name){
        if (name == null){
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (hasCity(name)){
                    return;
                }
                City city = new City();
                city.setName(name);
                city.saveThrows();
            }
        }).start();

    }

    /**
     * 查
     */
    public boolean hasCity(String name){
        List<City> list = LitePal.where("name = ? ", name).find(City.class);
        if (list != null && list.size() > 0){
            for (City city :list){
                if (city.getName().equals(name)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 改
     */
    public void changeCity(){
        /*ContentValues values = new ContentValues();
        values.put("title", "今日iPhone6 Plus发布");
        DataSupport.updateAll(News.class, values, "title = ? and id > ?", "今日iPhone6发布", "0");*/
    }

    public List<City> getAllCity(){
       return LitePal.findAll(City.class);
    }

    //---------------------------------sqlite数据库----------------------------------------------

    public static final String DB_NAME = "china_city.db"; //数据库名字
    public static final String PACKAGE_NAME = "com.azheng.sunnyweather";
    public static final String DB_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" +
            PACKAGE_NAME;  //在手机里存放数据库的位置(/data/data/com.azheng.OnlyWeather/china_city.db)
    private SQLiteDatabase database;

    public void openDatabase() {
        //PLog.e(TAG, DB_PATH + "/" + DB_NAME);
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);
    }

    public void closeDatabase() {
        if (this.database != null) {
            this.database.close();
        }
    }

    @Nullable
    private SQLiteDatabase openDatabase(String dbfile) {

        try {
            if (!(new File(dbfile).exists())) {
                //判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                InputStream is = SunnyApplication.getAppContext().getResources().openRawResource(R.raw.china_city); //欲导入的数据库
                FileOutputStream fos = new FileOutputStream(dbfile);
                int BUFFER_SIZE = 400000;
                byte[] buffer = new byte[BUFFER_SIZE];
                int count;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            return SQLiteDatabase.openOrCreateDatabase(dbfile, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<DBProvince> loadProvinces() {

        List<DBProvince> list = new ArrayList<>();

        Cursor cursor = database.query("T_Province", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                DBProvince province = new DBProvince();
                province.ProSort = cursor.getInt(cursor.getColumnIndex("ProSort"));
                province.ProName = cursor.getString(cursor.getColumnIndex("ProName"));
                list.add(province);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<DBCity> loadCities(int ProID) {
        List<DBCity> list = new ArrayList<>();
        Cursor cursor = database.query("T_City", null, "ProID = ?", new String[] { String.valueOf(ProID) }, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                DBCity city = new DBCity();
                city.CityName = cursor.getString(cursor.getColumnIndex("CityName"));
                city.ProID = ProID;
                city.CitySort = cursor.getInt(cursor.getColumnIndex("CitySort"));
                list.add(city);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
