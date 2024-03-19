package com.xupt3g.LocationUtils;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

/**
 * 定位服务
 *
 * @author lukecc0
 * @date 2024/03/17
 * TODO 初始化百度定位功能,每次使用时先init，再start
 */
public class LocationService {

    private static volatile LocationService sInstance = null;

    private LocationClient mClient = null;

    private LocationClientOption mOption = new LocationClientOption();


    private LocationService() {

    }


    /***
     * 初始化百度定位服务
     * @param context
     */
    public void init(Context context) throws Exception {
        SDKInitializer.setAgreePrivacy(context.getApplicationContext(), true);
        LocationClient.setAgreePrivacy(true);
        SDKInitializer.initialize(context);
        SDKInitializer.setCoordType(CoordType.BD09LL);

        if (mClient == null) {
            mClient = new LocationClient(context.getApplicationContext());
            mClient.setLocOption(getDefaultLocationClientOption());
        }
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static LocationService getInstance() {
        if (sInstance == null) {
            synchronized (LocationService.class) {
                if (sInstance == null) {
                    sInstance = new LocationService();
                }
            }
        }
        return sInstance;
    }

    /***
     * 注册定位监听
     * @param listener
     * @return
     */

    private LocationService registerListener(BDAbstractLocationListener listener) {
        if (listener != null) {
            mClient.registerLocationListener(listener);
        }
        return this;
    }

    /**
     * 注销定位监听
     *
     * @param listener
     */
    private LocationService unregisterListener(BDAbstractLocationListener listener) {
        if (listener != null) {
            mClient.unRegisterLocationListener(listener);
        }
        return this;
    }

    /***
     * 设置定位参数,主要用于修改位置参数
     * @param option
     * @return
     */
    private boolean setLocationOption(LocationClientOption option) {
        if (option != null) {
            if (mClient.isStarted()) {
                mClient.stop();
            }
            mClient.setLocOption(option);
            return true;
        }
        return false;
    }

    /***
     *
     * @return DefaultLocationClientOption  默认ClientOption 设置
     */
    private LocationClientOption getDefaultLocationClientOption() {
        if (mOption != null) {

            mOption.setIsNeedAddress(true);
            //可选，是否需要地址信息，默认为不需要，即参数为false
            //如果开发者需要获得当前点的地址信息，此处必须为true

            mOption.setNeedNewVersionRgc(true);

            mOption.setOpenGps(true); // 打开gps

            mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

            mOption.setCoorType("gcj02");

            mOption.setFirstLocType(LocationClientOption.FirstLocType.SPEED_IN_FIRST_LOC);

            mOption.setScanSpan(1_000_000);

            mOption.setOpenGnss(true);

        }
        return mOption;
    }


    /**
     * 开始定位
     *
     * @param listener 位置监听器
     */
    public static void start(BDAbstractLocationListener listener) {

        getInstance().registerListener(listener).start();
    }

    /**
     * 开始定位
     */
    private LocationService start() {

        if (mClient != null && !mClient.isStarted()) {
            mClient.start();
        }
        return this;
    }

    /**
     * 重启定位设置
     * @param listener
     */
    public static void restart(BDAbstractLocationListener listener) {
        getInstance().registerListener(listener).restart();
    }

    private LocationService restart() {
        if (mClient != null && mClient.isStarted()) {
            mClient.restart();
        }
        return this;
    }

    /**
     * 停止定位
     */
    public static void stop(BDAbstractLocationListener listener) {
        getInstance().unregisterListener(listener).stop();
    }

    /**
     * 停止定位
     */
    private LocationService stop() {
        if (mClient != null && mClient.isStarted()) {
            mClient.stop();
        }
        return this;
    }

}
