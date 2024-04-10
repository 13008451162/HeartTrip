package com.xupt3g.hearttrip;

import static com.xuexiang.xutil.tip.ToastUtils.toast;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.LocationUtils.LocationListener;
import com.xupt3g.LocationUtils.LocationService;
import com.xupt3g.UiTools.UiTool;
import com.xupt3g.collectionsview.view.CollectionsFragment;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xupt3g.mylibrary1.implservice.BrowsedHistoryManagerService;
import com.xupt3g.mylibrary1.implservice.CollectionManagerService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Route(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {
    private CollectionManagerService collectionManagerService;
    private BrowsedHistoryManagerService browsedHistoryManagerService;
    private ViewPager2 contentViewPager;
    private List<Fragment> contentFragments;
    private PagerAdapter pagerAdapter;
    private BottomNavigationView bottomNavigationView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //恢复上次的登录状态和登陆密钥
        recoverLoginStatus();

        LocationListener locationListener = LocationListener.getInstance();

        try {
            LocationService locationService = LocationService.getInstance();
            locationService.init(getApplicationContext());
            LocationService.start(locationListener);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        UiTool.setImmersionBar(this, true);
        bottomNavigationView = findViewById(R.id.app_bottom_navigation);
        contentViewPager = findViewById(R.id.app_viewpager);
        contentFragments = new ArrayList<>();
        CollectionsFragment collectionFragment = (CollectionsFragment) ARouter.getInstance().build("/collectionsView/CollectionsFragment").navigation();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHideBackBtn", true);
        collectionFragment.setArguments(bundle);

        Fragment homepageFragment = (Fragment) ARouter.getInstance().build("/homepageView/HomepageFragment").navigation();

        Fragment messageFragment = (Fragment) ARouter.getInstance().build("/messagesView/MessageFragment").navigation();

        Fragment personalFragment = (Fragment) ARouter.getInstance().build("/personalManagementView/PersonalManagementFragment").navigation();
        Collections.addAll(contentFragments, homepageFragment, collectionFragment, messageFragment, personalFragment);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle(), contentFragments);
        contentViewPager.setAdapter(pagerAdapter);
        contentViewPager.setOffscreenPageLimit(3);
        contentViewPager.setUserInputEnabled(false);


        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.ACCESS_FINE_LOCATION)
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean allGranted) {

                    }

                    @Override
                    public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                        if (doNotAskAgain) {
                            toast("被永久拒绝授权，请手动授予位置权限成功");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(getApplicationContext(), permissions);
                        } else {
                            toast("获取位置权限失败");
                        }
                    }
                });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottom_navigation_homepage) {
                    //首页
                    contentViewPager.setCurrentItem(0, false);
                    return true;
                } else if (item.getItemId() == R.id.bottom_navigation_collection) {
                    //收藏
                    contentViewPager.setCurrentItem(1, false);
                    return true;
                } else if (item.getItemId() == R.id.bottom_navigation_message) {
                    //消息
                    contentViewPager.setCurrentItem(2, false);
                    return true;
                } else if (item.getItemId() == R.id.bottom_navigation_personal) {
                    //个人
                    contentViewPager.setCurrentItem(3, false);
                    return true;
                }
                return false;
            }
        });
    }

    private void recoverLoginStatus() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                2,
                2,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        pool.submit(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("loggedInState", MODE_PRIVATE);

                //登陆状态 默认未登录
                boolean isLoggedIn = sp.getBoolean("isLoggedIn", false);
                String userToken = sp.getString("userToken", "");
                if (isLoggedIn) {
                    //登录密钥
                    //读取到LiveData
                    LoginStatusData.getLoginStatus().postValue(true);
                    LoginStatusData.getUserToken().postValue(userToken);
                } else {
                    LoginStatusData.getLoginStatus().postValue(false);
                    LoginStatusData.getUserToken().postValue("");
                }
            }
        });

        pool.shutdown();
    }

}