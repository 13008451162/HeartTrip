package com.xupt3g.browsinghistoryview.model.retrofit;

import com.xupt3g.browsinghistoryview.model.retrofit.BrowsingHistoryResponse;
import com.xupt3g.mylibrary1.IsSuccessfulResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.model.retrofit.BrowsingHistoryManageService
 *
 * @author: shallew
 * @data: 2024/2/28 16:19
 * @about: TODO 浏览历史管理服务Retrofit
 */
public interface BrowsingHistoryManageService {

    /**
     *
     * @param userToken 用户登录时获取的Token
     * @return 返回带有浏览记录集合的Response 返回结果可能为空
     *
     */
    @POST("/travel/v1/broswinghistory/getHistoryList")
    Call<BrowsingHistoryResponse> getBrowsingHistoryList(@Header("UserToken") String userToken);

    /**
     *
     * @param userToken 用户登录时获取的Token
     * @return 返回浏览历史集合是否清空成功的Response
     *
     */
    @POST("/travel/v1/broswinghistory/clearHistoryList")
    Call<IsSuccessfulResponse> clearBrowsingHistoryList(@Header("UserToken") String userToken);

    /**
     *
     * @param userToken 用户登录时获取的Token
     * @param houseId 要删除的子项民宿ID
     * @return 返回是否删除成功的Response
     *
     */
    @FormUrlEncoded
    @POST("/travel/v1/broswinghistory/removeHistory")
    Call<IsSuccessfulResponse> removeHistoryFromList(@Header("UserToken") String userToken,@Field("HouseId") int houseId);

    /**
     *
     * @param userToken 用户登录时获取的Token
     * @param houseId 要添加的子项民宿ID
     * @return 返回是否成功将民宿添加到浏览历史列表
     */
    @FormUrlEncoded
    @POST("/travel/v1/broswinghistory/addHistory")
    Call<IsSuccessfulResponse> addHistoryToList(@Header("UserToken") String userToken, @Field("HouseId") int houseId, @Field("CurrentTime") long currentTime);
}
