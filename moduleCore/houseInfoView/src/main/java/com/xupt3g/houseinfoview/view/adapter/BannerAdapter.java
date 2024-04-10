package com.xupt3g.houseinfoview.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xuexiang.xui.widget.imageview.preview.loader.GlideMediaLoader;
import com.xupt3g.houseinfoview.ImageViewInfo;
import com.xupt3g.houseinfoview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.view.adapter.BannerAdapter
 *
 * @author: shallew
 * @data: 2024/3/5 20:49
 * @about: TODO
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    private List<ImageViewInfo> imgUrlList;
    private String[] imgUrls;

    public BannerAdapter(List<ImageViewInfo> imgUrl) {
        this.imgUrlList = imgUrl;
    }

    public BannerAdapter(String[] imgUrls) {
        this.imgUrls = imgUrls;
        this.imgUrlList = new ArrayList<>();
        for (String imgUrl : imgUrls) {
            imgUrlList.add(new ImageViewInfo(imgUrl));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.houseinfo_banner_img_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext()).load(imgUrlList.get(position).getUrl())
                .apply(GlideMediaLoader.getRequestOptions()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imgUrlList.size();

    }

    public List<ImageViewInfo> getImgUrlList() {
        return imgUrlList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.view_item);
        }
    }
}