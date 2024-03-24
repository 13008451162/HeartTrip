package com.xupt3g.homepageview.model.net;

import com.xupt3g.homepageview.model.HomestayListReq;

import io.reactivex.rxjava3.core.Observable;

/**
 * 项目名: HeartTrip
 * 文件名: RecommendNetTask
 *
 * @author: lukecc0
 * @data:2024/3/21 下午3:41
 * @about: TODO 房间推荐网络信息管理类
 */

public interface RecommendNetTask<T> {

    /**
     * 加载房屋信息
     * @param homestayListReq
     * @return {@link Observable}<{@link T}>
     */
    Observable<T> execute(HomestayListReq homestayListReq);
}
