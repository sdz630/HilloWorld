package com.sdz.hilloworld.ui.photos;

import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.sdz.hilloworld.R;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class ImageBannerAdapter extends BannerAdapter<Object, ImageBannerAdapter.BannerViewHolder> {

    public ImageBannerAdapter(List<Object> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());

        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, Object data, int position, int size) {
        switch(position){
            case 0:{
                holder.imageView.setImageResource(R.mipmap.image1);
                break;
            }
            case 1:{
                holder.imageView.setImageResource(R.mipmap.image2);
                break;
            }
            case 2:{
                holder.imageView.setImageResource(R.mipmap.image3);
                break;
            }
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public BannerViewHolder(ImageView imageView) {
            super(imageView);
            this.imageView = imageView;
        }
    }
}
