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
        holder.houseCover.setImageResource(imageResources[i]);
        holder.ratingBar.setRating((float) random.nextInt(5));
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
                    Log.d("HouseId", "onBindViewHolder: " + collectionsData.getId());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            houseCover = (ImageView) itemView.findViewById(R.id.item_house_cover_img);
            houseTitle = (TextView) itemView.findViewById(R.id.item_house_title);
            houseIntro = (TextView) itemView.findViewById(R.id.item_brief_intro);
            ratingBar = (ScaleRatingBar) itemView.findViewById(R.id.item_rating_stars);
            priceBefore = (TextView) itemView.findViewById(R.id.item_price_before);
            priceAfter = (TextView) itemView.findViewById(R.id.item_price_after);
            darkOverlayView = (CardView) itemView.findViewById(R.id.dark_overlay_layout);
            darkOverlayView.setVisibility(View.GONE);
        }
    }

}
