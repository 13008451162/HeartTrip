package com.xupt3g.homepageview.view.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.utils.XToastUtils;
import com.xupt3g.homepageview.R;
import com.xupt3g.homepageview.model.RecommendHomeData;
import com.xupt3g.mylibrary1.implservice.CollectionManagerService;

import java.util.ArrayList;

/**
 * 项目名: HeartTrip
 * 文件名: recommendAdpter
 *
 * @author: lukecc0
 * @data:2024/3/21 下午12:35
 * @about: TODO
 */

public class RecommendAdpter extends RecyclerView.Adapter<RecommendAdpter.ViewHolder> {

    private ArrayList<RecommendHomeData.DataDTO.ListDTO> mHomeList;

    private CollectionManagerService collectionManagerService = null;

    private Context context;


    public RecommendAdpter(ArrayList<RecommendHomeData.DataDTO.ListDTO> mArrayList) {
        this.mHomeList = mArrayList;
    }

    public RecommendAdpter() {
        mHomeList = new ArrayList<>();
    }

    public void setmHomeList(ArrayList<RecommendHomeData.DataDTO.ListDTO> mHomeList) {
        this.mHomeList.addAll(mHomeList);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recommend_item, parent, false);

        if (!BuildConfig.isModule) {
            collectionManagerService = (CollectionManagerService) ARouter.getInstance().build("/collectionsView/CollectionManagerImpl").navigation();
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RecommendHomeData.DataDTO.ListDTO listDTO = mHomeList.get(position);

        holder.titleView.setText(listDTO.getTitle());
        holder.priceAfterView.setText(listDTO.getPriceAfter().toString());
        Glide.with(context).load(listDTO.getCover())
                .transform(new RoundedCorners(40))
                .into(holder.coverView);

        holder.locationView.setText(listDTO.getLocation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BuildConfig.isModule) {
                    //判断当前是不是集成开发模式
                    ARouter.getInstance().build("/houseInfoView/HouseInfoActivity")
                            .withInt("HouseId", listDTO.getId()).navigation();
                } else {
                    XToastUtils.error("当前不能跳转！");
                }
            }
        });

        holder.imageView.setOnClickListener(v -> {
            // 获取当前 ImageView 的 Tag
            Object tag = holder.imageView.getTag();

            // 检查 Tag 的值
            if (tag == null || (int) tag == 0) {
                // 如果当前为未点击状态，则设置为点击后的样式
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.collect_ret);
                holder.imageView.setImageDrawable(drawable);
                holder.imageView.setTag(1); // 更新 Tag 为 1，表示已点击状态

                if (collectionManagerService != null) {
//                    collectionManagerService.addCollection(listDTO.getId());
                }
            } else {
                // 如果当前为已点击状态，则设置为未点击时的样式（这里假设未点击时的样式为null）
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.collect_white);
                holder.imageView.setImageDrawable(drawable);
                holder.imageView.setTag(0); // 更新 Tag 为 0，表示未点击状态

                if (collectionManagerService != null) {
//                    collectionManagerService.removeCollection(listDTO.getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mHomeList != null ? mHomeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        TextView titleView;
        TextView priceAfterView;
        TextView locationView;
        ImageView coverView;

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageView = itemView.findViewById(R.id.click_to_favorite);
            titleView = itemView.findViewById(R.id.title_view);
            priceAfterView = itemView.findViewById(R.id.priceAfterView);
            locationView = itemView.findViewById(R.id.locationView);
            coverView = itemView.findViewById(R.id.cover);
        }
    }
}
