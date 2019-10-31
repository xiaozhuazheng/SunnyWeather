package com.azheng.sunnyweather.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.azheng.sunnyweather.SunnyApplication;

public class ToolUnit {
    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 只关注是否联网
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static String getAppCacheDir(Context context) {
        /*
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */
        String sCacheDir;
        if (context.getExternalCacheDir() != null && ExistSDCard()) {
            sCacheDir = context.getExternalCacheDir().toString();
        } else {
            sCacheDir = context.getCacheDir().toString();
        }
        return sCacheDir;
    }

    private static boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static void toast(String s){
        Toast.makeText(SunnyApplication.getAppContext(), s, Toast.LENGTH_SHORT).show();
    }
}
