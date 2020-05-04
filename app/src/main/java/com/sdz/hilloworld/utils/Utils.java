package com.sdz.hilloworld.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.load.engine.Resource;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {

    private static Application sApplication;


    public static void init(final Application app) {
        if (sApplication == null) {
            if (app == null) {
                sApplication = getApplicationByReflect();
            } else {
                sApplication = app;
            }
//            sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
        } else {
            if (app != null && app.getClass() != sApplication.getClass()) {
//                sApplication.unregisterActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
//                ACTIVITY_LIFECYCLE.mActivityList.clear();
                sApplication = app;
//                sApplication.registerActivityLifecycleCallbacks(ACTIVITY_LIFECYCLE);
            }
        }
    }

    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }


    public static Application getApp(){
        if(sApplication == null){
            sApplication = getApplicationByReflect();
        }
        init(sApplication);
        return sApplication;
    }

    public static void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException ignore) {
        }
    }

    public static boolean register(Context context) {
        boolean result = false;
        SharedPreferences mPreferences = context.getSharedPreferences("token_register", Context.MODE_PRIVATE);
        result = mPreferences.getBoolean("isRegistered",result);
        if(!result) {
            SharedPreferences.Editor mEditor = mPreferences.edit();
            mEditor.putBoolean("isRegistered",true);
            mEditor.apply();
        }
        return result;

    }

    public static boolean checkPhoneNumber(String str){
        Pattern pattern = Pattern.compile("1[3-9][0-9]{9}");
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()){
            return true;
        }
        return false;
    }

    public static boolean checkPassWd(String str){
        Pattern pattern = Pattern.compile("[A-Z][a-zA-Z0-9]{6}[a-zA-Z0-9]+");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            return true;
        }
        return false;
    }

    public static String getSystemTime(){
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日   HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static java.sql.Date getSystemDate(){
        return new java.sql.Date(System.currentTimeMillis());
    }


    public static int getScreenWidth(Context context) {
        Resources res = context.getResources();
        DisplayMetrics metrics = res.getDisplayMetrics();
        return  metrics.widthPixels;
    }

    public static int getScreenHeight(Context context){
        Resources res = context.getResources();
        DisplayMetrics metrics =  res.getDisplayMetrics();
        return metrics.heightPixels;

    }

}
