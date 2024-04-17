package com.xupt3g.browsinghistoryview.view;

import static androidx.recyclerview.widget.OrientationHelper.VERTICAL;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.adapter.recyclerview.DividerItemDecoration;
import com.xuexiang.xui.adapter.recyclerview.sticky.StickyHeadContainer;
import com.xuexiang.xui.adapter.recyclerview.sticky.StickyItemDecoration;
import com.xuexiang.xui.adapter.simple.XUISimpleAdapter;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.button.RippleView;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.popupwindow.popup.XUIListPopup;
import com.xuexiang.xui.widget.popupwindow.popup.XUIPopup;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.browsinghistoryview.R;
import com.xupt3g.browsinghistoryview.model.retrofit.HistoryData;
import com.xupt3g.browsinghistoryview.presenter.BrowsingHistoryPresenter;
import com.xupt3g.browsinghistoryview.view.adapter.MyExpandableListAdapter;
import com.xupt3g.browsinghistoryview.view.entity.StickyItem;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xupt3g.mylibrary1.implservice.BrowsedHistoryManagerService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.view.BrowsingHistoryFragment
 *
 * @author: shallew
 * @data:2024/2/22 0:51
 * @about: TODO
 */
public class BrowsingHistoryFragment extends Fragment implements BrowsingHistoryUiImpl {

