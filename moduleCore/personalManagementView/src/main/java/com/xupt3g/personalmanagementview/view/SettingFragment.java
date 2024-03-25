package com.xupt3g.personalmanagementview.view;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.libbase.BuildConfig;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.button.RippleView;
import com.xuexiang.xui.widget.grouplist.XUICommonListItemView;
import com.xuexiang.xui.widget.grouplist.XUIGroupListView;
import com.xupt3g.mylibrary1.NotificationManagement;
import com.xupt3g.personalmanagementview.R;
import com.xupt3g.personalmanagementview.model.retrofit.AccountInfoResponse;

import java.util.List;
import java.util.Objects;


/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.personalmanagementview.View.SettingFragment
 *
 * @author: shallew
 * @data: 2024/2/4 19:28
 * @about: TODO 设置页面
 */
public class SettingFragment extends Fragment {
    private View mView;
    private XUIGroupListView mGroupListView;
    private ImageView backButton;
    private RippleView logoutButton;
    private AccountInfoResponse response;
    private XUICommonListItemView item1;
    private String userAvatar;
    private String userNickname;
    private String userMobile;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.personal_frag_personal_setting, container, false);

        mGroupListView = mView.findViewById(R.id.setting_groupListView);
        logoutButton = (RippleView) mView.findViewById(R.id.setting_ripple_logout);
        backButton = mView.findViewById(R.id.setting_back);

        //返回键
        backButton.setOnClickListener(view -> {
            requireActivity().onBackPressed();
        });

        //退出登录按钮
        logoutButton.setOnClickListener(view -> {
            LoginStatusData.getLoginStatus().setValue(false);
        });

        //设置Fragment进场效果
        PersonalManagementActivity.setFragmentChangeTransition(getContext(), this);

        //观察登录状态改变
        LoginStatusData.getLoginStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mGroupListView.removeAllViews();
                //先布局
                initGroupListView();
                if (Boolean.TRUE.equals(LoginStatusData.getLoginStatus().getValue())) {
                    //已登录
                    //观察用户信息
                    PersonalManagementFragment.presenter.getResponseLiveData().observe(getViewLifecycleOwner(), new Observer<AccountInfoResponse>() {
                        @Override
                        public void onChanged(AccountInfoResponse response) {
                            if (response != null && response.getCode() == 200 && "OK".equals(response.getMsg())) {
                                userAvatar = response.getUserInfo().getAvatar();
                                userMobile = response.getUserInfo().getMobile();
                                userNickname = response.getUserInfo().getNickname();
                                changeUserInfo();
                            }
                        }
                    });
                }
            }
        });

        return mView;
    }

    private void changeUserInfo() {
        Glide.with(requireContext()).load(userAvatar).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                item1.setImageDrawable(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
        item1.setText(userNickname);
        item1.setDetailText(userMobile);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initGroupListView() {
        View.OnClickListener onClickListener = v -> {
            if (v instanceof XUICommonListItemView) {
                CharSequence text = ((XUICommonListItemView) v).getText();
                XToastUtils.toast(text + " is Clicked");
            }
        };

        XUICommonListItemView item4 = mGroupListView.createItemView(
                null,
                "关于我们",
                null,
                XUICommonListItemView.HORIZONTAL,
                XUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        if (Boolean.TRUE.equals(LoginStatusData.getLoginStatus().getValue())) {
            item1 = mGroupListView.createItemView(
                    requireActivity().getDrawable(R.drawable.personal_default_avatar_2),
                    userNickname, userMobile,
                    XUICommonListItemView.HORIZONTAL,
                    XUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

            Glide.with(requireContext()).load(userAvatar)
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            item1.setImageDrawable(resource);
                        }
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });

            XUIGroupListView.newSection(mView.getContext())
                    .addItemView(item1, new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                        @Override
                        public void onClick(View view) {
                            //跳转至账号信息管理页面
                            if (!BuildConfig.isModule) {
                                //集成开发模式
                                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                transaction.add(R.id.setting_fragment_container, new AccountInfoFragment())
                                        .addToBackStack(null).commit();
                            } else {
                                //组件开发模式
                                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                transaction.add(R.id.personal_fragment_container, new AccountInfoFragment())
                                        .addToBackStack(null).commit();
                            }

                        }
                    })
                    //加入第一节
                    .addTo(mGroupListView);

            XUICommonListItemView item3 = mGroupListView.createItemView(
                    null,
                    "允许通知",
                    null,
                    XUICommonListItemView.HORIZONTAL,
                    XUICommonListItemView.ACCESSORY_TYPE_SWITCH);
            item3.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        Log.d("Switch", "onCheckedChanged: 允许");
                        //允许通知
                        notificationAllowed();
                    } else {
                        Log.d("Switch", "onCheckedChanged: 不允许");
                        notificationNotAllowed();
                    }
                }
            });

            //2\3\4加入第二节
            int size = DensityUtils.dp2px(mView.getContext(), 20);
            XUIGroupListView.newSection(getContext())
                    .setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .addItemView(item3, null)
                    .addItemView(item4, onClickListener)
                    .addTo(mGroupListView);
            //已登录状态下展示退出登录按钮
            logoutButton.setVisibility(View.VISIBLE);

        } else if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            //未登录
            //未登录 隐藏退出登录按钮
            logoutButton.setVisibility(View.INVISIBLE);

            int size = DensityUtils.dp2px(mView.getContext(), 20);
            XUIGroupListView.newSection(getContext())
                    .setLeftIconSize(size, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .addItemView(item4, onClickListener)
                    .addTo(mGroupListView);
        }
    }

    /**
     * 允许通知
     */
    public void notificationAllowed() {
        final boolean[] success = {false};
        if (!XXPermissions.isGranted(requireContext(), Permission.POST_NOTIFICATIONS)) {
            //检测是否有发送通知权限
            //没有
            XXPermissions.with(requireContext())
                    .permission(Permission.POST_NOTIFICATIONS)
                    .request(new OnPermissionCallback() {
                        @Override
                        public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                            if (allGranted) {
                                ToastUtils.toast("已成功授权！");
                                success[0] = true;
                            } else {
                                ToastUtils.toast("授权失败");
                            }
                        }

                        @Override
                        public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                            if (doNotAskAgain) {
                                ToastUtils.toast("被永久拒绝授权，请手动授予通知权限！");
                                if (XXPermissions.isGranted(requireContext(), permissions)) {
                                    success[0] = true;
                                }
                            } else {
                                ToastUtils.toast("通知权限获取失败");
                            }
                        }
                    });
            if (!success[0]) {
                return;
            }
        }

        Log.d("Switch", "onCheckedChanged: 允许通知");
        NotificationManagement.isAllowNotification = true;
        NotificationManager manager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //2.判断系统版本是否大于等于android8.0
            //3.获取NotificationChannel的实例，构造函数第一个参数表示通知渠道的id；第二个参数表示通知渠道的名称，第三个参数则表示重要性
            NotificationChannel channel = new NotificationChannel("allow_notification", "channel_0", NotificationManager.IMPORTANCE_DEFAULT /*重要性*/);
            //4.构造通知渠道。使用NotificationManager实例的createNotificationChannel()方法
            manager.createNotificationChannel(channel);
        }
        Notification notification = new NotificationCompat.Builder(requireContext(), "allow_notification")
                .setContentTitle("允许通知") //通知标题
                .setContentText("您开启了通知允许，可以接收到推送的通知") //通知内容
                .setTicker("666")
                .setSmallIcon(R.drawable.personal_baseline_done_outline_24) //设置小图标：必须提供
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.personal_baseline_done_outline_24))
                .setAutoCancel(true)//点击时自动取消通知
                .build();
        manager.notify(1, notification);
    }

    /**
     * 不允许通知
     */
    public void notificationNotAllowed() {
        Log.d("Switch", "onCheckedChanged: 不允许通知");
        NotificationManagement.isAllowNotification = false;
        NotificationManager manager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }


}
