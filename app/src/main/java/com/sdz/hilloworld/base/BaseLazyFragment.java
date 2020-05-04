package com.sdz.hilloworld.base;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public abstract class BaseLazyFragment extends Fragment {

    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        }else{
            isVisible = false;
            onInvisible();
        }
    }

    protected  void onInvisible(){

    }

    protected  void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();


}
