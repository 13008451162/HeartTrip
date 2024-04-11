package com.xupt3g.houseinfoview.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.libbase.BuildConfig;
import com.google.android.material.tabs.TabLayout;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.GravityEnum;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.flowlayout.FlowTagLayout;
import com.xuexiang.xui.widget.layout.ExpandableLayout;
import com.xuexiang.xui.widget.popupwindow.ViewTooltip;
import com.xuexiang.xui.widget.popupwindow.good.GoodView;
import com.xuexiang.xui.widget.popupwindow.good.IGoodView;
import com.xuexiang.xui.widget.progress.ratingbar.ScaleRatingBar;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.houseinfoview.view.adapter.BannerAdapter;
import com.xupt3g.houseinfoview.view.adapter.FlowLayoutAdapter;
import com.xupt3g.houseinfoview.R;
import com.xupt3g.houseinfoview.view.adapter.RecommendHousesAdapter;
import com.xupt3g.houseinfoview.VideoPlayerView;
import com.xupt3g.houseinfoview.model.retrofit.HouseInfoBaseData;
import com.xupt3g.houseinfoview.model.retrofit.RecommendHouse;
import com.xupt3g.houseinfoview.presenter.HouseInfoPresenter;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xupt3g.mylibrary1.PublicRetrofit;
import com.xupt3g.mylibrary1.implservice.TopCommentData;
import com.xupt3g.mylibrary1.implservice.TopCommentGetService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.view.HouseInfoFragment
 *
 * @author: shallew
 * @data: 2024/3/5 18:19
 * @about: TODO 民宿详情页面Fragment
 */
public class HouseInfoFragment extends Fragment implements View.OnClickListener, HouseInfoShowImpl {
    private View mView;
    private ViewPager2 bannerVp;
    private ProgressBar bannerPb;
    private ConstraintLayout bannerLayout;
    private TabLayout navigationTabLayout;
    private NestedScrollView scrollView;
    private ConstraintLayout view1;
    private ConstraintLayout view2;
    private ConstraintLayout view3;
    private ConstraintLayout view4;
    private ConstraintLayout view5;
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

    //第一个视图的控件变量
    /**
     * 流标签布局
     */
    private FlowTagLayout flowTagLayoutInFirstView;
    /**
     * 流标签集合
     */
    private List<String> flowLayoutTagsInFirstView;
    private FlowLayoutAdapter flowLayoutAdapterInFirstView;
    /**
     * 优惠券点击视图
     */
    private CardView couponCardViewInFirstView;
    /**
     * 定位的视图
     */
    private CardView locateCardViewInFirstView;
    /**
     * 第一个View的标题
     */
    private TextView houseTitleInFirstView;
    /**
     * 预约时间的视图
     */
    private CardView timeCardViewInFirstView;
    /**
     * 评分的视图
     */
    private CardView scoreCardViewInFirstView;
    /**
     * 详细的定位信息
     */
    private TextView detailedLocationInFirstView;
    /**
     * 预约的开始入住时间 03月08日这样的 两位月份和日 0占位
     */
    private TextView startLiveTimeInFirstView;
    /**
     * 预约的结束时间 03月09日 同上
     */
    private TextView endLiveTimeInFirstView;
    /**
     * 共住几晚 共3晚 这样的
     */
    private TextView totalDaysInFirstView;
    /**
     * 民宿的评分 0~5分浮点数 一位小数
     */
    private TextView scoreTextInFirstView;
    /**
     * 共多少条点评 共20条点评 这样的
     */
    private TextView totalCommentsInFirstView;

