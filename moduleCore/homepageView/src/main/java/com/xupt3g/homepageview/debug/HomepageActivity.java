package com.xupt3g.homepageview.debug;

import static com.xuexiang.xui.utils.XToastUtils.toast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.xupt3g.UiTools.UiTool;
import com.xupt3g.homepageview.R;
import com.xupt3g.homepageview.model.SearchedLocationData;
import com.xupt3g.homepageview.model.net.LocationInfoTask;
import com.xupt3g.homepageview.presenter.LocationInfoPresenter;
import com.xupt3g.homepageview.view.CityPickerFragment;
import com.xupt3g.LocationUtils.LocationListener;
import com.xupt3g.LocationUtils.LocationService;

import java.util.List;

/**
 * @author lukecc0
 * @date 2024/03/06
 */
@Route(path = "/homepageView/HomepageActivity")
public class HomepageActivity extends AppCompatActivity {

    private static final String TAG = HomepageActivity.class.getSimpleName();

    private FragmentManager FragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        //使用百度定位Sdk
        LocationListener locationListener = LocationListener.getInstance();

        try {
            LocationService locationService = LocationService.getInstance();
            locationService.init(getApplicationContext());
            LocationService.start(locationListener);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        //实现MVP绑定

        CityPickerFragment cityPickerFragment = initCityPicker();
        LocationInfoTask locationInfoTask = LocationInfoTask.getInstance();
        LocationInfoPresenter presenter = new LocationInfoPresenter(locationInfoTask, cityPickerFragment);

        cityPickerFragment.setPresenter(presenter);




        //沉浸式布局栏
        UiTool.setImmersionBar(this, true);


        //获取定位权限
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


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private CityPickerFragment initCityPicker() {
        FragmentManager = getSupportFragmentManager();
        CityPickerFragment cityPickerFragment = (CityPickerFragment) FragmentManager.findFragmentById((int) R.layout.city_picke_fragment);

        if (cityPickerFragment == null) {
            cityPickerFragment = CityPickerFragment.newInstance();
            FragmentTransaction ft = FragmentManager.beginTransaction();
            ft.add(R.id.city_fragment_container, cityPickerFragment);

            ft.addToBackStack(null);
            ft.commit();
        }
        return cityPickerFragment;
    }
}