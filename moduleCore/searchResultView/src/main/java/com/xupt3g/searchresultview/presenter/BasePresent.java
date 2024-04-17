package com.xupt3g.searchresultview.presenter;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 项目名: HeartTrip
 * 文件名: BasePresent
 *
 * @author: lukecc0
 * @data:2024/4/12 下午7:12
 * @about: TODO Presenter的基类
 */

public interface BasePresent {

    /**
     * 添加订阅
     * @param disposable
     */
    void subscribe(Disposable disposable);

    /**
     * 关闭所有订阅
     */
    void unSubscribe();
}
