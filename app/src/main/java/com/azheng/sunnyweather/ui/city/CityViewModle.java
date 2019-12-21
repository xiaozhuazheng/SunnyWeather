package com.azheng.sunnyweather.ui.city;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.azheng.sunnyweather.R;
import com.azheng.sunnyweather.data.CityRepository;
import com.azheng.sunnyweather.data.db.City;
import com.azheng.sunnyweather.data.db.DBData;
import com.azheng.sunnyweather.data.db.DBManager;
import com.azheng.sunnyweather.data.model.CityModel;
import com.azheng.sunnyweather.databinding.CityItemBinding;
import com.azheng.sunnyweather.util.NetCallback;
import com.azheng.sunnyweather.util.ToolUnit;
import com.azheng.sunnyweather.util.weight.FloatView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.azheng.sunnyweather.util.Config.MSG_1;
import static com.azheng.sunnyweather.util.Config.MSG_2;

public class CityViewModle extends ViewModel{
    private CityRepository repository;
    private Context context;
    private FloatView floatView;

    public MutableLiveData<Boolean> hasCity;//是否有已添加城市
    public MutableLiveData<ArrayList<CityModel>> mCityList;//已添加城市列表

    public MutableLiveData<List<DBData>> mDBList;//城市选择列表
    private BroadcastReceiver mReceiver;

    public CityViewModle(CityRepository repository, Context context) {
        this.repository = repository;
        this.context = context;

        hasCity = new MutableLiveData<>();
        mCityList = new MutableLiveData<>();
        mDBList = new MutableLiveData<>();

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("PROVICENS_CLIK")){
                    int pid = intent.getIntExtra("provices_id",0);
                    getCitys(pid);
                } else if (intent.getAction().equals("ADD_CITY")){
                    String cityname = intent.getStringExtra("add_city");
                    DBManager.getIns().putCity(cityname);
                    getAddCity();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("PROVICENS_CLIK");
        filter.addAction("ADD_CITY");
        context.registerReceiver(mReceiver,filter);
    }

    public void getAddCity(){
        repository.getAddCity(new NetCallback<ArrayList<CityModel>>() {
            @Override
            public void onSucess(ArrayList<CityModel> data) {
                if (data != null && data.size() > 0){
                    hasCity.setValue(true);
                    mCityList.setValue(data);

                    //回到主界面
                    Activity activity = (Activity) context;
                    if (activity instanceof CityChooseActivity){
                        activity.onBackPressed();
                    }
                }
            }

            @Override
            public void onFailure(String msg) {
                ToolUnit.toast(msg);
            }
        });
    }

    public void getProvinces(){
        repository.getProvince(new NetCallback<List<DBData>>() {
            @Override
            public void onSucess(List<DBData> data) {
                mDBList.setValue(data);
            }

            @Override
            public void onFailure(String msg) {
                ToolUnit.toast(msg);
            }
        });
    }

    public void getCitys(int pid){
        repository.getCity(pid,new NetCallback<List<DBData>>() {
            @Override
            public void onSucess(List<DBData> data) {
                mDBList.setValue(data);
            }

            @Override
            public void onFailure(String msg) {
                ToolUnit.toast(msg);
            }
        });
    }

    /* float动画*/
    public void setFloatView(FloatView view){
        floatView = view;
    }

    public void onFloatClick(){
        //去添加城市界面
        Intent intent = new Intent();
        intent.setClass(context,CityChooseActivity.class);

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            context.startActivity(intent);
            return;
        }

        //动画
        int[] location = new int[2];
        floatView.getLocationInWindow(location);
        final int cx = location[0] + floatView.getWidth() / 2;
        final int cy = location[1] + floatView.getHeight() / 2;
        final ImageView view = new ImageView(context);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setImageResource(R.color.blue);
        Activity activity = (Activity) context;
        final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int w = decorView.getWidth();
        int h = decorView.getHeight();
        decorView.addView(view, w, h);
        final int finalRadius = (int) Math.sqrt(w * w + h * h) + 1;

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        anim.setDuration(300);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                context.startActivity(intent);
                // 默认渐隐过渡动画.
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                // 默认显示返回至当前Activity的动画.
                floatView.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {

                        Animator anim =
                                ViewAnimationUtils.createCircularReveal(view, cx, cy, finalRadius, 0);
                        anim.setDuration(300);
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                try {
                                    decorView.removeView(view);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        anim.start();

                    }
                }, 300);


            }
        });
        anim.start();
    }
}
