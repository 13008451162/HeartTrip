package com.xupt3g.collectionsview.view;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.progress.ratingbar.ScaleRatingBar;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.collectionsview.R;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionData;

import java.util.List;
import java.util.Random;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.view.CollectionsListAdapter
 *
 * @author: shallew
 * @data:2024/2/15 18:11
 * @about: TODO 收藏列表适配器
 */
public class CollectionsListAdapter extends RecyclerView.Adapter<CollectionsListAdapter.ViewHolder> {
    //测试数据
    private final int[] imageResources = {R.drawable.collection_house_1, R.drawable.collection_house_2, R.drawable.collection_house_3};

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_collections_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CollectionData collectionsData = list.get(position);

        Random random = new Random();
        int i = random.nextInt(3);
        //测试数据 随机封面
        holder.houseCover.setImageResource(imageResources[i]);
        //测试数据 随机评分
        holder.ratingBar.setRating(5);

        //设置真实数据
        if (collectionsData.getTitle() != null && !"".equals(collectionsData.getTitle())) {
            //标题
            holder.houseTitle.setText(collectionsData.getTitle());
        }
        if (collectionsData.getCover() != null && !"".equals(collectionsData.getCover())) {
            //封面
            Glide.with(holder.itemView.getContext()).load(collectionsData.getCover())
                    .into(holder.houseCover);
        }
        if (collectionsData.getIntro() != null && !"".equals(collectionsData.getIntro())) {
            //简介
            holder.houseIntro.setText(collectionsData.getIntro());
        }
        if (collectionsData.getLocation() != null && !"".equals(collectionsData.getLocation())) {
            //定位位置
            holder.houseLocation.setText(collectionsData.getLocation());
        }
        //float 评星
        holder.ratingBar.setRating(collectionsData.getRatingstarts());
        if (collectionsData.getPriceBefore() != 0) {
            //折前价
            holder.priceBefore.setText(collectionsData.getPriceBefore() + "");
        }
        if (collectionsData.getPriceAfter() != 0) {
            //折后价
            holder.priceAfter.setText(collectionsData.getPriceAfter() + "");
        }
        //tem
        holder.ratingBar.setRating(5);
        //中间横线（删除线）
        holder.priceBefore.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        // 抗锯齿
        holder.priceBefore.getPaint().setAntiAlias(true);

        if (collectionsData.getRowState() == 0) {
            //已下架
            holder.darkOverlayView.setVisibility(View.VISIBLE);
        } else {
            holder.darkOverlayView.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(view -> {
            if (collectionsData.getRowState() == 0) {
                ToastUtils.toast("该民宿已下架");
            } else {
                if (!BuildConfig.isModule) {
                    //判断当前是不是集成开发模式
                    Log.d("TAGGGGYTU", "onBindViewHolder: " + collectionsData.getId());
                    ARouter.getInstance().build("/houseInfoView/HouseInfoActivity")
                            .withInt("HouseId", collectionsData.getId()).navigation();
                } else {
                    XToastUtils.error("当前不能跳转！");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private List<CollectionData> list;

    public CollectionsListAdapter(List<CollectionData> list) {
        this.list = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView houseCover;
        private TextView houseTitle;
        private TextView houseIntro;
        private ScaleRatingBar ratingBar;
        private TextView priceBefore;
        private TextView priceAfter;
        private CardView darkOverlayView;
        private TextView houseLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            houseCover = (ImageView) itemView.findViewById(R.id.item_house_cover_img);
            houseTitle = (TextView) itemView.findViewById(R.id.item_house_title);
            houseIntro = (TextView) itemView.findViewById(R.id.item_brief_intro);
            ratingBar = (ScaleRatingBar) itemView.findViewById(R.id.item_rating_stars);
            priceBefore = (TextView) itemView.findViewById(R.id.item_price_before);
            priceAfter = (TextView) itemView.findViewById(R.id.item_price_after);
            houseLocation = (TextView) itemView.findViewById(R.id.house_from);
            darkOverlayView = (CardView) itemView.findViewById(R.id.dark_overlay_layout);
            darkOverlayView.setVisibility(View.GONE);
        }
    }

}
