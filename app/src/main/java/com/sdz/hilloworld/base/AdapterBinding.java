package com.sdz.hilloworld.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingBuildInfo;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.sdz.hilloworld.R;
import com.sdz.hilloworld.utils.Utils;

public class AdapterBinding {
    @BindingAdapter(value = {"ImageUrl", "PlaceHoder"}, requireAll = false)
    public static void loadUrl(ImageView view, String url, Drawable placeHolder) {
        Glide.with(view.getContext()).load(url).placeholder(placeHolder).into(view);
    }

    @BindingAdapter(value = {"visible"}, requireAll = false)
    public static void visible(View view, boolean visiable) {
        view.setVisibility(visiable ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter(value = {"showDrable", "drableShowed"}, requireAll = false)
    public static void showDrable(ImageView view, boolean showDrawable, int drawableShowed) {
        view.setImageResource(showDrawable ? drawableShowed : R.color.transparent);
    }

    @BindingAdapter(value = {"textColor"}, requireAll = false)
    public static void setTextColor(TextView textview, int textColorRes) {
        textview.setTextColor(textColorRes);
    }

    @BindingAdapter(value = {"imageRes"}, requireAll = false)
    public static void setImageRes(ImageView view, int imageRes) {
        view.setImageResource(imageRes);
    }

    @BindingAdapter(value = {"selected"}, requireAll = false)
    public static void selected(View view, boolean selected) {
        view.setSelected(selected);
    }

    @BindingAdapter(value = {"isOpenDrawer"}, requireAll = false)
    public static void openDrawer(DrawerLayout drawerLayout, boolean isOpenDrawer) {
        if (isOpenDrawer && !drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @BindingAdapter(value = {"allowOpenDrawer"}, requireAll = false)
    public static void allowOpenDrawerOpen(DrawerLayout drawerLayout, boolean allowDrawerOpen) {
        drawerLayout.setDrawerLockMode(allowDrawerOpen?DrawerLayout.LOCK_MODE_UNLOCKED:DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @BindingAdapter(value={"initTabAndPage"}, requireAll = false)
    public static void initTabAndPage(TabLayout tabLayout, boolean initTabAndPage){
        if (initTabAndPage) {
            int count = tabLayout.getTabCount();
            String[] title = new String[count];
            for(int i = 0;i<count;i++){
                title[i] = tabLayout.getTabAt(i).getText().toString();
            }
            ViewPager viewPager = tabLayout.getRootView().findViewById(R.id.view_pager_main);
            if(viewPager!=null){
                viewPager.setAdapter(new CommonViewPagerAdapter(count, false, title));
                tabLayout.setupWithViewPager(viewPager);
            }
        }

    }

    @BindingAdapter(value = {"pageAssetPath"}, requireAll = false)
    public static void loadAssetsPage(WebView webView, String assetPath) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    uri = request.getUrl();
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Utils.getApp().startActivity(intent);
                return true;
            }
        });
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        String url = "file:///android_asset/" + assetPath;
        webView.loadUrl(url);
    }

    @BindingAdapter(value = {"loadPage"}, requireAll = false)
    public static void loadPage(WebView webView, String loadPage) {
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.loadUrl(loadPage);
    }



}
