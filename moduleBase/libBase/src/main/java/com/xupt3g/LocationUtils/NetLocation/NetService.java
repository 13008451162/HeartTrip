package com.xupt3g.LocationUtils.NetLocation;

import android.database.Observable;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 项目名: HeartTrip
 * 文件名: NetService
 *
 * @author: lukecc0
 * @data:2024/4/3 下午7:52
 * @about: TODO  通过经纬度网络请求数据
 */

public interface NetService {


    /**
     * @return {@link Observable}<{@link LocationData}>
     * https://api.map.baidu.com/reverse_geocoding/v3/?ak=您的ak&output=json&coordtype=wgs84ll&location=31.225696563611,121.49884033194
     */
    @GET("reverse_geocoding/v3/?")
    io.reactivex.rxjava3.core.Observable<LocationData> getLocation(@Query("ak") String ak,
                                                                   @Query("output") String output,
                                                                   @Query("coordtype") String coordtype,
                                                                   @Query("location") String location);
}
