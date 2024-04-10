package com.xupt3g.personalmanagementview.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.utils.XToastUtils;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xuexiang.xui.widget.button.RippleView;
import com.xupt3g.personalmanagementview.R;
import com.xupt3g.personalmanagementview.model.retrofit.AccountInfoResponse;
import com.xupt3g.personalmanagementview.presenter.AccountInfoPresenter;

import org.greenrobot.eventbus.EventBus;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.personalmanagementview.View.PersonalManagementFragment
 *
 * @author: shallew
 * @data: 2024/2/3 12:13
 * @about: TODO 个人管理页面Fragment
 */
@Route(path = "/personalManagementView/PersonalManagementFragment")
public class PersonalManagementFragment extends Fragment implements AccountInfoShowImpl {

    private View mView;
    private ImageView settingButton;
    private TextView pay;
    private ImageView locationButton;
    private ImageView calendarButton;
    /**
     * 用户头像
     */
    private CircleImageView userAvatar;
    /**
     * 用户昵称
     */
    private TextView userNickname;
    /**
     * 用户自我介绍
     */
    private TextView userIntroduce;
    /**
     * presenter层实例
     */
    public static AccountInfoPresenter presenter;

    /**
     * 收藏数量
     */
    private TextView countOfCollections;
    /**
     * 收藏的可点击View
     */
    private LinearLayout collections;
    /**
     * 浏览数量
     */
    private TextView countOfBrowses;
    /**
     * 浏览的可点击View
     */
    private LinearLayout browses;
    /**
     * 浏览的可点击View
     */
    private TextView countOfOrders;
    /**
     * 订单的可点击View
     */
    private LinearLayout orders;
    /**
     * 点评数量
     */
    private TextView countOfReviews;
    /**
     * 点评的可点击View
     */
    private LinearLayout reviews;
    /**
     * 未登陆时的模糊背景
     */
    private FrameLayout blurBackground;
    /**
     * 未登录时的登录按钮
     */
    private RippleView loginRippleButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.personal_frag_personal_managment, container, false);

        //碎片切换动画
        PersonalManagementActivity.setFragmentChangeTransition(getContext(), this);

        presenter = new AccountInfoPresenter(this);

        settingButton = (ImageView) mView.findViewById(R.id.personal_setting);
        locationButton = (ImageView) mView.findViewById(R.id.personal_location);
        calendarButton = (ImageView) mView.findViewById(R.id.personal_calendar);
        blurBackground = (FrameLayout) mView.findViewById(R.id.blur_background);
        loginRippleButton = (RippleView) mView.findViewById(R.id.personal_ripple_login);
        userAvatar = (CircleImageView) mView.findViewById(R.id.personal_avatar);
        userNickname = (TextView) mView.findViewById(R.id.personal_username);
        userIntroduce = (TextView) mView.findViewById(R.id.personal_introduce);
        countOfCollections = (TextView) mView.findViewById(R.id.personal_collections_count);
        countOfBrowses = (TextView) mView.findViewById(R.id.personal_browsed_count);
        countOfOrders = (TextView) mView.findViewById(R.id.personal_orders_count);
        countOfReviews = (TextView) mView.findViewById(R.id.personal_comments_count);
        collections = (LinearLayout) mView.findViewById(R.id.personal_collections_text);
        browses = (LinearLayout) mView.findViewById(R.id.personal_browsed_text);
        orders = (LinearLayout) mView.findViewById(R.id.personal_orders_text);
        reviews = (LinearLayout) mView.findViewById(R.id.personal_comments_text);

        //注册EventBus的接收者
