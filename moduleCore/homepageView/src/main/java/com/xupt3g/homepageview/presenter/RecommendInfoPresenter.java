package com.xupt3g.homepageview.presenter;

import android.util.Log;

import com.xupt3g.homepageview.model.HomestayListReq;
import com.xupt3g.homepageview.model.RecommendHomeData;
import com.xupt3g.homepageview.model.net.RecommendInfoTask;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 项目名: HeartTrip
 * 文件名: RecommendInfoPresenter
 *
 * @author: lukecc0
 * @data:2024/3/21 下午8:10
 * @about: TODO
 */

public class RecommendInfoPresenter implements RecommendInfoContrach.Presenter<RecommendHomeData> {

    /**
     * 每次请求8个数据
     */
    private static final int PAGE = 8;

    private RecommendInfoTask infoTask;

    private RecommendInfoContrach.HomeView homeView;

    private CompositeDisposable compositeDisposable;

    public RecommendInfoPresenter(RecommendInfoTask infoTask, RecommendInfoContrach.HomeView homeView) {
        this.infoTask = infoTask;
        this.homeView = homeView;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {
        if (!compositeDisposable.isDisposed() && compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void getHomeData(int page) {
        HomestayListReq homestayListReq = new HomestayListReq();
        homestayListReq.setPage(page);
        homestayListReq.setPageSize(PAGE);


        compositeDisposable.add(infoTask.execute(homestayListReq)
                .doOnNext(data -> Log.d("TAG", "getHomeData11: "+data))
                .onErrorResumeNext(throwable -> Observable.just(new RecommendHomeData()))
                .doOnNext(data -> Log.d("TAG", "getHomeData22: "+data))
                .subscribe(data -> homeView.revealRecycler(data),
                        error -> {
                            throw new RuntimeException("在访问推荐时出现异常： ");
                        }));
    }
}
