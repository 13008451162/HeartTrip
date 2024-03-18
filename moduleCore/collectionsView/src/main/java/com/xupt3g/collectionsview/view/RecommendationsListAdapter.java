package com.xupt3g.collectionsview.view;

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
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.popupwindow.good.GoodView;
import com.xuexiang.xui.widget.popupwindow.good.IGoodView;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.collectionsview.R;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionData;
import com.xupt3g.collectionsview.guessModel.retrofit.GuessData;
import com.xupt3g.collectionsview.presenter.ThePresenter;

import java.util.List;
import java.util.Random;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.view.RecommendationsListAdapter
 *
 * @author: shallew
 * @data: 2024/2/17 1:24
 * @about: TODO
 */
public class RecommendationsListAdapter extends RecyclerView.Adapter<RecommendationsListAdapter.ViewHolder> implements CollectionsGuessManagerImpl {

    private ThePresenter presenter;
    private List<GuessData> list;
    private IGoodView goodView;


    public RecommendationsListAdapter(List<GuessData> list) {
        this.list = list;
        presenter = new ThePresenter(this);
    }

    @Override
    public void showCollectionsList(List<CollectionData> collectionsDataList) {

    }

    @Override
    public void showGuessList(List<GuessData> guessDataList) {

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_guess_item, parent, false);

        return new ViewHolder(view);
    }

    private final int[] imageResources = {R.drawable.collection_house_1, R.drawable.collection_house_2, R.drawable.collection_house_3};

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GuessData guessData = list.get(position);
        Random random = new Random();
        int i = random.nextInt(3);
        holder.houseCover.setImageResource(imageResources[i]);
        //中间横线（删除线）
        holder.housePriceBefore.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        // 抗锯齿
        holder.housePriceBefore.getPaint().setAntiAlias(true);

        goodView = new GoodView(holder.itemView.getContext());
        //UI初始化
        if (guessData.isCollected()) {
            //被收藏的推荐
            holder.collectionButton.setImageResource(R.drawable.collection_icon_collect_light);
        } else {
            //未被收藏的推荐
            holder.collectionButton.setImageResource(R.drawable.collection_icon_collect_dark);
        }
        //点击监听
        holder.collectionButton.setOnClickListener(view1 -> {
            if (!guessData.isCollected()) {
                //先进行添加收藏的网络请求 Presenter
                CollectionData collectionsData = presenter.addCollection(list.get(position).getId());
                if (collectionsData == null) {
                    //添加失败
                    ToastUtils.toast("收藏失败！");
                } else {
                    //收藏民宿操作
                    mCollectManager.collectSuccessfulCallback(position, collectionsData);
                    //UI展示
                    holder.collectionButton.setImageResource(R.drawable.collection_icon_collect_light);
                    goodView.setText("收藏成功")
                            .setTextColor(Color.parseColor("#4facfe"))
                            .setDuration(1000)
                            .setTextSize(12)
                            .setDistance(10)
                            .show(holder.itemView);
                    guessData.setCollected(true);
                }
            } else {
                //移除的网络请求
                boolean b = presenter.removeCollection(list.get(position).getId());
                if (!b) {
                    //移除失败
                    ToastUtils.toast("移除收藏失败！");
                } else {
                    //移除收藏操作
                    mCollectManager.removeSuccessfulCallback(list.get(position).getId());
                    //UI展示
                    holder.collectionButton.setImageResource(R.drawable.collection_icon_collect_dark);
                    ToastUtils.toast("移除收藏成功！");
                    guessData.setCollected(false);
                }
            }
        });
        holder.itemView.setOnClickListener(view -> {
            if (!BuildConfig.isModule) {
                ARouter.getInstance().build("/houseInfoView/HouseInfoActivity")
                        .withInt("HouseId", guessData.getId()).navigation();
            } else {
                XToastUtils.error("当前不能跳转！");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * TODO 接口：该接口负责回调猜你喜欢页面的收藏管理按钮
     *      当按钮点亮时会先进行添加收藏的网络请求，如果网络请求有效，调用该回调，将新的CollectionsData添加到收藏列表中并刷新显示
     *      当按钮再次被点击熄灭时表示移除收藏，如果移除的网络请求有效，调用该回调，会将改民宿的ID传给CollectionPage，遍历查找相应的民宿，刷新列表并刷新显示
     */
    public interface CollectionManageButtonClickListener {
        /**
         * @param position 子项下标、位置
         *                 TODO 收藏成功，将新的CollectionsData添加到收藏集合并更新显示
         */
        void collectSuccessfulCallback(int position, CollectionData collectionsData);

        /**
         * @param houseId 民宿ID
         *                TODO 移除收藏成功，猜你喜欢页面成功移除，拿着ID到收藏列表中遍历查找，找到了将它移除并更新显示
         */
        void removeSuccessfulCallback(int houseId);
    }

    private CollectionManageButtonClickListener mCollectManager;

    public void setCollectManager(CollectionManageButtonClickListener collectManager) {
        this.mCollectManager = collectManager;
    }
}
