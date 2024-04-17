package com.xupt3g.searchresultview.presenter;

import android.util.Log;

import com.xupt3g.searchresultview.model.HousingData;
import com.xupt3g.searchresultview.model.net.HouseInfoTask;
import com.xupt3g.searchresultview.model.net.KeyWordReq;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 项目名: HeartTrip
 * 文件名: HouseInfoPresent
 *
 * @author: lukecc0
 * @data:2024/4/17 下午3:13
 * @about: TODO 处理房屋信息搜索
 */

public class HouseInfoPresent implements SearchInfoContract.houseInfoPresenter<HousingData> {


    private HouseInfoTask houseInfoTask;

    private SearchInfoContract.SearchView searchView;

    CompositeDisposable compositeDisposable;

    public HouseInfoPresent(HouseInfoTask houseInfoTask, SearchInfoContract.SearchView searchView) {
        this.houseInfoTask = houseInfoTask;
        this.searchView = searchView;

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe(Disposable disposable) {
        if (compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void unSubscribe() {
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    @Override
    public Observable<HousingData> getHousedata(KeyWordReq keyWordReq) {
        return houseInfoTask.execute(keyWordReq)
                .doOnError(throwable -> {
                    Log.e("TAG", "getHousedata: ", throwable);
                });
    }

    @Override
    public void setHouseAdapter(Observable<CharSequence> editObservable) {
        subscribe(editObservable
                .switchMap(sequence ->
                        {
                            KeyWordReq keyWordReq = new KeyWordReq(sequence.toString());
                            Log.d("TAG", "switchMap: " + keyWordReq.toString());
                            return getHousedata(keyWordReq)
                                    .onErrorResumeNext(throwable -> {
                                        HousingData housingData = new HousingData();
                                        return Observable.just(housingData);
                                    });
                        }
                )
                .doOnNext(housingData -> {
                    Log.d("TAG", "setHouseAdapter: " + housingData);
                })
                .subscribe(housingData -> {
                            searchView.sethouseView(housingData);
                        },
                        error -> {
                            throw new RuntimeException("搜索房屋出现异常");
                        })
        );

    }
}