    //第二个视图的控件变量
    /**
     * 基础设施标签流布局
     */
    private FlowTagLayout baseFacilitiesFlowTagLayoutInSecondView;
    /**
     * 基础设施标签适配器
     */
    private FlowLayoutAdapter baseFacilitiesTagsAdapterInSecondView;
    /**
     * 基础设施总数TextView 共多少项 共11项 这样的
     */
    private TextView totalBaseFacilitiesCountInSecondView;
    /**
     * 民宿面积标签text 多少平这样的 50平
     */
    private TextView areaTagTextInSecondView;
    /**
     * 房间配置标签text 几室几厅几卫几厨 这样的 1室1厅1卫1厨
     */
    private TextView roomConfigTextInSecondView;
    /**
     * 卫浴设施的流标签布局
     */
    private FlowTagLayout bathFlowTagLayoutInSecondView;
    /**
     * 卫浴设施的流标签适配器
     */
    private FlowLayoutAdapter bathFlowTagsAdapter;
    /**
     * 服务设施流标签布局
     */
    private FlowTagLayout serviceFlowTagLayoutInSecondView;
    /**
     * 服务设施的流标签适配器
     */
    private FlowLayoutAdapter serviceFlowTagsAdapter;
    /**
     * 居家设施流标签布局
     */
    private FlowTagLayout livingFlowTagLayoutInSecondView;
    /**
     * 居家设施的流标签适配器
     */
    private FlowLayoutAdapter livingFlowTagsAdapter;
    /**
     * 安全设施流标签布局
     */
    private FlowTagLayout safetyFlowTagLayoutInSecondView;
    /**
     * 安全设施的流标签适配器
     */
    private FlowLayoutAdapter safetyFlowsAdapter;
    /**
     * 周边设施流标签布局
     */
    private FlowTagLayout surroundingFlowTagLayoutInSecondView;
    /**
     * 周边设施的流标签适配器
     */
    private FlowLayoutAdapter surroundingFlowTagsAdapter;
    /**
     * 向下展开的按钮
     */
    private ImageView expandImageInSecondView;
    /**
     * 向下展开显示所有设施的布局
     */
    private ExpandableLayout expandableForAllFacilitiesInSecondView;
    /**
     * 标记设施是否全部展开
     */
    private boolean facilitiesIsExpandedInSecondView;
    private GSYVideoOptionBuilder optionBuilderInSecondView;
    private VideoPlayerView playerViewInSecondView;

    //第三个视图View的控件变量
    //点击什么都跳转到评论区页面
    /**
     * 跳转查看所有评论（评论区）
     */
    private TextView gotoAllCommentsInThirdView;
    /**
     * 评论主视图
     */
    private ConstraintLayout commentLayoutInThirdView;
    //要显示的数据
    /**
     * 标题一样醒目的一个总评分数
     */
    private TextView titleScoreTextInThirdView;
    /**
     * 评星栏
     */
    private ScaleRatingBar ratingBarInThirdView;
    /**
     * 评论总条数 格式 （共320条点评） 320换成数据
     */
    private TextView totalCommentsCountInThirdView;
    //显示最热门的一条用户点评
    /**
     * 用户头像
     */
    private CircleImageView userAvatarInThirdView;
    /**
     * 用户昵称
     */
    private TextView userNicknameInThirdView;
    /**
     * 用户发表这条评论的时间 "2024年03月留下点评" 这样的
     */
    private TextView userPostedTimeInThirdView;
    /**
     * 用户给出的评分 “他给出5.0分的评价” 这样的 5.0可替换
     */
    private TextView userRatingScoreInThirdView;
    /**
     * 用户评论的文本内容 最大显示4行
     */
    private TextView userContentInThirdView;
    /**
     * 用户评论的文本内容过长被折叠 点击跳转评论区查看完整评论 默认可见性为gone
     */
    private TextView gotoExpandInThirdView;
    /**
     * 这条评论被点赞的数量
     */
    private TextView likedCountInThirdView;
    /**
     * 评论区第一二三张图片。默认三张照片可见性为gone 有一张显示一张 如果大于三张显示"更多"蒙层
     */
    private ImageView picture1InThirdView;
    private ImageView picture2InThirdView;
    private ImageView picture3InThirdView;
    /**
     * 将上面三个图片控件放入一个人数组中方便管理
     */
    private ImageView[] pictureArrayInThirdView;
    /**
     * 照片上面的的灰色蒙层 可见性默认为gone 当评论照片大于三张时显示在第三张照片上
     */
    private FrameLayout pictureOverlayInThirdView;
    //第四个视图View的控件变量
    /**
     * 房东的头像
     */
    private CircleImageView landlordAvatarInFourthView;
    /**
     * 房东的昵称
     */
    private TextView landlordNicknameInFourthView;
    /**
     * 点击向房东咨询的页面
     */
    private TextView consultToLandlordInFourthView;
    /**
     * 点击跳转房东的个人主页
     */
    private TextView gotoLandlordPageInFourthView;
    //第五个视图View的控件变量
    /**
     * 同类推荐的RecyclerView列表
     */
    private RecyclerView recommendRecycler;
    /**
     * 推荐列表
     */
    private List<RecommendHouse> recommendHouseList;
    /**
     * 推荐列表的适配器
     */
    private RecommendHousesAdapter recommendHousesAdapter;
    //底部视图View的控件变量
    /**
     * 底部的咨询按钮
     */
    private TextView consultInBottomView;
    /**
     * 底部的折后价格
     */
    private TextView priceAfterInBottomView;
    /**
     * 底部的折前价格
     */
    private TextView priceBeforeInBottomView;
    /**
     * 底部的已经优惠的价格
     */
    private TextView hasDiscountInBottomView;
    /**
     * 底部的预定按钮 （已下架、选择时间）
     */
    private TextView bookInBottomView;

