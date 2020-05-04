package com.sdz.hilloworld.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NoScrollerViewPager extends ViewPager {

    private boolean noScroll;

    private boolean isNoScroll(){
        return noScroll;
    }


    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    public NoScrollerViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !noScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !noScroll && super.onTouchEvent(ev);
    }

}
