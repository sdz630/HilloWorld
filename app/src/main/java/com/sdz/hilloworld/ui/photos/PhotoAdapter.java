package com.sdz.hilloworld.ui.photos;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sdz.hilloworld.R;
import com.sdz.hilloworld.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<PhotoBean> mDatas;
    private Random random;

    private int baseWidth;

    public PhotoAdapter(List<String> data, Context context) {
        mDatas = new ArrayList<>();
        for (String str : data) {
            mDatas.add(new PhotoBean(str, 0));
        }
        this.mContext = context;
        this.mLayoutInflater = mLayoutInflater.from(context);
        random = new Random();
        baseWidth = Utils.getScreenWidth(context) / 3;
    }

    public void loadMore(){
        int size = mDatas.size();
        for (int i = size; i < size + 20; i++) {
            mDatas.add(new PhotoBean("https://bak.yantuz.cn/mmPic/?v=" + (random.nextInt(1000) + 300),0));
        }
        notifyItemChanged(size+1);
    }

    public void refresh(){

        int size = mDatas.size();
        for(int i = 0; i<size;i++){
            mDatas.get(i).height=0;
            mDatas.get(i).url="https://bak.yantuz.cn/mmPic/?v=" + (random.nextInt(1000) + 300);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.item_photo, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri uri = Uri.parse(mDatas.get(position).url);
        if (!uri.equals(holder.tag)) {
            ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
            int height = mDatas.get(position).height;
            if (height <= 0) {
                height = random.nextInt(200) + 500;
                mDatas.get(position).height = height;
            }
            params.height = height;
            holder.imageView.setLayoutParams(params);
            holder.tag = mDatas.get(position).url;
            Glide.with(mContext)
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, position + "", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, DetailActivity.class);

                    intent.putExtra("uri", mDatas.get(position).url);

                    mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) mContext, holder.imageView, "share").toBundle());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public String tag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.photo);
        }
    }
}