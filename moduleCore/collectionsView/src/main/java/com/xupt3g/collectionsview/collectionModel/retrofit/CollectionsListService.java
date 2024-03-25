package com.xupt3g.collectionsview.collectionModel.retrofit;

import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.CollectionsListGetService
 *
 * @author: shallew
 * @data: 2024/2/18 1:53
 * @about: TODO 收藏集合管理服务（获取收藏列表、添加收藏至列表、移除收藏）
 */
public interface CollectionsListService {

    /**
     *
     * @param userToken 用户登录获取的Token
     * @return 用户收藏列表
     * TODO 获取民宿列表
     */
    @POST("/travel/v1/collections/collectionsList")
    Call<CollectionsListResponse> getCollectionsList(@Header("UserToken") String userToken);

    /**
     *
     * @param userToken 用户登录获取的Token
     * @param houseId 用户要添加的民宿id
     * @return 是否添加成功
     * TODO 添加一个民宿
     */
    @FormUrlEncoded
    @POST("/travel/v1/collections/addCollection")
    Call<CollectionDataResponse> addCollection(@Header("UserToken") String userToken, @Field("HouseId") int houseId);

    /**
     *
     * @param userToken 用户登录获取的Token
     * @param houseId 用户要删除的民宿ID
     * @return 是否删除成功
     * TODO 删除一个民宿
     */
    @FormUrlEncoded
    @POST("/travel/v1/collections/removeCollection")
    Call<IsSuccessfulResponse> removeCollection(@Header("UserToken") String userToken, @Field("HouseId") int houseId);
}
