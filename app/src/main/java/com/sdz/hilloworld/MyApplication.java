package com.sdz.hilloworld;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

import com.bumptech.glide.util.Util;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sdz.hilloworld.common.AsyncTask;
import com.sdz.hilloworld.data.sql.todolist.Plan;
import com.sdz.hilloworld.data.sql.todolist.PlanRepository;
import com.sdz.hilloworld.utils.Utils;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        QMUISwipeBackActivityManager.init(this);
//        Utils.init(this)
        initData();
    }


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public void initData(){
        SharedPreferences sharedPreferences = getSharedPreferences("init", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Boolean isFirstOpen = sharedPreferences.getBoolean("isFirstOpen",true);if(isFirstOpen){
            AsyncTask.getInstance().postNormalTask(new Runnable() {
                @Override
                public void run() {
                    PlanRepository planRepository =  PlanRepository.getInstance(getApplicationContext());
                    planRepository.insertPlan(new Plan("Do homework", Utils.getSystemDate()));
                    planRepository.insertPlan(new Plan("Do development", Utils.getSystemDate()));
                    planRepository.insertPlan(new Plan("Brush algorithm", Utils.getSystemDate()));
                }
            });

            editor.putBoolean("isFirstOpen", false);
            editor.apply();
        }
    }
}
