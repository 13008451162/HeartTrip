package com.xupt3g.hearttrip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
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
            collectionManagerService = (CollectionManagerService) ARouter.getInstance().build("/collections/CollectionManagerImpl").navigation();
            browsedHistoryManagerService = (BrowsedHistoryManagerService) ARouter.getInstance().build("/browsingHistoryView/BrowsingHistoryRequest").navigation();

            ToastUtils.toast("collectionsCount == " + collectionManagerService.getCollectionsCount() + "\nhistoryCount == " + browsedHistoryManagerService.getBrowsedHistoryCount()
                    + "\n" + browsedHistoryManagerService.addBrowsedHistory(1));

            findViewById(R.id.hello).setOnClickListener(view -> {
                Log.d("TAG", "onCreate: 点击了");
                ARouter.getInstance().build("/personalManagement/PersonalManagementActivity").navigation();

            });
        }
    }
}