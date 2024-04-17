package com.xupt3g.searchresultview.model.net;

import io.reactivex.rxjava3.core.Observable;

/**
 * 项目名: HeartTrip
 * 文件名: CountyNetTask
 *
 * @author: lukecc0
 * @data:2024/4/12 下午6:35
 * @about: TODO 搜索城市的下级行政区县
 */

public interface CountyNetTask<T> {
    /**
     * 搜索下级城市行政区
     * @param query
     * @return {@link Observable}<{@link T}>
     */
    Observable<T> execute(String query);
}
