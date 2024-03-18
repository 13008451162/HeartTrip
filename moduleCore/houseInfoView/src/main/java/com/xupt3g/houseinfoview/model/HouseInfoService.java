package com.xupt3g.houseinfoview.model;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.model.HouseInfoService
 *
 * @author: shallew
 * @data: 2024/3/12 20:40
 * @about: TODO 民宿详情的基础信息接口服务
 */
public interface HouseInfoService {
    /**
     * 无需登录
     * @return 返回民宿详情的基本数据
     * @param houseId 民宿Id
     */
    @FormUrlEncoded
    @POST("/travel/v1/homestay/homestayDetail")
    Call<HouseInfoBaseDataResponse> getHouseInfoBaseData(@Header("UserToken") String userToken,@Field("HouseId") int houseId);

    /**
     * 无需登录
     * @return 返回推荐的6个民宿
     */
    @POST("/travel/v1/collections/guessList")
    Call<RecommendHouseResponse> getRecommendHousesList();

}