    /**
     * 民宿详情的基本数据
     */
    private HouseInfoBaseData mBaseData;
    private HouseInfoPresenter presenter;

    private final int CHOOSE_TIME_REQUEST_CODE = 12002;
    /**
     * 当前民宿Id
     */
    private int houseId;

    public static HouseInfoFragment newInstance(int houseId) {

        Bundle args = new Bundle();
        args.putInt("HouseId", houseId);
        HouseInfoFragment fragment = new HouseInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.houseId = getArguments().getInt("HouseId");
        }
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.houseinfo_frag_house_info, container, false);
        //控件实例化的方法
        instantiationOfView();
        //调用Presenter层的方法获取基本数据
        presenter = new HouseInfoPresenter(this);

        presenter.getHouseInfoBaseData(houseId);
        //顶部TabLayout的标签初始化
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
        //设置ScrollView和TabLayout的关联
        setScrollViewPositionChangedListener();
        setTabLayoutSelectedTabChangedListener();

        return mView;
    }


    @Override
    public void houseInfoBaseDataShowOnUi(@NonNull HouseInfoBaseData houseInfoBaseData) {
        //从Model层获取到了数据
        mBaseData = houseInfoBaseData;
        //收藏状态获取
        isCollected = false;
        goodView = new GoodView(getContext());
        if (mBaseData != null) {
            if (Boolean.TRUE.equals(LoginStatusData.getLoginStatus().getValue()) && mBaseData.isCollected()) {
                //如果已登录 且已收藏
                isCollected = true;
                collectImg.setImageResource(R.drawable.houseinfo_icon_collect_after);
            }
            //生成民宿图片二点轮播图（手动滑动的）
            initBannerView();
            //生成第一个视图View 概览
            initView1();
            //生成第二个视图View 设施-服务
            initView2();
            //生成第三个视图View 点评
            initView3();
            //生成第四个视图View 规则须知 房东
            initView4();
            //生成第五个视图View 同类推荐
            initView5();
            //设置底部的监听
            consultInBottomView.setOnClickListener(view -> {
                ToastUtils.toast("跳转至房东咨询页面");
            });
            priceBeforeInBottomView.setText(String.valueOf(mBaseData.getPriceBefore()));
            //中间横线（删除线）
            priceBeforeInBottomView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            // 抗锯齿
            priceBeforeInBottomView.getPaint().setAntiAlias(true);
            priceAfterInBottomView.setText(String.valueOf(mBaseData.getPriceAfter()));
            hasDiscountInBottomView.setText("已为您优惠减免" + (mBaseData.getPriceBefore() - mBaseData.getPriceAfter()) + "元");
            bookInBottomView.setOnClickListener(view -> {
                //跳转至订单组件
                ToastUtils.toast("跳转至订单信息确认及支付页面");
            });
        }

    }

    @Override
    public void collectFailed() {
        new MaterialDialog.Builder(requireContext())
                .positiveText("跳转")
                .negativeText("取消")
                .title("尚未登陆")
                .content("您当前尚未登陆，是否为您跳转去登录？")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        if (!BuildConfig.isModule) {
                            ARouter.getInstance().build("/loginregistrationView/LoginActivity").navigation();
                        }
                    }
                }).show();
    }

    @Override
    public void collectSucceed() {
        if (!isCollected) {
            //当前未收藏
            collectImg.setImageResource(R.drawable.houseinfo_icon_collect_after);
            goodView.setText("收藏成功")
                    .setTextColor(Color.parseColor("#4facfe"))
                    .setTextSize(12)
                    .setDuration(1000)
                    .setDistance(120)
                    .show(collectImg);
            isCollected = true;
        } else {
            //当前已收藏
            collectImg.setImageResource(R.drawable.houseinfo_icon_collect_before);
            isCollected = false;
        }
    }

    /**
     * TODO 将推荐列表显示
     */
    @Override
    public void recommendHouseListShow(List<RecommendHouse> list) {
        this.recommendHouseList = new ArrayList<>();
        if (list == null || list.size() == 0) {
            //无数据
            XToastUtils.error("推荐民宿数据获取异常！");
        } else {
            //有数据
            this.recommendHouseList = list;
        }
    }

    /**
     * TODO 生成第五个View并生成交互
     */
    private void initView5() {
        if (this.recommendHouseList != null) {
            recommendHousesAdapter = new RecommendHousesAdapter(recommendHouseList, this);
            recommendRecycler.setAdapter(recommendHousesAdapter);
            recommendRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
    }


    /**
     * TODO 生成第四个View并生成交互
     */
    private void initView4() {
        //设置头像和昵称
        Glide.with(requireContext()).load(mBaseData.getLandlordAvatar()).into(landlordAvatarInFourthView);
        landlordNicknameInFourthView.setText(mBaseData.getLandlordNickname());
        gotoLandlordPageInFourthView.setOnClickListener(view -> {
            //跳转至房东主页页面
            ToastUtils.toast("跳转至房东主页");
        });
        consultToLandlordInFourthView.setOnClickListener(view -> {
            //跳转至房东咨询页面（与房东的聊天页面）
            ToastUtils.toast("跳转至房东咨询页面");
        });
    }

    private TopCommentGetService topCommentGetService;

    /**
     * TODO 生成第三个View并生成交互
     */
    @SuppressLint("SetTextI18n")
    private void initView3() {
        //点击跳转至评论区详情页面
        gotoAllCommentsInThirdView.setOnClickListener(view -> {
            if (!BuildConfig.isModule) {
                ARouter.getInstance().build("/commentsview/CommentsActivity")
                        .withInt("HouseId", houseId).navigation();
            } else {
                XToastUtils.error("当前处于组件开发模式下，不可跳转！");
            }
        });
        commentLayoutInThirdView.setOnClickListener(view -> {
            gotoAllCommentsInThirdView.performClick();
        });
        titleScoreTextInThirdView.setText(String.valueOf(mBaseData.getRatingStars()));
        ratingBarInThirdView.setRating(mBaseData.getRatingStars());
        totalCommentsCountInThirdView.setText("（共" + mBaseData.getCommentsCount() + "条点评）");
        //数据变量
        final String[] commentContent = {requireActivity().getString(R.string.shudaonan)};
        final String[][] pictureRes = {new String[]{"https://img.zcool.cn/community/016cc85cfb2000a801205e4b7ef441.jpg@1280w_1l_2o_100sh.jpg",
                "https://img.zcool.cn/community/016cc85cfb2000a801205e4b7ef441.jpg@1280w_1l_2o_100sh.jpg",
                "https://img.zcool.cn/community/016cc85cfb2000a801205e4b7ef441.jpg@1280w_1l_2o_100sh.jpg",
                "https://img.zcool.cn/community/016cc85cfb2000a801205e4b7ef441.jpg@1280w_1l_2o_100sh.jpg",
                "https://img.zcool.cn/community/016cc85cfb2000a801205e4b7ef441.jpg@1280w_1l_2o_100sh.jpg"}};
        //替换不同的数据源 组件测试开发使用自定义数据，集成开发使用获取的数据
        //使用ARouter+接口服务来获取信息
        if (!BuildConfig.isModule) {
            //集成模式下可获取
            topCommentGetService = (TopCommentGetService)
                    ARouter.getInstance().build("/commentsview/CommentsRequest").navigation();
            MutableLiveData<TopCommentData> topCommentInfoLiveData
                    = topCommentGetService.getTopCommentInfo(this, houseId);
            topCommentInfoLiveData.observe(this, new Observer<TopCommentData>() {
                @Override
                public void onChanged(TopCommentData topCommentData) {
                    if (topCommentData != null && !topCommentData.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                        //评论内容
                        userContentInThirdView.setText(topCommentData.getContent());
                        //用户头像
                        Glide.with(requireContext()).load(topCommentData.getUserAvatar()).circleCrop().into(userAvatarInThirdView);
                        //用户昵称
                        userNicknameInThirdView.setText(topCommentData.getUserNickname());
                        //用户发表时间
                        userPostedTimeInThirdView.setText(topCommentData.getUserPostTime());
                        //用户评分
                        userRatingScoreInThirdView.setText(topCommentData.getUserRating());
                        //照片
                        pictureRes[0] = topCommentData.getPicturesUrls();
                        //点赞数 随机
                        likedCountInThirdView.setText(new Random().nextInt(600) + 100 + "");
                    } else {
                        XToastUtils.error("获取数据异常！有可能暂无评论。");
                    }
                }
            });
        }
        //测试评论的内容长度不同的UI
        userContentInThirdView.setText(commentContent[0]);
        ViewTreeObserver vto = userContentInThirdView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //布局完成之后调用
                Layout layout = userContentInThirdView.getLayout();
                if (layout != null && layout.getEllipsisCount(layout.getLineCount() - 1) > 0) {
                    //被省略了
                    gotoExpandInThirdView.setVisibility(View.VISIBLE);
                }
                //移除监听器，避免重复调用
                userContentInThirdView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        //没有照片 什么都不做，直接跳过
        if (pictureRes[0] != null && pictureRes[0].length > 0) {
            for (int i = 0; i < pictureRes[0].length; i++) {
                if (i >= 3) {
                    break;
                }
                pictureArrayInThirdView[i].setVisibility(View.VISIBLE);
                Glide.with(requireContext()).load(pictureRes[0][i]).into(pictureArrayInThirdView[i]);
            }
        }
        if (pictureRes[0] != null && pictureRes[0].length > 3) {
            pictureOverlayInThirdView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * TODO 生成初始化第一个View
     */
    @SuppressLint("SetTextI18n")
    private void initView1() {
        //View1的信息设置
        //Title
        houseTitleInFirstView.setText(mBaseData.getTitle());
        houseTitleInFirstView.setOnClickListener(view -> {
            //显示全部标题
            Layout layout = houseTitleInFirstView.getLayout();
            //getEllipsisCount(int) 获取指定行（- 1）的省略号使用的次数
            if (layout != null && layout.getEllipsisCount(1) > 0) {
                ViewTooltip.on(houseTitleInFirstView)
                        .color(Color.parseColor("#3E4055"))
                        .position(ViewTooltip.Position.TOP)
                        .text(houseTitleInFirstView.getText().toString())
                        .clickToHide(true)
                        .autoHide(true, 2500)
                        .animation(new ViewTooltip.FadeTooltipAnimation(600))
                        .show();
            }
        });

        //默认今晚入住明晚离开，共1晚，用户可以自己手动更改日期（监听，动态改变）
        Date date = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        startLiveTimeInFirstView.setText(sdf.format(date));
        endLiveTimeInFirstView.setText(sdf.format(tomorrow));
        totalDaysInFirstView.setText("共1晚");

        //FLowTagLayout的初始化（数据设置）
        String[] titleTags = mBaseData.getTitleTags();
        if (titleTags != null && titleTags.length > 0) {
            initFlowTagsLayout(flowTagLayoutInFirstView, true, titleTags);
        }
        //评分和点评数量
        scoreTextInFirstView.setText(String.valueOf(mBaseData.getRatingStars()));
        int commentsCount = mBaseData.getCommentsCount();
        if (commentsCount > 0) {
            totalCommentsInFirstView.setText("共" + commentsCount + "条点评");
        } else {
            totalCommentsInFirstView.setText("暂无点评");
        }
        //View1的点击监听
        couponCardViewInFirstView.setOnClickListener(view -> {
            ToastUtils.toast("优惠券页面弹出");
            //如果没有优惠券
            new MaterialDialog.Builder(requireContext())
                    .title("选择优惠券")
                    .customView(R.layout.houseinfo_hasno_coupon_view, false)
                    .neutralText("取消")
                    .buttonsGravity(GravityEnum.END)
                    .neutralColor(Color.parseColor("#636363"))
                    .show();

        });


        locateCardViewInFirstView.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), MapActivity.class);
            intent.putExtra("HouseLatitude", mBaseData.getLatitude());
            intent.putExtra("HouseLongitude", mBaseData.getLongitude());
            startActivity(intent);
        });
        timeCardViewInFirstView.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ChooseTimeCalendarActivity.class);
            startActivityForResult(intent, CHOOSE_TIME_REQUEST_CODE);
        });
        scoreCardViewInFirstView.setOnClickListener(view -> {
            if (!BuildConfig.isModule) {
                //集成模式下
                ARouter.getInstance().build("/commentsview/CommentsActivity")
                        .withInt("HouseId", houseId);
            }
        });

    }

    /**
     * TODO 生成第二个View并设置各种监听
     */
    @SuppressLint("SetTextI18n")
    private void initView2() {
//        String[] baseFacilitiesTags = {"付费停车位", "电梯", "无线网络", "换客换床品", "拖鞋", "窗户", "空调", "有线网络", "饮水设备", "淋浴房", "智能门锁"};
        String[] baseFacilities = mBaseData.getBaseFacilities();
        initFlowTagsLayout(baseFacilitiesFlowTagLayoutInSecondView, false, baseFacilities);
        totalBaseFacilitiesCountInSecondView.setText("共" + baseFacilities.length + "项");

        initFlowTagsLayout(bathFlowTagLayoutInSecondView, false, new String[]{"热水淋浴", "牙具", "毛巾", "电吹风", "浴液洗发水", "浴巾", "手纸", "24小时热水", "洗手液"});
        initFlowTagsLayout(serviceFlowTagLayoutInSecondView, false, new String[]{"门禁系统", "自助入住", "免费瓶装水"});
        initFlowTagsLayout(livingFlowTagLayoutInSecondView, false, new String[]{"洗衣机", "暖气", "沙发", "茶几", "办公空间", "厨房", "厨具", "洗衣粉/液"});
        initFlowTagsLayout(safetyFlowTagLayoutInSecondView, false, new String[]{"灭火器", "公共监控", "急救包", "保安"});
        initFlowTagsLayout(surroundingFlowTagLayoutInSecondView, false, new String[]{"菜市场", "公园", "超市", "地铁", "提款机", "餐厅", "地标景", "药店"});
        areaTagTextInSecondView.setText(mBaseData.getArea());
        roomConfigTextInSecondView.setText(mBaseData.getRoom());
        expandImageInSecondView.setOnClickListener(view -> {
            if (expandableForAllFacilitiesInSecondView != null) {
                facilitiesIsExpandedInSecondView = !facilitiesIsExpandedInSecondView;
                expandableForAllFacilitiesInSecondView.setExpanded(facilitiesIsExpandedInSecondView, true);
            }
        });
        expandableForAllFacilitiesInSecondView.setInterpolator(new AccelerateInterpolator());
        expandableForAllFacilitiesInSecondView.setOnExpansionChangedListener((expansion, state) -> {
            if (expandImageInSecondView != null) {
                expandImageInSecondView.setRotation(expansion * 180);
            }
        });

        //视频播放器
//        String cleanVideoUrl = "https://stream7.iqilu.com/10339/upload_transcode/202002/18/20200218114723HDu3hhxqIT.mp4";
        String cleanVideoUrl = mBaseData.getCleanVideoUrl();
        playVideo(cleanVideoUrl);
    }


    /**
     * TODO 控件实例化的方法
     */
    private void instantiationOfView() {
        bannerVp = mView.findViewById(R.id.houseInfo_banner_viewPager);
        bannerPb = mView.findViewById(R.id.houseInfo_banner_progressBar);
        navigationTabLayout = mView.findViewById(R.id.houseInfo_tabLayout);
        scrollView = mView.findViewById(R.id.houseInfo_scrollView);
        flowTagLayoutInFirstView = mView.findViewById(R.id.houseinfo_tab_flowlayout);
        view1 = mView.findViewById(R.id.t_1);
        view2 = mView.findViewById(R.id.t_2);
        view3 = mView.findViewById(R.id.t_3);
        view4 = mView.findViewById(R.id.t_4);
        view5 = mView.findViewById(R.id.t_5);

        topLayout = mView.findViewById(R.id.houseInfo_top_view);
        backImg = mView.findViewById(R.id.houseInfo_back_icon);
        backImg.setOnClickListener(this);
        shareImg = mView.findViewById(R.id.houseInfo_share_icon);
        shareImg.setOnClickListener(this);
        collectImg = mView.findViewById(R.id.houseInfo_collect_icon);
        collectImg.setOnClickListener(this);
        commentImg = mView.findViewById(R.id.houseInfo_comment_icon);
        commentImg.setOnClickListener(this);

        houseTitleInFirstView = mView.findViewById(R.id.houseinfo_title);
        detailedLocationInFirstView = mView.findViewById(R.id.houseinfo_location_text);
        startLiveTimeInFirstView = mView.findViewById(R.id.houseinfo_time_start_text);
        endLiveTimeInFirstView = mView.findViewById(R.id.houseinfo_time_end_text);
        totalDaysInFirstView = mView.findViewById(R.id.text4_in_card);
        scoreTextInFirstView = mView.findViewById(R.id.houseinfo_score_text);
        totalCommentsInFirstView = mView.findViewById(R.id.houseinfo_goto_comments);

        couponCardViewInFirstView = mView.findViewById(R.id.houseinfo_coupon);
        locateCardViewInFirstView = mView.findViewById(R.id.houseinfo_locate_card);
        timeCardViewInFirstView = mView.findViewById(R.id.houseinfo_time_card);
        scoreCardViewInFirstView = mView.findViewById(R.id.houseinfo_comment_card);

        baseFacilitiesFlowTagLayoutInSecondView = mView.findViewById(R.id.second_base_flowtaglayout);
        totalBaseFacilitiesCountInSecondView = mView.findViewById(R.id.second_total_base);
        areaTagTextInSecondView = mView.findViewById(R.id.second_area_tag);
        roomConfigTextInSecondView = mView.findViewById(R.id.second_room_tag);
        bathFlowTagLayoutInSecondView = mView.findViewById(R.id.second_bath_flowtaglayout);
        serviceFlowTagLayoutInSecondView = mView.findViewById(R.id.second_service_flowtaglayout);
        livingFlowTagLayoutInSecondView = mView.findViewById(R.id.second_living_flowtaglayout);
        safetyFlowTagLayoutInSecondView = mView.findViewById(R.id.second_safety_flowtaglayout);
        surroundingFlowTagLayoutInSecondView = mView.findViewById(R.id.second_surrounding_flowtaglayout);
        expandImageInSecondView = mView.findViewById(R.id.second_expand_image);
        expandableForAllFacilitiesInSecondView = mView.findViewById(R.id.second_expand_all_facilities);
        facilitiesIsExpandedInSecondView = false;
        playerViewInSecondView = mView.findViewById(R.id.second_video_view);

        gotoAllCommentsInThirdView = mView.findViewById(R.id.third_into_all_comment);
        commentLayoutInThirdView = mView.findViewById(R.id.third_comment_layout);
        titleScoreTextInThirdView = mView.findViewById(R.id.third_score);
        ratingBarInThirdView = mView.findViewById(R.id.third_rating_star);
        totalCommentsCountInThirdView = mView.findViewById(R.id.third_comment_count);
        userAvatarInThirdView = mView.findViewById(R.id.third_avatar);
        userNicknameInThirdView = mView.findViewById(R.id.third_username);
        userPostedTimeInThirdView = mView.findViewById(R.id.third_time);
        userRatingScoreInThirdView = mView.findViewById(R.id.third_user_score);
        userContentInThirdView = mView.findViewById(R.id.third_content);
        gotoExpandInThirdView = mView.findViewById(R.id.third_to_expand);
        gotoExpandInThirdView.setVisibility(View.GONE);
        likedCountInThirdView = mView.findViewById(R.id.third_liked_count);
        picture1InThirdView = mView.findViewById(R.id.third_picture_1);
        picture1InThirdView.setVisibility(View.GONE);
        picture2InThirdView = mView.findViewById(R.id.third_picture_2);
        picture2InThirdView.setVisibility(View.GONE);
        picture3InThirdView = mView.findViewById(R.id.third_picture_3);
        picture3InThirdView.setVisibility(View.GONE);
        pictureArrayInThirdView = new ImageView[]{picture1InThirdView, picture2InThirdView, picture3InThirdView};
        pictureOverlayInThirdView = mView.findViewById(R.id.third_more_picture_greyview);
        pictureOverlayInThirdView.setVisibility(View.GONE);

        landlordAvatarInFourthView = mView.findViewById(R.id.fourth_landlord_avatar);
        landlordNicknameInFourthView = mView.findViewById(R.id.fourth_landlord_nickname);
        consultToLandlordInFourthView = mView.findViewById(R.id.fourth_landlord_consult);
        gotoLandlordPageInFourthView = mView.findViewById(R.id.fourth_landlord_personal_page);

        recommendRecycler = mView.findViewById(R.id.fifth_recommend_recycler);

        consultInBottomView = mView.findViewById(R.id.bottom_text_1);
        priceAfterInBottomView = mView.findViewById(R.id.bottom_price_after);
        priceBeforeInBottomView = mView.findViewById(R.id.bottom_price_before);
        hasDiscountInBottomView = mView.findViewById(R.id.bottom_price_discount);
        bookInBottomView = mView.findViewById(R.id.bottom_time_or_book);

        bannerLayout = mView.findViewById(R.id.houseInfo_bannerLayout);
    }

    private void playVideo(String url) {
        if (playerViewInSecondView != null) {
            optionBuilderInSecondView = new GSYVideoOptionBuilder();
            optionBuilderInSecondView.setIsTouchWiget(true)
                    .setUrl(url).setShowFullAnimation(true).build(playerViewInSecondView);
            Glide.with(requireContext()).load(mBaseData.getBannerUrls()[0]).into(playerViewInSecondView.getCoverView());
            playerViewInSecondView.getBackButton().setVisibility(View.GONE);
            playerViewInSecondView.getFullscreenButton().setOnClickListener(view -> {
                playerViewInSecondView.startWindowFullscreen(playerViewInSecondView.getContext(), true, true);
            });
        }
    }

    /**
     * 初始化标签流布局 字符串数组标签
     */
    private void initFlowTagsLayout(FlowTagLayout flowTagLayout, boolean useColorTags, String[] tags) {
        if (flowTagLayout != null) {
            FlowLayoutAdapter adapter = new FlowLayoutAdapter(getContext(), useColorTags);
            flowTagLayout.setAdapter(adapter);
            flowTagLayout.addTags(tags);
        }
    }

    /**
     * 初始化标签流布局 集合标签
     */
    private void initFlowTagsLayout(FlowTagLayout flowTagLayout, boolean useColorTags, List<String> tags) {
        if (flowTagLayout != null) {
            FlowLayoutAdapter adapter = new FlowLayoutAdapter(getContext(), useColorTags);
            flowTagLayout.setAdapter(adapter);
            flowTagLayout.addTags(tags);
        }
    }

    /**
     * TODO 分享到微信的tag
     */
    private final String SHARE_TO_WEIXIN = "share_to_weixin";
    /**
     * TODO 分享到微博的tag
     */
    private final String SHARE_TO_WEIBO = "share_to_weibo";
    /**
     * TODO 分享到空间的tag
     */
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
                //未被收藏
                if (presenter != null) {
                    presenter.addToCollectionList(mBaseData.getId());
                } else {
                    XToastUtils.error("presenter == null");
                }
            } else {
                //已被收藏
                if (presenter != null) {
                    presenter.removeFromCollectionList(mBaseData.getId());
                } else {
                    XToastUtils.error("presenter == null");
                }
            }
        } else if (view.getId() == R.id.houseInfo_comment_icon) {
            //写评论按钮点击监听
            if (!BuildConfig.isModule) {
                ARouter.getInstance().build("/commentsview/CommentPostActivity")
                        .withInt("HouseId", houseId).navigation();
            } else {
                XToastUtils.error("当前处于组件开发模式下，不能跳转！");
            }
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
                    tab.setCustomView(R.layout.houseinfo_tab_text_layout);
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
                        scrollView.smoothScrollTo(0, view2.getTop() - tabLayoutHeight);
                    } else if (tab.getPosition() == 2) {
                        isUsing = true;
                        scrollView.smoothScrollTo(0, view3.getTop() - tabLayoutHeight);
                    } else if (tab.getPosition() == 3) {
                        isUsing = true;
                        scrollView.smoothScrollTo(0, view4.getTop() - tabLayoutHeight);
                    } else if (tab.getPosition() == 4) {
                        isUsing = true;
                        scrollView.smoothScrollTo(0, view5.getTop() - tabLayoutHeight);
                    }
                    isUsing = false;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView == null) {
                    tab.setCustomView(R.layout.houseinfo_tab_text_layout);
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
        //滑动未开始 尚未滑动
        topViewColorChange(true);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (!isUsing) {
                    int tabLayoutHeight = navigationTabLayout.getBottom();
                    int mScrollY = scrollY + tabLayoutHeight;
                    if (mScrollY <= bannerVp.getBottom()) {
                        topViewColorChange(true);
                        navigationTabLayout.setVisibility(View.GONE);
                    } else if (mScrollY >= view1.getTop() && mScrollY < view2.getTop()) {
                        isUsing = true;
                        topViewColorChange(false);
                        navigationTabLayout.setVisibility(View.VISIBLE);
                        navigationTabLayout.selectTab(top);
                        navigationTabLayout.setScrollPosition(0, 0f, true);
                    } else if (mScrollY >= view2.getTop() && mScrollY < view3.getTop()) {
                        isUsing = true;
                        topViewColorChange(false);
                        navigationTabLayout.setVisibility(View.VISIBLE);
                        navigationTabLayout.selectTab(facility);
                        navigationTabLayout.setScrollPosition(1, 0f, true);
                    } else if (mScrollY >= view3.getTop() && mScrollY < view4.getTop()) {
                        isUsing = true;
                        topViewColorChange(false);
                        navigationTabLayout.setVisibility(View.VISIBLE);
                        navigationTabLayout.selectTab(review);
                        navigationTabLayout.setScrollPosition(2, 0f, true);
                    } else if (mScrollY >= view4.getTop() && mScrollY < view5.getTop()) {
                        isUsing = true;
                        topViewColorChange(false);
                        navigationTabLayout.setVisibility(View.VISIBLE);
                        navigationTabLayout.selectTab(notice);
                        navigationTabLayout.setScrollPosition(3, 0f, true);
                    } else if (mScrollY >= view5.getTop()) {
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
     * @param iconColorIsWhite 图标颜色是否为白色
     *                                                                                                                                                                                                                                                                                                 TODO 根据ScrollView动态改变顶部图标的颜色和背景的颜色
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

    /**
     * TODO 生成民宿图片轮播图（手动滑动）
     */
    private void initBannerView() {
        String[] bannerUrls = mBaseData.getBannerUrls();
        if (bannerUrls != null && bannerUrls.length > 0) {
            BannerAdapter bannerAdapter = new BannerAdapter(bannerUrls);
            bannerVp.setAdapter(bannerAdapter);
            bannerPb.setMax(bannerUrls.length);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_TIME_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String[] timeResult = data.getStringArrayExtra("data_return");
                startLiveTimeInFirstView.setText(timeResult[0]);
                endLiveTimeInFirstView.setText(timeResult[1]);
                totalDaysInFirstView.setText(timeResult[2]);
            }
        }
    }
}