    private View mView;
    private RecyclerView historyRecycler;
    private List<StickyItem> dataList;
    /**
     * 一周内（最近）的浏览
     */
    private List<StickyItem> weekBrowsedList;
    /**
     * 一月内的浏览
     */
    private List<StickyItem> monthBrowsedList;
    /**
     * 一季（三个月）内的浏览
     */
    private List<StickyItem> seasonBrowsedList;
    /**
     * 更早的浏览
     */
    private List<StickyItem> earlierBrowsedList;
    private StickyHeadContainer stickyHeadContainer;
    private TextView stickyHeaderTitle;
    private XUIListPopup mListPopup;
    private List<String> headerTitles = new ArrayList<>();
    private List<Integer> headerIndexList = new ArrayList<>();
    private MyExpandableListAdapter adapter;
    private int WEEK_GROUP_HEADER_INDEX;
    private int MONTH_GROUP_HEADER_INDEX;
    private int SEASON_GROUP_HEADER_INDEX;
    private int EARLIER_GROUP_HEADER_INDEX;
    /**
     * 历史数据展示布局
     */
    private FrameLayout historyPageLayout;
    /**
     * 缺省页布局
     */
    private ConstraintLayout defaultPageLayout;
    /**
     * 缺省页图像
     */
    private ImageView defaultImage;
    /**
     * 缺省页提示文本
     */
    private TextView defaultHintText;
    /**
     * 缺省页按钮文本
     */
    private TextView defaultBtnText;
    /**
     * 缺省页按钮
     */
    private RippleView defaultButton;
    private BrowsingHistoryPresenter presenter;
    private AppCompatImageView backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.history_frag_browsing_history, container, false);
        //订阅
        EventBus.getDefault().register(this);

        //历史数据展示页布局初始化（变量声明）
        backBtn = (AppCompatImageView) mView.findViewById(R.id.history_backbtn);
        backBtn.setOnClickListener(view -> requireActivity().finish());
        historyRecycler = (RecyclerView) mView.findViewById(R.id.history_recycler);
        stickyHeadContainer = (StickyHeadContainer) mView.findViewById(R.id.sticky_container);
        stickyHeaderTitle = (TextView) mView.findViewById(R.id.stv_title);

        historyPageLayout = (FrameLayout) mView.findViewById(R.id.history_page_layout);

        //缺省页布局初始化（变量声明）
        defaultPageLayout = (ConstraintLayout) mView.findViewById(R.id.default_page_layout);
        defaultImage = (ImageView) mView.findViewById(R.id.default_image);
        defaultHintText = (TextView) mView.findViewById(R.id.default_text);
        defaultButton = (RippleView) mView.findViewById(R.id.ripple_to_homepage);
        defaultBtnText = (TextView) mView.findViewById(R.id.default_button_text);

        presenter = new BrowsingHistoryPresenter(this);
        LoginStatusData.getLoginStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
                    //如果未登录
                    //缺省页初始化
                    defaultViewInit(R.drawable.history_not_login, "您还没有登录，请先完成登录！", "点我去登录", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!BuildConfig.isModule) {
                                //集成模式下可跳转
                                ARouter.getInstance().build("/loginRegistration/LoginActivity").navigation();
                            } else {
                                XToastUtils.error("当前不可跳转");
                            }
                        }
                    });
                } else {
                    //如果已登录 正常获取浏览历史记录
                    //隐藏未登录页面
                    defaultPageLayout.setVisibility(View.GONE);
                    historyPageLayout.setVisibility(View.VISIBLE);
                    //进行数据处理 Presenter
                    presenter.getHistoryListShowOnUi();
                }

            }
        });
        return mView;
    }

    private void defaultViewInit(int drawableRes, String hintText, String buttonText, View.OnClickListener onClickListener) {

        Glide.with(requireContext()).asGif().load(drawableRes).into(defaultImage);
        defaultHintText.setText(hintText);
        defaultBtnText.setText(buttonText);
        defaultButton.setOnClickListener(onClickListener);

        historyPageLayout.setVisibility(View.GONE);
        defaultPageLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        stickyHeadContainer.recycle();
        super.onDestroyView();
    }

    /**
     * @param listItems 字符串列表
     * @param view      生成列表弹窗的根View
     *                  TODO 生成列表弹窗
     */
    public void listPopup(List<String> listItems, View view) {
        //将字符数组转成adapter
        XUISimpleAdapter simpleAdapter = XUISimpleAdapter.create(getContext(), listItems);
        mListPopup = new XUIListPopup(requireContext(), simpleAdapter);
        mListPopup.create(DensityUtils.dp2px(view.getContext(), 200), DensityUtils.dp2px(view.getContext(), 200), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == headerTitles.size() - 1) {
                    //最后一项被点击了
                    new MaterialDialog.Builder(view.getContext())
                            .title("清空浏览记录")
                            .content("清空后无法恢复，确认清空吗？")
                            .positiveText("确认")
                            .negativeText("取消")
                            .buttonsGravity(GravityEnum.START)
                            .titleColor(Color.WHITE)
                            .contentColor(Color.parseColor("#e3eeff"))
                            .backgroundColor(Color.parseColor("#3E4055"))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    //清空历史记录 Presenter
                                    if (presenter.clearHistoryList() && dataList != null && adapter != null) {
                                        dataList.clear();
                                        historyRecycler.setVisibility(View.INVISIBLE);
                                        stickyHeadContainer.onInVisible();
                                        //生成缺省页
                                        defaultViewInit(R.drawable.history_no_history, "未找到您的浏览记录，去首页看看吧！", "点我去首页", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (!BuildConfig.isModule) {
                                                    //在集成模式下可跳转
                                                    ARouter.getInstance().build("/homepage/HomepageActivity").navigation();
                                                } else {
                                                    XToastUtils.error("当前不可跳转");
                                                }
                                            }
                                        });
                                        ToastUtils.toast("已清空");
                                    }
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                }
                            }).show();
                    mListPopup.dismiss();
                    return;
                }
                ((LinearLayoutManager) Objects.requireNonNull(historyRecycler.getLayoutManager())).scrollToPositionWithOffset(headerIndexList.get(i), 0);
                mListPopup.dismiss();
            }
        });
        mListPopup.setAnimStyle(XUIPopup.ANIM_GROW_FROM_CENTER);
        mListPopup.setPreferredDirection(XUIPopup.DIRECTION_TOP);
        mListPopup.show(view);
    }

    private void processData(List<HistoryData> historyDataList) throws ParseException {
        weekBrowsedList = new ArrayList<>();
        monthBrowsedList = new ArrayList<>();
        seasonBrowsedList = new ArrayList<>();
        earlierBrowsedList = new ArrayList<>();
        dataList = new ArrayList<>();


        //遍历数据集合
        for (int i = 0; i < historyDataList.size(); i++) {
            //根据HistoryData的browsingTime进行分组
            HistoryData historyData = historyDataList.get(i);

            long diffInDays = TimeUnit.DAYS.convert(System.currentTimeMillis() - historyData.getBrowsingTime() * 1000, TimeUnit.MILLISECONDS);

            if (diffInDays < 0) {
                //有错
                continue;
            }
            if (diffInDays <= 7) {
                weekBrowsedList.add(new StickyItem(historyData));
            } else if (diffInDays <= 30) {
                monthBrowsedList.add(new StickyItem(historyData));
            } else if (diffInDays <= 90) {
                seasonBrowsedList.add(new StickyItem(historyData));
            } else {
                earlierBrowsedList.add(new StickyItem(historyData));
            }
        }
        //分组完成放到dataList中进行显示
        if (weekBrowsedList.size() > 0) {
            dataList.add(new StickyItem("最近的浏览"));
            dataList.addAll(weekBrowsedList);
        }
        WEEK_GROUP_HEADER_INDEX = 0;
        if (monthBrowsedList.size() > 0) {
            dataList.add(new StickyItem("一月内的浏览"));
            dataList.addAll(monthBrowsedList);
        }
        MONTH_GROUP_HEADER_INDEX = weekBrowsedList.size() + 1;
        if (seasonBrowsedList.size() > 0) {
            dataList.add(new StickyItem("一季内的浏览"));
            dataList.addAll(seasonBrowsedList);
        }
        SEASON_GROUP_HEADER_INDEX = MONTH_GROUP_HEADER_INDEX + monthBrowsedList.size() + 1;
        if (earlierBrowsedList.size() > 0) {
            dataList.add(new StickyItem("更早的浏览"));
            dataList.addAll(earlierBrowsedList);
        }
        EARLIER_GROUP_HEADER_INDEX = SEASON_GROUP_HEADER_INDEX + seasonBrowsedList.size() + 1;
    }

    /**
     * @param historyDataList 从Model层进行网络请求获取的浏览历史集合
     *                        TODO 在Ui上展示获取到的历史数据
     */
    @Override
    public void showHistoryListOnUi(List<HistoryData> historyDataList) {
        if (historyDataList == null || historyDataList.size() == 0) {
            //如果获取到的数据集合为空 可能未登录或请求失败
            //未登录的情况前面已经判断 应该是请求失败
            defaultViewInit(R.drawable.history_no_history, "未找到您的浏览记录，去首页看看吧！", "点我去首页", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!BuildConfig.isModule) {
                        //在集成模式下可跳转
                        ARouter.getInstance().build("/homepage/HomepageActivity").navigation();
                    } else {
                        XToastUtils.error("当前不可跳转");
                    }
                }
            });
            return;
        }

        try {
            //对数据进行分组，并初始化dataList集合
            processData(historyDataList);

            historyRecyclerInit();
        } catch (ParseException e) {
            XToastUtils.error(e.toString() + " 时间转化错误！");
        }
    }

    /**
     * TODO 配置并显示历史记录的RecyclerView
     */
    private void historyRecyclerInit() {
        stickyHeadContainer.setOnStickyPositionChangedListener(new StickyHeadContainer.OnStickyPositionChangedListener() {
            @Override
            public void onPositionChanged(int position) {
                StickyItem item = dataList.get(position);
                if (item != null) {
                    stickyHeaderTitle.setText(item.getHeadTitle());
                }
            }
        });

        for (int i = 0; i < dataList.size(); i++) {
            StickyItem item = dataList.get(i);
            if (item != null && item.isHeadSticky()) {
                headerTitles.add(item.getHeadTitle());
                headerIndexList.add(i);
            }
        }
        headerTitles.add("清空浏览记录");
        stickyHeadContainer.setOnClickListener(view -> {

            listPopup(headerTitles, view);

        });


        historyRecycler.addItemDecoration(new DividerItemDecoration(historyRecycler.getContext(), VERTICAL));

        StickyItemDecoration stickyItemDecoration = new StickyItemDecoration(stickyHeadContainer, MyExpandableListAdapter.TYPE_HEAD_STICKY);
        historyRecycler.addItemDecoration(stickyItemDecoration);
        historyRecycler.setItemAnimator(new DefaultItemAnimator());

        historyRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new MyExpandableListAdapter(dataList, historyRecycler, this);

        historyRecycler.setAdapter(adapter);
        animationInit(R.anim.history_recycler_anim);
    }

    /**
     * @param animRes 动画资源
     *                TODO 给RecyclerView的子项设置入场动画
     */
    private void animationInit(int animRes) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), animRes);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        layoutAnimationController.setDelay(0.2f);
        historyRecycler.setLayoutAnimation(layoutAnimationController);
    }

    /**
     * @param position 要删除的子项在集合中的下标
     * @return 返回是否成功删除
     * TODO 删除历史子项 更新集合和RecyclerView
     */
    @SuppressLint("NotifyDataSetChanged")
    private boolean deleteHistoryItemFromList(int position) {
        StickyItem item = dataList.remove(position);

        if (weekBrowsedList.remove(item)) {
            MONTH_GROUP_HEADER_INDEX--;
            SEASON_GROUP_HEADER_INDEX--;
            EARLIER_GROUP_HEADER_INDEX--;
            if (weekBrowsedList.size() == 0) {
                //该分组为空
                dataList.remove(WEEK_GROUP_HEADER_INDEX);
                MONTH_GROUP_HEADER_INDEX--;
                SEASON_GROUP_HEADER_INDEX--;
                EARLIER_GROUP_HEADER_INDEX--;
            }
        }
        if (monthBrowsedList.remove(item)) {
            SEASON_GROUP_HEADER_INDEX--;
            EARLIER_GROUP_HEADER_INDEX--;
            if (monthBrowsedList.size() == 0) {
                //该分组为空
                dataList.remove(MONTH_GROUP_HEADER_INDEX);
                SEASON_GROUP_HEADER_INDEX--;
                EARLIER_GROUP_HEADER_INDEX--;
            }
        }
        if (seasonBrowsedList.remove(item)) {
            EARLIER_GROUP_HEADER_INDEX--;
            if (seasonBrowsedList.size() == 0) {
                //该分组为空
                dataList.remove(SEASON_GROUP_HEADER_INDEX);
                EARLIER_GROUP_HEADER_INDEX--;
            }
        }
        if (earlierBrowsedList.remove(item)) {
            if (earlierBrowsedList.size() == 0) {
                //该分组为空
                dataList.remove(EARLIER_GROUP_HEADER_INDEX);
            }
        }
        if (dataList.size() == 0) {
            //没有元素 数据清空了
            stickyHeadContainer.onInVisible();
            //生成缺省页
            defaultViewInit(R.drawable.history_no_history, "未找到您的浏览记录，去首页看看吧！", "点我去首页", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!BuildConfig.isModule) {
                        //在集成模式下可跳转
                        ARouter.getInstance().build("/homepage/HomepageActivity").navigation();
                    } else {
                        XToastUtils.error("当前不可跳转");
                    }
                }
            });
        }
        adapter.notifyDataSetChanged();
        historyRecycler.scheduleLayoutAnimation();
        return true;
    }

    @Subscribe
    public void onHistoryItemChanged(String tag) {
        if (tag.equals(BrowsedHistoryManagerService.BROWSED_HISTORY_HAS_CHANGED)) {
            Log.d("remoteHistory", "onHistoryItemChanged: 更新浏览历史列表");
            presenter.getHistoryListShowOnUi();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
