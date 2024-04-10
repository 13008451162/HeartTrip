package com.xupt3g.houseinfoview.model.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.model.retrofit.HouseInfoService
 *
 * @author: shallew
 * @data: 2024/3/12 20:40
 * @about: TODO 民宿详情的基础信息接口服务
 */
public interface HouseInfoService {
    /**
     * 无需登录
     * @return 返回民宿详情的基本数据
     */
    @POST("/travel/v1/homestay/homestayDetail")
    Call<HouseInfoBaseDataResponse> getHouseInfoBaseData(@Header("Authorization") String userToken, @Body HouseInfoRequestBody body);


    @POST("/travel/v1/homestay/homestayDetailWithoutLogin")
    Call<HouseInfoBaseDataResponse> getHouseInfoBaseData(@Body HouseInfoRequestBody body);

    /**
     * 无需登录
     * @return 返回推荐的6个民宿
     */
    @POST("/travel/v1/homestay/guessList")
    Call<RecommendHouseResponse> getRecommendHousesList();

}
