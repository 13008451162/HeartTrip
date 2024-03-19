package com.xupt3g.hearttrip;

import static com.xuexiang.xutil.tip.ToastUtils.toast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType;
import com.xuexiang.xutil.data.DateUtils;
import com.xupt3g.UiTools.UiTool;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.UiTools.UiTool;
import com.xupt3g.mylibrary1.BrowsedHistoryManagerService;
import com.xupt3g.mylibrary1.CollectionManagerService;


public class MainActivity extends AppCompatActivity {
    private TimePickerView mTimePicker;
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
        ARouter.getInstance().build("/commentsview/CommentsActivity").navigation();

        UiTool.setImmersionBar(this, true);
        bottomNavigationView = findViewById(R.id.app_bottom_navigation);
        contentViewPager = findViewById(R.id.app_viewpager);
        contentFragments = new ArrayList<>();
        Fragment collectionFragment = (Fragment) ARouter.getInstance().build("/collectionsView/CollectionsFragment").navigation();
        Fragment homepageFragment = (Fragment) ARouter.getInstance().build("/homepageView/HomepageFragment").navigation();
        Fragment messageFragment = (Fragment) ARouter.getInstance().build("/messagesView/MessageFragment").navigation();
        Fragment personalFragment = (Fragment) ARouter.getInstance().build("/personalManagementView/PersonalManagementFragment").navigation();
        Collections.addAll(contentFragments, homepageFragment, collectionFragment, messageFragment, personalFragment);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle(), contentFragments);
        contentViewPager.setAdapter(pagerAdapter);
        contentViewPager.setOffscreenPageLimit(3);
        contentViewPager.setUserInputEnabled(false);

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
}