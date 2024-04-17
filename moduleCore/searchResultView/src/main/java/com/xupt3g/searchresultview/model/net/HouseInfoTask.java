package com.xupt3g.searchresultview.model.net;

import android.util.Log;

import com.xupt3g.UiTools.RootDirectory;
import com.xupt3g.searchresultview.model.HousingData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名: HeartTrip
 * 文件名: HouseInfoTask
 *
 * @author: lukecc0
 * @data:2024/4/17 下午2:47
 * @about: TODO 用于实现房屋信息搜索
 */

public class HouseInfoTask implements HouseNetTask<HousingData> {

    Retrofit retrofit;

    private static HouseInfoTask INSTANCE = null;

    public static HouseInfoTask getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HouseInfoTask();
            return INSTANCE;
        }
        return INSTANCE;
    }

    public HouseInfoTask() {
        onCreateRetorfit();
    }

    private void onCreateRetorfit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(RootDirectory.getNetRoot())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Override
    public Observable<HousingData> execute(KeyWordReq keyword) {
        HouseService houseService = retrofit.create(HouseService.class);
        Log.d("TAG", "execute: "+keyword.toString());
        return houseService.getHouseData(keyword)
                .subscribeOn(Schedulers.io())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
