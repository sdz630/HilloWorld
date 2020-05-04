package com.sdz.hilloworld;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sdz.hilloworld.base.NoScrollerViewPager;
import com.sdz.hilloworld.global.Constants;
import com.sdz.hilloworld.ui.BottomNavigationViewHelper;
import com.sdz.hilloworld.ui.aboutMe.AboutMeFragment;
import com.sdz.hilloworld.ui.chat.ChatFragment;
import com.sdz.hilloworld.ui.home.HomeFragment;
import com.sdz.hilloworld.ui.photos.PhotosFragment;
import com.sdz.hilloworld.ui.task.TaskFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    BottomNavigationView navView;

    RadioGroup radioGroup;
    NoScrollerViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();


//
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_photos, R.id.navigation_chat, R.id.navigation_study, R.id.navigation_about_me)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    private void initView() {
        navView = findViewById(R.id.nav_view);
        viewPager = findViewById(R.id.view_pager_main);
        BottomNavigationViewHelper.disableShiftMode(navView);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_navigation_home:{
                        viewPager.setCurrentItem(0);
                        break;
                    }
                    case R.id.item_navigation_photos:{
                        viewPager.setCurrentItem(1);
                        break;
                    }
                    case R.id.item_navigation_chat:{
                        viewPager.setCurrentItem(2);
                        break;
                    }
                    case R.id.item_navigation_study:{
                        viewPager.setCurrentItem(3);
                        break;
                    }
                    case R.id.item_navigation_about_me:{
                        viewPager.setCurrentItem(4);
                        break;
                    }
                }
                return false;
            }
        });

        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setNoScroll(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class BottomAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();

        public BottomAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }
    }


    private void setupViewPager(ViewPager viewPager){
        BottomAdapter adapter = new BottomAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new PhotosFragment());
        adapter.addFragment(new ChatFragment());
        adapter.addFragment(new TaskFragment());
        adapter.addFragment(new AboutMeFragment());
        viewPager.setAdapter(adapter);
    }

}
