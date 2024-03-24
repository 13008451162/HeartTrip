package com.xupt3g.homepageview.model.net;

import com.xupt3g.homepageview.model.SearchedLocationData;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 项目名: HeartTrip
 * 文件名: LocationNetTask
 *
 * @author: lukecc0
 * @data:2024/3/17 下午10:32
 * @about: TODO 搜索请求的网络工具
 */

public interface LocationNetTask<T> {
    /**
     * 搜索位置信息
     * @param query
     * @return {@link Disposable}
     */
    Observable<T> execute(String query);

    /**
     * 搜索指定范围的位置信息
     * @param query
     * @param region 指定范围
     * @return {@link Disposable}
     */
    Disposable execute(String query, String region);
}
