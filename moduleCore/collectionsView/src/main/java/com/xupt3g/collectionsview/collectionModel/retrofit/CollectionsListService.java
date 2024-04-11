package com.xupt3g.collectionsview.collectionModel.retrofit;

import com.xupt3g.collectionsview.collectionModel.AddCollectionRequestBody;
import com.xupt3g.collectionsview.collectionModel.RemoveCollectionRequestBody;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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
    @POST("/usercenter/v1/user/wishList")
    Call<CollectionsListResponse> getCollectionsList(@Header("Authorization") String userToken);

    /**
     *
     * @param userToken 用户登录获取的Token
     * @return 是否添加成功
     * TODO 添加一个民宿
     */
    @POST("/usercenter/v1/user/addWishList")
    Call<CollectionDataResponse> addCollection(@Header("Authorization") String userToken, @Body AddCollectionRequestBody body);

    /**
     *
     * @param userToken 用户登录获取的Token
     * @return 是否删除成功
     * TODO 删除一个民宿
     */
    @POST("/usercenter/v1/user/removeWishList")
    Call<IsSuccessfulResponse> removeCollection(@Header("Authorization") String userToken, @Body RemoveCollectionRequestBody body);
}
