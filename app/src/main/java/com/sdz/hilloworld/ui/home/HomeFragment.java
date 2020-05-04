package com.sdz.hilloworld.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sdz.hilloworld.R;
import com.sdz.hilloworld.base.BaseLazyFragment;
import com.sdz.hilloworld.common.AsyncTask;
import com.sdz.hilloworld.ui.home.news.NewsFragment;
import com.sdz.hilloworld.ui.task.TaskFragment;
import com.sdz.hilloworld.ui.home.tools.ToolsFragment;

import java.util.ArrayList;

public class HomeFragment extends BaseLazyFragment {

    private static final String TAG = "HomeFragment";

    private HomeViewModel homeViewModel;

    private View mRootView;

    private ViewPager viewPager;
    private PageAdapter pageAdapter;
    private TabLayout tabLayout;

    private boolean isPrepared;
    private boolean hasLoaded;


    @Override
    protected void lazyLoad() {
        if (isPrepared || isVisible || hasLoaded) {
            return;
        }
        AsyncTask.getInstance().postNormalTask(()->{

        });

    }

    class PageAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments = new ArrayList<>();

        public PageAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new ToolsFragment());
            fragments.add(new NewsFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_home, null  );
            isPrepared = true;
//            lazyLoad();
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setTab();
        
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ");
    }

    public void init(View v){
        tabLayout=  v.findViewById(R.id.tab_layout);
        viewPager=  v.findViewById(R.id.view_pager_home);
        if (pageAdapter == null) {
            pageAdapter = new PageAdapter(getActivity().getSupportFragmentManager());
        }
        viewPager.setAdapter(pageAdapter);
        viewPager.setCurrentItem(0);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(1);
    }

    public void setTab(){
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Tools").setIcon(R.drawable.ic_tools_black_24dp);
        tabLayout.getTabAt(1).setText("News").setIcon(R.drawable.ic_news_black_24dp);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: ");
        if(mRootView!=null){
            ((ViewGroup)mRootView.getParent()).removeView(mRootView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }
}