package com.xupt3g.browsinghistoryview.view.adapter;


import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.adapter.recyclerview.sticky.FullSpanUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.layout.ExpandableLayout;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.browsinghistoryview.R;
import com.xupt3g.browsinghistoryview.model.retrofit.HistoryData;
import com.xupt3g.browsinghistoryview.presenter.BrowsingHistoryPresenter;
import com.xupt3g.browsinghistoryview.view.BrowsingHistoryUiImpl;
import com.xupt3g.browsinghistoryview.view.entity.StickyItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.view.adapter.MyExpandableListAdapter
 *
 * @author: shallew
 * @data:2024/2/22 1:14
 * @about: TODO
 */
public class MyExpandableListAdapter extends RecyclerView.Adapter<MyExpandableListAdapter.ViewHolder> implements BrowsingHistoryUiImpl {
    private final List<StickyItem> mList;
    private final RecyclerView mRecyclerView;
    /**
     * 头部标题
     */
    public static final int TYPE_HEAD_STICKY = 1;
    /**
     * 新闻信息
     */
    private static final int TYPE_NEW_INFO = 2;
    private static int loadWhichView;
    private BrowsingHistoryPresenter presenter;
    private LifecycleOwner owner;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        FullSpanUtils.onAttachedToRecyclerView(recyclerView, this, TYPE_HEAD_STICKY);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        FullSpanUtils.onViewAttachedToWindow(holder, this, TYPE_HEAD_STICKY);
    }

    public MyExpandableListAdapter(List<StickyItem> mList, RecyclerView mRecyclerView, LifecycleOwner owner) {
        this.mList = mList;
        this.mRecyclerView = mRecyclerView;
        this.presenter = new BrowsingHistoryPresenter(this);
        this.owner = owner;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).isHeadSticky()) {
            //粘顶 说明是组标题
            return TYPE_HEAD_STICKY;
        } else {
            return TYPE_NEW_INFO;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_HEAD_STICKY) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_sticky_title, parent, false);
            loadWhichView = TYPE_HEAD_STICKY;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_adapter_expendable_list_item, parent, false);
            loadWhichView = TYPE_NEW_INFO;
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StickyItem item = mList.get(position);
        if (item == null) {
            return;
        }

        if (item.isHeadSticky()) {
            //如果是粘顶组标题
            holder.stickyHeaderText.setText(item.getHeadTitle());
            if (position != 0) {
                //第一个可以显示更多Action
                //因为StickyHeaderContainer在列表没有滑动时没有
                holder.stickyHeaderAction.setVisibility(View.GONE);
            }
        } else {
            //如果是不粘顶正文
            holder.expandableLayout.setInterpolator(new OvershootInterpolator());
            //展开动作监听
            holder.indicator.setOnClickListener(view -> {
                holder.isExpanded = !holder.isExpanded;
                holder.expandableLayout.setExpanded(holder.isExpanded, true);
            });

            holder.expandableLayout.setOnExpansionChangedListener((expansion, state) -> {
                if (mRecyclerView != null && state == ExpandableLayout.State.EXPANDING) {
                    mRecyclerView.smoothScrollToPosition(position);
                }
                if (holder.indicator != null) {
                    holder.indicator.setRotation(expansion * 90);
                }
            });


            // 设置不粘顶正文子项的数据
            Random random = new Random();

            holder.houseCover.setImageResource(drawableRes[random.nextInt(3)]);
            try {
                Date time = new Date(item.getHistoryData().getBrowsingTime());
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("y/M/d");
                holder.lastBrowsedTime.setText(simpleDateFormat.format(time));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            //中间横线（删除线）
            holder.priceBefore.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            // 抗锯齿
            holder.priceBefore.getPaint().setAntiAlias(true);

            holder.expandableLayoutTitle.setOnClickListener(view -> {
                //由此跳转至民宿详情页面
                if (item.getHistoryData().getRowState() == 0) {
                    //已下架
                    ToastUtils.toast("该民宿已下架");
                } else {
                    if (!BuildConfig.isModule) {
                        ARouter.getInstance().build("/houseInfoView/HouseInfoActivity")
                                .withInt("HouseId", item.getHistoryData().getId()).navigation();
                    } else {
                        XToastUtils.error("当前不可跳转！");
                    }
                }
            });

            holder.expandCollectButton.setOnClickListener(view -> {
                MutableLiveData<Boolean> booleanLiveData = presenter.passRequestOfAddCollection(item.getHistoryData().getId());
                booleanLiveData.observe(owner, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            ToastUtils.toast("添加成功");
                        } else {
                            XToastUtils.error("收藏失败");
                        }
                    }
                });
            });

            holder.expandDeleteButton.setOnClickListener(view -> {
                boolean b = presenter.passRequestOfRemoveHistory(item.getHistoryData().getId());
                if (b) {
                    boolean b1 = mDeleteHistoryItemListener.deleteHistoryItem(position);
                    if (b1) {
                        ToastUtils.toast("删除成功");
                    }
                } else {
                    XToastUtils.error("删除失败");
                }
            });
            if (item.getHistoryData().getRowState() == 0) {
                //已下架
                holder.darkOverlayView.setVisibility(View.VISIBLE);
            } else {
                holder.darkOverlayView.setVisibility(View.GONE);
            }
        }
    }

    private final int[] drawableRes = {R.drawable.history_house_1, R.drawable.history_house_2, R.drawable.history_house_3};

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void showHistoryListOnUi(List<HistoryData> historyDataList) {
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView houseCover;
        private TextView houseTitle;
        private TextView houseIntro;
        private TextView houseFrom;
        private TextView lastBrowsedTime;
        private TextView priceBefore;
        private TextView priceAfter;
        private CardView darkOverlayView;
        private AppCompatImageView indicator;
        private ExpandableLayout expandableLayout;
        private LinearLayout expandableLayoutTitle;
        private TextView expandCollectButton;
        private TextView expandDeleteButton;
        /**
         * 是否是展开的
         */
        private boolean isExpanded;

        private TextView stickyHeaderText;
        private TextView stickyHeaderAction;

        @SuppressLint("UseCompatLoadingForDrawables")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("Type", "ViewHolder: " + loadWhichView);
            if (loadWhichView == TYPE_NEW_INFO) {
                houseCover = (ImageView) itemView.findViewById(R.id.item_house_cover_img);
                houseTitle = (TextView) itemView.findViewById(R.id.item_house_title);
                houseIntro = (TextView) itemView.findViewById(R.id.item_brief_intro);
                houseFrom = (TextView) itemView.findViewById(R.id.house_from);
                lastBrowsedTime = (TextView) itemView.findViewById(R.id.item_last_browsed_time);
                priceBefore = (TextView) itemView.findViewById(R.id.item_price_before);
                priceAfter = (TextView) itemView.findViewById(R.id.item_price_after);
                darkOverlayView = (CardView) itemView.findViewById(R.id.dark_overlay_layout);

                indicator = (AppCompatImageView) itemView.findViewById(R.id.iv_indicator);
                expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
                expandableLayoutTitle = (LinearLayout) itemView.findViewById(R.id.fl_title);
                expandCollectButton = (TextView) itemView.findViewById(R.id.expand_collect);
                expandDeleteButton = (TextView) itemView.findViewById(R.id.expand_delete);

                isExpanded = false;//默认不展开

            } else if (loadWhichView == TYPE_HEAD_STICKY) {
                stickyHeaderText = (TextView) itemView.findViewById(R.id.stv_title);
                stickyHeaderAction = (TextView) itemView.findViewById(R.id.stv_action);
            }

        }
    }

    public interface DeleteHistoryItemListener {
        /**
         * @param position 该历史子项在dataList中的下标
         * @return 是否删除成功
         */
        boolean deleteHistoryItem(int position);
    }

    private DeleteHistoryItemListener mDeleteHistoryItemListener;

    public void setDeleteHistoryItemListener(DeleteHistoryItemListener deleteHistoryItemListener) {
        this.mDeleteHistoryItemListener = deleteHistoryItemListener;
    }

}
