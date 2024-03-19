package com.xupt3g.homepageview.presenter;

import com.xupt3g.homepageview.model.SearchedLocationData;
import com.xupt3g.homepageview.model.net.LocationInfoTask;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import lombok.NonNull;

/**
 * 项目名: HeartTrip
 * 文件名: CityInfoPresenter
 *
 * @author: lukecc0
 * @data:2024/3/17 下午11:34
 * @about: TODO
 */

public class LocationInfoPresenter implements LocationInfoContract.Presenter<SearchedLocationData> {

    private LocationInfoTask locationInfoTask;

    private LocationInfoContract.LocationView locationView;

    private BehaviorSubject behaviorSubject = BehaviorSubject.create();

    private CompositeDisposable compositeDisposable;


    public LocationInfoPresenter(LocationInfoTask locationInfoTask, LocationInfoContract.LocationView locationView) {
        this.locationInfoTask = locationInfoTask;
        this.locationView = locationView;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }


    @Override
    public Observable<SearchedLocationData> getLocationData(String position) {
        return locationInfoTask.execute(position);
    }

    @Override
    public void locationSearch(Observable<CharSequence> sequenceObservable) {

        subscribe(sequenceObservable
                .switchMap(charSequence -> getLocationData(charSequence.toString())
                        .onErrorResumeNext(throwable -> Observable.just(new SearchedLocationData())) // 在发生错误后继续流
                )
                .subscribe(data -> locationView.searchRecycler(data),
                        error -> {
                            throw new RuntimeException("在位置搜索流时出现异常： " + LocationInfoPresenter.class.getName());
                        })
        );
    }

}
