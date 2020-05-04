package com.sdz.hilloworld.ui.photos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdz.hilloworld.R;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PhotosFragment extends Fragment implements OnBannerListener, OnPageChangeListener {

    private static final String TAG = "PhotosFragment";

    private ArrayList<Object> mData = new ArrayList<>();

    private PhotosViewModel dashboardViewModel;

    /*
     *  photo数据
     *
     */
    private RecyclerView mRecyclerView;
    private PhotoAdapter mAdapter;
    private Handler handler;
    private List<String> mImages;
    private List<Integer> mImageHeights;
    private int count = 1;
    private Random random = new Random();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(PhotosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_photos, container, false);

        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        Banner banner = getActivity().findViewById(R.id.banner);
        banner.setAdapter(new ImageBannerAdapter(mData))
                .setOrientation(Banner.HORIZONTAL)
                .setIndicator(new CircleIndicator(getActivity()))
                .setUserInputEnabled(false)
                .isAutoLoop(true)
                .setDelayTime(3000);
        banner.start();

        RefreshLayout refreshLayout = getActivity().findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                mImages.clear();
//                for(int i = 0;i<20;i++){
//                    mImages.add("https://bak.yantuz.cn/mmPic/?v=" + random.nextInt(1000) + 300);
//                }
//                mAdapter = new PhotoAdapter(mImages,getActivity());
//                mRecyclerView.setAdapter(mAdapter);
                mAdapter.refresh();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mAdapter.loadMore();
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new PhotoAdapter(mImages, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                mAdapter.notifyDataSetChanged();
            }
        };


//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                //判断是否滑动到底部，用于分页加载
//                if (isSlideToBottom(recyclerView)) {
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //                                OkHttpClient client = new OkHttpClient();
////                                Request request = new Request.Builder().url("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/40/").build();
////                                Response response = client.newCall(request).execute();
////                                String resultStr = response.body().string()+count++;
//                            Message msg = new Message();
////
////                                msg.obj = resultStr;
//
//                            handler.sendMessage(msg);
//
//                        }
//                    }).start();
//
//
//                }
//            }
//        });

    }

    private void init() {
        mData.add(new Object());
        mData.add(new Object());
        mData.add(new Object());
        mImages = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
//            mImages.add("https://img.xjh.me/random_img.php?type=bg&ctype=nature&return=302&device=mobile");
            mImages.add("https://bak.yantuz.cn/mmPic/?v=" + (random.nextInt(1000) + 300));
        }
        mRecyclerView = getActivity().findViewById(R.id.photo_show);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }


    @Override
    public void OnBannerClick(Object data, int position) {

    }

    @Override
    public void onBannerChanged(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void getImagesFromJson(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            JSONArray jsonArray = jsonObject.getJSONArray("results");


            for (int i = 0; i < jsonArray.length(); i++) {


                JSONObject jb = jsonArray.getJSONObject(i);

                mImages.add(jb.getString("url"));


            }


        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
        }

    }

    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;

        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;

        return false;
    }


}

