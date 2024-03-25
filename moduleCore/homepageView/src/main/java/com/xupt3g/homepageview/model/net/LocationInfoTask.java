package com.xupt3g.homepageview.model.net;

import static com.xupt3g.LocationUtils.SDKConstant.AK_WEB;
import static com.xupt3g.LocationUtils.SDKConstant.OUTPUT;

import com.xupt3g.homepageview.model.SearchedLocationData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名: HeartTrip
 * 文件名: LocationInfoTask
 *
 * @author: lukecc0
 * @data:2024/3/17 下午10:40
 * @about: TODO
 */


public class LocationInfoTask implements LocationNetTask<SearchedLocationData> {

    private static String HOST = "https://api.map.baidu.com/";
    private static String REGION = "全国";
    private static LocationInfoTask INSTANCE = null;

    private Retrofit retrofit;


    public static LocationInfoTask getInstance() {
        if (INSTANCE == null) {
            return new LocationInfoTask();
        }
        return INSTANCE;
    }

    private LocationInfoTask() {
        createRetrofit();
    }

    private void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Override
    public Observable<SearchedLocationData> execute(String query) {

        LocationService locationService = retrofit.create(LocationService.class);

        Observable<SearchedLocationData> observable = locationService.getLocation(query, REGION, true, OUTPUT, AK_WEB)
                .subscribeOn(Schedulers.io())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }


    @Override
    public Disposable execute(String query, String region) {
        return null;
    }
}
