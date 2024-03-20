package com.xupt3g.houseinfoview;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.popupwindow.good.GoodView;
import com.xupt3g.houseinfoview.model.RecommendHouse;
import com.xupt3g.mylibrary1.CollectionManagerService;

import java.util.List;
import java.util.Random;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.RecommendHousesAdapter
 *
 * @author: shallew
 * @data: 2024/3/11 22:44
 * @about: TODO 同类推荐适配器
 */
public class RecommendHousesAdapter extends RecyclerView.Adapter<RecommendHousesAdapter.ViewHolder> {
    private final List<RecommendHouse> recommendHouseList;
    private CollectionManagerService collectionManagerService;
    private GoodView goodView;

    public RecommendHousesAdapter(List<RecommendHouse> recommendHouseList) {
        this.recommendHouseList = recommendHouseList;
        if (!BuildConfig.isModule) {
            //集成模式下 可添加到收藏
            collectionManagerService = (CollectionManagerService) ARouter.getInstance().build("/collectionsView/CollectionManagerImpl").navigation();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.houseinfo_adapter_recommend_item, parent, false));
    }
    private final int[] imageResources = {R.drawable.houseinfo_house_1, R.drawable.houseinfo_house_2, R.drawable.houseinfo_house_3};

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecommendHouse recommendHouse = recommendHouseList.get(position);
        Random random = new Random();
        int i = random.nextInt(3);
        Glide.with(holder.itemView.getContext()).load(imageResources[i]).into(holder.houseCover);
        //中间横线（删除线）
        holder.housePriceBefore.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        // 抗锯齿
        holder.housePriceBefore.getPaint().setAntiAlias(true);

        goodView = new GoodView(holder.itemView.getContext());
        //UI初始化
        if (recommendHouse.isCollected()) {
            //被收藏的推荐
            holder.collectionButton.setImageResource(R.drawable.houseinfo_icon_collect_light);
        } else {
            //未被收藏的推荐
            holder.collectionButton.setImageResource(R.drawable.houseinfo_icon_collect_dark);
        }
        //收藏按钮点击监听
        holder.collectionButton.setOnClickListener(view1 -> {
            if (!recommendHouse.isCollected()) {
                //如果当前未收藏该民宿
                //申请收藏
                if (collectionManagerService != null) {
                    boolean b = collectionManagerService.addCollection(recommendHouse.getId());
                    if (b) {
                        //添加成功
                        recommendHouse.setCollected(true);
                        holder.collectionButton.setImageResource(R.drawable.houseinfo_icon_collect_light);
                        goodView.setText("收藏成功")
                                .setTextColor(Color.parseColor("#4facfe"))
                                .setTextSize(12)
                                .setDuration(1000)
                                .setDistance(120)
                                .show(view1);
                    }
                } else {
                    XToastUtils.error("当前无法添加收藏！");
                }
            } else {
                //如果当前该民宿已收藏
                //申请移除收藏
                if (collectionManagerService != null) {
                    boolean b = collectionManagerService.removeCollection(recommendHouse.getId());
                    if (b) {
                        //移除成功
                        recommendHouse.setCollected(false);
                        holder.collectionButton.setImageResource(R.drawable.houseinfo_icon_collect_dark);
                    } else {
                        XToastUtils.error("移除失败！");
                    }
                } else {
                    XToastUtils.error("当前无法移除收藏！");
                }
            }
        });

        holder.itemView.setOnClickListener(view -> {
            if (!BuildConfig.isModule) {
                //跳转民宿详情页面
                ARouter.getInstance().build("/houseInfoView/HouseInfoActivity").navigation();
            } else {
                XToastUtils.error("当前无法跳转！");
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommendHouseList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView houseCover;
        private TextView houseTitle;
        private TextView houseIntro;
        private TextView houseFrom;
        private TextView housePriceBefore;
        private TextView housePriceAfter;
        private ImageView collectionButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            houseCover = (ImageView) itemView.findViewById(R.id.item_re_img);
            houseTitle = (TextView) itemView.findViewById(R.id.item_re_title);
            houseIntro = (TextView) itemView.findViewById(R.id.item_re_house_intro);
            houseFrom = (TextView) itemView.findViewById(R.id.item_re_house_from);
            housePriceBefore = (TextView) itemView.findViewById(R.id.item_re_house_price_before);
            housePriceAfter = (TextView) itemView.findViewById(R.id.item_re_house_price_after);
            collectionButton = (ImageView) itemView.findViewById(R.id.item_re_collect);
        }
    }
}
