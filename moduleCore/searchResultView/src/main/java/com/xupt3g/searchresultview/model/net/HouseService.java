package com.xupt3g.searchresultview.model.net;

import com.xupt3g.searchresultview.model.HousingData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 项目名: HeartTrip
 * 文件名: HouseService
 *
 * @author: lukecc0
 * @data:2024/4/17 下午2:38
 * @about: TODO 使用Retrofit2加载房屋搜索
 */

public interface HouseService {

    /**
     * 获取房屋信息
     *
     * @param kekWordReq
     * @return {@link Observable}<{@link HousingData}>
     */
    @POST("/travel/v1/homestay/searchByLocation")
    Observable<HousingData> getHouseData(@Body KeyWordReq kekWordReq);
}
