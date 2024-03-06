package com.xupt3g.houseinfoview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.popupwindow.good.GoodView;
import com.xuexiang.xui.widget.popupwindow.good.IGoodView;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.HouseInfoFragment
 *
 * @author: shallew
 * @data: 2024/3/5 18:19
 * @about: TODO
 */
public class HouseInfoFragment extends Fragment implements View.OnClickListener{
    private View mView;
    private ViewPager2 bannerVp;
    private ProgressBar bannerPb;
    private List<Integer> bannerImages;
    private TabLayout navigationTabLayout;
    private NestedScrollView scrollView;

    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private TextView t5;
    private TabLayout.Tab top;
    private TabLayout.Tab facility;
    private TabLayout.Tab review;
    private TabLayout.Tab notice;
    private TabLayout.Tab recommend;
    private boolean isUsing = false;
    private LinearLayout topLayout;
    private ImageView backImg;
    private ImageView shareImg;
    private ImageView collectImg;
    private ImageView commentImg;
    private boolean isCollected;
    private IGoodView goodView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.houseinfo_frag_house_info, container, false);

        bannerVp = mView.findViewById(R.id.houseInfo_banner_viewPager);
        bannerPb = mView.findViewById(R.id.houseInfo_banner_progressBar);
        navigationTabLayout = mView.findViewById(R.id.houseInfo_tabLayout);
        scrollView = mView.findViewById(R.id.houseInfo_scrollView);
        t1 = mView.findViewById(R.id.t_1);
        t2 = mView.findViewById(R.id.t_2);
        t3 = mView.findViewById(R.id.t_3);
        t4 = mView.findViewById(R.id.t_4);
        t5 = mView.findViewById(R.id.t_5);
        topLayout = mView.findViewById(R.id.houseInfo_top_view);
        backImg = mView.findViewById(R.id.houseInfo_back_icon);
        backImg.setOnClickListener(this);
        shareImg = mView.findViewById(R.id.houseInfo_share_icon);
        shareImg.setOnClickListener(this);
        collectImg = mView.findViewById(R.id.houseInfo_collect_icon);
        collectImg.setOnClickListener(this);
        commentImg = mView.findViewById(R.id.houseInfo_comment_icon);
        commentImg.setOnClickListener(this);

        isCollected = false;
        goodView = new GoodView(getContext());


        bannerImages = new ArrayList<>();

        initBannerView();

        top = navigationTabLayout.newTab().setText("Top");
        navigationTabLayout.addTab(top);
        facility = navigationTabLayout.newTab().setText("设施");
        navigationTabLayout.addTab(facility);
        review = navigationTabLayout.newTab().setText("点评");
        navigationTabLayout.addTab(review);
        notice = navigationTabLayout.newTab().setText("须知");
        navigationTabLayout.addTab(notice);
        recommend = navigationTabLayout.newTab().setText("推荐");
        navigationTabLayout.addTab(recommend);
        navigationTabLayout.selectTab(top);

        setScrollViewPositionChangedListener();

        setTabLayoutSelectedTabChangedListener();



        return mView;
    }

    private final String SHARE_TO_WEIXIN = "share_to_weixin";
    private final String SHARE_TO_WEIBO = "share_to_weibo";
    private final String SHARE_TO_KONGJIAN = "share_to_kongjian";
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.houseInfo_back_icon) {
            //返回按钮点击监听
            //退出活动
            requireActivity().finish();
        } else if (view.getId() == R.id.houseInfo_share_icon) {
            //分享按钮点击监听
            new BottomSheet.BottomGridSheetBuilder(getContext())
                    .addItem(R.drawable.houseinfo_icon_weixin, "分享到微信", SHARE_TO_WEIXIN, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.drawable.houseinfo_icon_weibo, "分享到微博", SHARE_TO_WEIBO, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .addItem(R.drawable.houseinfo_icon_kongjian, "分享到空间", SHARE_TO_KONGJIAN, BottomSheet.BottomGridSheetBuilder.FIRST_LINE)
                    .setOnSheetItemClickListener((dialog, itemView) -> {
                        dialog.dismiss();
                        String tag = (String) itemView.getTag();
                        if (SHARE_TO_WEIXIN.equals(tag)) {
                            XToastUtils.info("分享到微信");
                        } else if (SHARE_TO_WEIBO.equals(tag)) {
                            XToastUtils.info("分享到微博");
                        } else if (SHARE_TO_KONGJIAN.equals(tag)) {
                            XToastUtils.info("分享到空间");
                        }
                    }).build().show();
        } else if (view.getId() == R.id.houseInfo_collect_icon) {
            //收藏按钮点击监听
            if (!isCollected) {
                //当前未收藏
                collectImg.setImageResource(R.drawable.houseinfo_icon_collect_after);
                goodView.setText("收藏成功")
                        .setTextColor(Color.parseColor("#4facfe"))
                        .setTextSize(12)
                        .setDuration(1000)
                        .setDistance(120)
                        .show(view);
                isCollected = true;
            } else {
                //当前已收藏
                collectImg.setImageResource(R.drawable.houseinfo_icon_collect_before);
                isCollected = false;
            }
        } else if (view.getId() == R.id.houseInfo_comment_icon) {
            //写评论按钮点击监听
            ToastUtils.toast("跳转去为这间民宿写评论");
        }
    }

    /**
     * TODO 设置TabLayout标签改变时的监听
     */
    private void setTabLayoutSelectedTabChangedListener() {
        navigationTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView == null) {
                    tab.setCustomView(R.layout.house_tab_text_layout);
                }
                TextView textView = tab.getCustomView().findViewById(android.R.id.text1);
                textView.setTextAppearance(R.style.tabIndicatorselectedText);

                if (!isUsing) {
                    int tabLayoutHeight = navigationTabLayout.getBottom();
                    if (tab.getPosition() == 0) {
                        isUsing = true;
                        //第0项
                        scrollView.smoothScrollTo(0, 0);
                    } else if (tab.getPosition() == 1) {
                        isUsing = true;
                        scrollView.smoothScrollTo(0, t2.getTop() - tabLayoutHeight);
                    } else if (tab.getPosition() == 2) {
                        isUsing = true;
                        scrollView.smoothScrollTo(0, t3.getTop() - tabLayoutHeight);
                    } else if (tab.getPosition() == 3) {
                        isUsing = true;
                        scrollView.smoothScrollTo(0, t4.getTop() - tabLayoutHeight);
                    } else if (tab.getPosition() == 4) {
                        isUsing = true;
                        scrollView.smoothScrollTo(0, t5.getTop() - tabLayoutHeight);
                    }
                    isUsing = false;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView == null) {
                    tab.setCustomView(R.layout.house_tab_text_layout);
                }
                TextView textView = tab.getCustomView().findViewById(android.R.id.text1);
                textView.setTextAppearance(R.style.tabIndicatorUnselectedText);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /**
     * TODO 设置ScrollView滑动位置改变时的监听
     */
    private void setScrollViewPositionChangedListener() {
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (!isUsing) {
                    int tabLayoutHeight = navigationTabLayout.getBottom();
                    int mScrollY = scrollY + tabLayoutHeight;
                    if (mScrollY <= bannerVp.getBottom()) {
                        topViewColorChange(true);
                        navigationTabLayout.setVisibility(View.GONE);
                    } else if (mScrollY >= t1.getTop() && mScrollY < t2.getTop()) {
                        isUsing = true;
                        topViewColorChange(false);
                        navigationTabLayout.setVisibility(View.VISIBLE);
                        navigationTabLayout.selectTab(top);
                        navigationTabLayout.setScrollPosition(0, 0f, true);
                    } else if (mScrollY >= t2.getTop() && mScrollY < t3.getTop()) {
                        isUsing = true;
                        topViewColorChange(false);
                        navigationTabLayout.setVisibility(View.VISIBLE);
                        navigationTabLayout.selectTab(facility);
                        navigationTabLayout.setScrollPosition(1, 0f, true);
                    } else if (mScrollY >= t3.getTop() && mScrollY < t4.getTop()) {
                        isUsing = true;
                        topViewColorChange(false);
                        navigationTabLayout.setVisibility(View.VISIBLE);
                        navigationTabLayout.selectTab(review);
                        navigationTabLayout.setScrollPosition(2, 0f, true);
                    } else if (mScrollY >= t4.getTop() && mScrollY < t5.getTop()) {
                        isUsing = true;
                        topViewColorChange(false);
                        navigationTabLayout.setVisibility(View.VISIBLE);
                        navigationTabLayout.selectTab(notice);
                        navigationTabLayout.setScrollPosition(3, 0f, true);
                    } else if (mScrollY >= t5.getTop()) {
                        isUsing = true;
                        topViewColorChange(false);
                        navigationTabLayout.selectTab(recommend);
                        navigationTabLayout.setVisibility(View.VISIBLE);
                        navigationTabLayout.setScrollPosition(4, 0f, true);
                    }
                    isUsing = false;
                }
            }
        });
    }

    /**
     *
     * @param iconColorIsWhite 图标颜色是否为白色
     * TODO 根据ScrollView动态改变顶部图标的颜色和背景的颜色
     */
    private void topViewColorChange(boolean iconColorIsWhite) {
        if (iconColorIsWhite) {
            backImg.setColorFilter(Color.WHITE);
            collectImg.setColorFilter(Color.WHITE);
            commentImg.setColorFilter(Color.WHITE);
            shareImg.setColorFilter(Color.WHITE);
            //背景透明
            topLayout.setBackgroundColor(Color.TRANSPARENT);
        } else {
            backImg.setColorFilter(Color.parseColor("#3E4055"));
            collectImg.setColorFilter(Color.parseColor("#3E4055"));
            commentImg.setColorFilter(Color.parseColor("#3E4055"));
            shareImg.setColorFilter(Color.parseColor("#3E4055"));
            //背景透明
            topLayout.setBackgroundColor(Color.WHITE);
        }
    }

    private void initBannerView() {
        Collections.addAll(bannerImages, R.drawable.houseinfo_house_1, R.drawable.houseinfo_house_2, R.drawable.houseinfo_house_3);
        bannerVp.setAdapter(new BannerAdapter(getContext(), bannerImages));
        bannerPb.setMax(bannerImages.size());

        bannerVp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                //当滚动到该页面
                bannerPb.setProgress(position + 1);
            }
        });
    }
}
