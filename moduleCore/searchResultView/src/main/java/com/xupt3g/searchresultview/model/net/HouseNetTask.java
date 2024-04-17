package com.xupt3g.searchresultview.model.net;

import io.reactivex.rxjava3.core.Observable;

/**
 * 项目名: HeartTrip
 * 文件名: HouseNetTask
 *
 * @author: lukecc0
 * @data:2024/4/17 下午2:39
 * @about: TODO 搜索推荐信息
 */

public interface HouseNetTask<T> {

    /**
     * 搜索房屋
     * @param keyword 关键字
     * @return {@link Observable}<{@link T}>
     */
    Observable<T> execute(KeyWordReq keyword);
}
