package com.xupt3g.houseinfoview;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.HouseInfoApp
 *
 * @author: shallew
 * @data: 2024/4/8 19:09
 * @about: TODO
 */
public class HouseInfoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
