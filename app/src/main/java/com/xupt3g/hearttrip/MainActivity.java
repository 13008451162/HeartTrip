package com.xupt3g.hearttrip;

import static com.xuexiang.xui.utils.XToastUtils.toast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.xupt3g.UiTools.UiTool;

import java.util.List;

import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.mylibrary1.BrowsedHistoryManagerService;
import com.xupt3g.mylibrary1.CollectionManagerService;
import com.xupt3g.LocationUtils.LocationListener;
import com.xupt3g.LocationUtils.LocationService;


public class MainActivity extends AppCompatActivity {
    private CollectionManagerService collectionManagerService;
    private BrowsedHistoryManagerService browsedHistoryManagerService;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationListener locationListener = LocationListener.getInstance();

        try {
            LocationService locationService = LocationService.getInstance();
            locationService.init(getApplicationContext());
            LocationService.start(locationListener);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        UiTool.setImmersionBar(this, true);

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