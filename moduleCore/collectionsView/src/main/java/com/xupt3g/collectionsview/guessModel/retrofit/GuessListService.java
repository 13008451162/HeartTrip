package com.xupt3g.collectionsview.guessModel.retrofit;

import com.xupt3g.collectionsview.guessModel.retrofit.GuessListResponse;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.guessModel.retrofit.GuessListService
 *
 * @author: shallew
 * @data:2024/2/20 0:03
 * @about: TODO
 */
public interface GuessListService {
    /**
     * 无需登录
     * @return 返回猜你喜欢列表
     * TODO 获取猜你喜欢列表
     */
    @POST("/travel/v1//homestay/guessList")
    Call<GuessListResponse> getGuessList();

}
