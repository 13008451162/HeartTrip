package com.xupt3g.messagesview.Presenter;

import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 项目名: HeartTrip
 * 文件名: BasePresenter
 *
 * @author: lukecc0
 * @data:2024/3/17 下午11:19
 * @about: TODO 用于取消网络请求功能
 */

public interface BasePresenter {
    /**
     * 将订阅添加到集合
     *
     * @param disposable
     */
    void subscribe(Disposable disposable);

    /**
     * 关闭所有的订阅
     */
    void unsubscribe();
}
