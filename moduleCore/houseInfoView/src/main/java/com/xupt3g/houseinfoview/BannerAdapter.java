package com.xupt3g.houseinfoview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.BannerAdapter
 *
 * @author: shallew
 * @data: 2024/3/5 20:49
 * @about: TODO
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    private Context mContext;
    private List<Integer> imgUrl;

    public BannerAdapter(Context mContext, List<Integer> imgUrl) {
        this.mContext = mContext;
        this.imgUrl = imgUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.houseinfo_banner_img_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(imgUrl.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imgUrl.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.view_item);
        }
    }
}