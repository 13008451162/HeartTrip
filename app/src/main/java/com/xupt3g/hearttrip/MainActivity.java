package com.xupt3g.hearttrip;

import static com.xuexiang.xutil.tip.ToastUtils.toast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType;
import com.xuexiang.xutil.data.DateUtils;
import com.xupt3g.UiTools.UiTool;

import java.util.Calendar;
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


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UiTool.setImmersionBar(this, true);

        if (!BuildConfig.isModule) {
            //集成模式
            findViewById(R.id.hello).setOnClickListener(view -> {
                Log.d("TAG", "onCreate: 点击了");
                ARouter.getInstance().build("/personalManagement/PersonalManagementActivity").navigation();

            });
        }
    }
}