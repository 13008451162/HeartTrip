package com.xupt3g.searchresultview.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.xupt3g.searchresultview.model.CountyData;
import com.xupt3g.searchresultview.model.net.CountyInfoTask;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * 项目名: HeartTrip
 * 文件名: CountyInfoPresent
 *
 * @author: lukecc0
 * @data:2024/4/12 下午8:14
 * @about: TODO 处理搜索城市下级行政区的Presenter
 */

public class CountyInfoPresent implements SearchInfoContract.CountyInfoPresenter<CountyData> {


    CompositeDisposable compositeDisposable;

    CountyInfoTask countyInfoTask;

    SearchInfoContract.SearchView searchView;

    public CountyInfoPresent(CountyInfoTask countyInfoTask, SearchInfoContract.SearchView searchView) {
        this.countyInfoTask = countyInfoTask;
        this.searchView = searchView;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void unSubscribe() {
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    @Override
    public Observable<CountyData> getCountyData(String city) {
        return countyInfoTask.execute(city);
    }

    @Override
    public void setDropDownMenu(String city) {
        subscribe(getCountyData(city)
                .onErrorResumeNext(throwable -> {
                    CountyData countyData = new CountyData();
                    CountyData.DistrictsDTO.Districts districtsDTO = new CountyData.DistrictsDTO.Districts();
                    districtsDTO.setName("位置信息");
                    countyData.getDistricts().getDistricts().add(districtsDTO);
                    return Observable.just(countyData);
                })
                .doOnNext(countyData -> {
                    Log.e("TAG", "setDropDownMenu: "+countyData);
                })
                .subscribe(data -> searchView.initDropDownMenu(data),
                        error -> {
                            throw new RuntimeException("加载城市下级行政区出现异常");
                        }));
    }


}
