package com.xupt3g.searchresultview.model.net;

import static com.xupt3g.LocationUtils.SDKConstant.AK_WEB;

import com.xupt3g.searchresultview.model.CountyData;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名: HeartTrip
 * 文件名: CountyInfoTask
 *
 * @author: lukecc0
 * @data:2024/4/12 下午7:48
 * @about: TODO 实现行政区搜索的网络请求
 */

public class CountyInfoTask implements CountyNetTask<CountyData> {

    private static String HOST = "https://api.map.baidu.com/";
    private Retrofit retrofit;

    private static CountyInfoTask INSTANCE = null;

    public static CountyInfoTask getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CountyInfoTask();
            return INSTANCE;
        }
        return INSTANCE;
    }

    private CountyInfoTask() {
        onCreateRetrofit();
    }

    private void onCreateRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Override
    public Observable<CountyData> execute(String query) {

        CountyService countyService = retrofit.create(CountyService.class);

        return countyService.getCountyData(query, "1", AK_WEB)
                .subscribeOn(Schedulers.io())
                .retry(2)
                .observeOn(AndroidSchedulers.mainThread());
    }

}
