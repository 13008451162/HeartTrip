package com.xupt3g.LocationUtils.NetLocation;

import static com.xupt3g.LocationUtils.SDKConstant.AK_WEB;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名: HeartTrip
 * 文件名: Location
 *
 * @author: lukecc0
 * @data:2024/4/3 下午7:49
 * @about: TODO
 */

public class LocationTask {
    private static String HOST = "https://api.map.baidu.com/";

    private Retrofit retrofit;


    /**
     * @param latitude  维度
     * @param longitude 经度
     * @return {@link Observable}<{@link LocationData}>
     */
    public Observable<LocationData> init(String latitude, String longitude) {
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        return execute(latitude + "," + longitude);

    }

    /**
     * @param location 位置信息
     * @return {@link Observable}<{@link LocationData}>
     */
    private Observable<LocationData> execute(String location) {

        NetService netService = retrofit.create(NetService.class);

        Observable<LocationData> observable = netService.getLocation(AK_WEB, "json", "wgs84ll", location)
                .subscribeOn(Schedulers.io())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread());

        return observable;
    }


}
