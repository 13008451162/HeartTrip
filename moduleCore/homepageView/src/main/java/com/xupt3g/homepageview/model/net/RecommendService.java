package com.xupt3g.homepageview.model.net;

import android.database.Observable;

import com.xupt3g.homepageview.model.HomestayListReq;
import com.xupt3g.homepageview.model.RecommendHomeData;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 项目名: HeartTrip
 * 文件名: RecommendService
 *
 * @author: lukecc0
 * @data:2024/3/21 下午1:58
 * @about: TODO 进行推荐民宿的搜索
 */

public interface RecommendService {
    /**
     * 房屋推荐信息查询
     * @param homestayListReq
     * @return {@link Observable}<{@link RecommendHomeData}>
     */
    @POST("/travel/v1/homestay/homestayList")
    io.reactivex.rxjava3.core.Observable<RecommendHomeData> getHomeData(@Body HomestayListReq homestayListReq);
}
