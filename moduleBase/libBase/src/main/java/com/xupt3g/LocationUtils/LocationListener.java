package com.xupt3g.LocationUtils;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

import lombok.ToString;

/**
 * 项目名: HeartTrip
 * 文件名: MyLocationListener
 *
 * @author: lukecc0
 * @data:2024/3/15 下午1:27
 * @about: TODO 设置位置监听器，调用get方法获取位置信息
 */

@ToString
public class LocationListener extends BDAbstractLocationListener {

    /**
     * 监听获取的位置信息
     */
    private BDLocation locData;
    private static LocationListener mInstance = null;

    public static LocationListener getInstance() {
        if (mInstance == null) {
            synchronized (LocationListener.class) {
                if (mInstance == null) {
                    return new LocationListener();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取监听的位置信息
     *
     * @return {@link BDLocation}
     */
    public BDLocation getLocData() {
        return locData;
    }

    private LocationListener() {
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        locData = location;
    }

}