//        registerEventBus();

        //观察者监视登陆状态的改变 将改页面UI改成对应的登录状态
        LoginStatusData.getLoginStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //当登录状态改变时
                if (Boolean.TRUE.equals(LoginStatusData.getLoginStatus().getValue())) {
                    //如果登录状态为true 已登录
                    loggedInUi();//已登录界面展示
                    //Presenter
                    presenter.accountInfoGet();

                } else if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
                    //如果登录状态为false 未登录
                    notLoggedInUi();//未登录界面展示
                }
            }
        });

        //设置图标点击监听
        settingButton.setOnClickListener(view -> {
            if (BuildConfig.isModule) {
                //组件模式下
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.personal_fragment_container, new SettingFragment())
                        .addToBackStack(null).commit();
            } else {
                //集成模式下
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        //observe观察者当数据第一次使用时（没有修改）也会被调用
        // 所以不需要初次登陆显示UI，再看数据改变更改UI
        //只需要保留observe观察
        Log.d("createOne", "onCreateView: 创建了一个PMF");

        return mView;
    }

    private void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null && !BuildConfig.isModule) {
            MutableLiveData<Integer> historyCount = presenter.getBrowsedHistoryManagerService().getBrowsedHistoryCount(this);
            historyCount.observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    countOfBrowses.setText(String.valueOf(integer));
                }
            });
            MutableLiveData<Integer> collectionsCount = presenter.getCollectionManagerService().getCollectionsCount(this);
            collectionsCount.observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    countOfCollections.setText(String.valueOf(integer));
                }
            });

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * TODO 账户已登录的界面设置
     */
    public void loggedInUi() {
        //未登录 模糊背景消失
        blurBackground.setVisibility(View.INVISIBLE);
        //定位按钮 显示
        locationButton.setVisibility(View.VISIBLE);
        //日程日历按钮 显示
        calendarButton.setVisibility(View.VISIBLE);

        //设置各种UI监听
        //除开设置图标，"设置"图标在登录和未登录状态下点击都会跳转到SettingFragment，逻辑一样
        collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转至收藏页面（组件）
                if (!BuildConfig.isModule) {
                    //集成开发模式
                    ARouter.getInstance().build("/collectionsView/CollectionsActivity")
                            .navigation();
                }
            }
        });
        browses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转至浏览历史页面
                if (!BuildConfig.isModule) {
                    //集成开发模式
                    ARouter.getInstance().build("/browsingHistoryView/BrowsingHistoryActivity")
                            .navigation();

                }
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转至订单功能页面
                if (!BuildConfig.isModule) {
                    //集成开发模式
                    ARouter.getInstance().build("/ordersView/OrdersActivity")
                            .navigation();

                }
            }
        });


    }

    /**
     * TODO 账户未登录的界面设置
     */
    public void notLoggedInUi() {
        //如果未登录
        //未登录 模糊背景显示
        blurBackground.setVisibility(View.VISIBLE);
        // 定位按钮 消失
        locationButton.setVisibility(View.INVISIBLE);
        // 日程日历按钮 消失
        calendarButton.setVisibility(View.INVISIBLE);
        //也不需要这两个功能 只需要登录按钮和设置按钮

        //登录按钮的监听 （跳转至登录模块）
        loginRippleButton.setOnClickListener(view -> {
            if (!BuildConfig.isModule) {
                //只有集成模式下可以尝试跳转
                ARouter.getInstance().build("/loginregistrationView/LoginActivity")
                        .navigation();
            } else {
                XToastUtils.error("当前不能跳转！");
            }
        });

    }

    /**
     * TODO 将账户信息显示在UI上
     */
    @Override
    public void showOnUi() {
        MutableLiveData<AccountInfoResponse> liveData = presenter.getResponseLiveData();
        liveData.observe(getViewLifecycleOwner(), new Observer<AccountInfoResponse>() {
            @Override
            public void onChanged(AccountInfoResponse response) {
                if (response != null && response.getCode() == 200 && "OK".equals(response.getMsg())) {
                    //显示用户信息
                    //头像及预览监听设置
                    Intent intent = new Intent(getContext(), UserAvatarPreviewActivity.class);
                    if (response.getUserInfo().getAvatar() == null || "".equals(response.getUserInfo().getAvatar())) {
                        //头像为空 默认头像
                        Glide.with(requireContext()).load(R.drawable.personal_default_avatar_2)
                                .into(userAvatar);
                        intent.putExtra("UserAvatar", UserAvatarPreviewActivity.defAvatar);
                    } else {
                        Glide.with(requireContext()).load(response.getUserInfo().getAvatar())
                                .into(userAvatar);
                        intent.putExtra("UserAvatar", response.getUserInfo().getAvatar());
                    }
                    userAvatar.setOnClickListener(view -> {
                        startActivity(intent);
                    });
                    //昵称设置
                    userNickname.setText(response.getUserInfo().getNickname());
                    //自我介绍设置
                    userIntroduce.setText(response.getUserInfo().getInfo());
                    if (!BuildConfig.isModule) {
                        //集成模式下可使用ARouter调用以下API
                        //显示收藏数量
                        MutableLiveData<Integer> collectionsCount = presenter.getCollectionManagerService().getCollectionsCount(getViewLifecycleOwner());
                        collectionsCount.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {
                                countOfCollections.setText(String.valueOf(integer));
                            }
                        });
                        //显示浏览历史数量
                        MutableLiveData<Integer> historyCount = presenter.getBrowsedHistoryManagerService().getBrowsedHistoryCount(getViewLifecycleOwner());
                        historyCount.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {
                                countOfBrowses.setText(String.valueOf(integer));
                            }
                        });
                    }
                }
            }
        });
    }
}
