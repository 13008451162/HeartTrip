package com.xupt3g.personalmanagementview.model.retrofit;

import com.xupt3g.mylibrary1.IsSuccessfulResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.personalmanagementview.model.retrofit.AccountInfoGetService
 *
 * @author: shallew
 * @data: 2024/2/11 0:06
 * @about: TODO 获取账号信息的retrofit服务接口
 */
public interface AccountInfoGetService {

    /**
     * @param token 用户登录从服务器获取的token
     * @return 返回用户信息Response
     */
    @POST("/usercenter/v1/user/detail")
    Call<AccountInfoResponse> getAccountInfo(@Header("Authorization") String token);

    /**
     *
     * @param token 用户登录时从服务器获取的token
     * @param userInfo 更新后的账户信息
     * @return 希望能返回TRUE作为更新成功的表示，FALSE作为更新失败的标志
     * TODO 修改用户除头像以外的信息
     */
    @POST("/usercenter/v1/user/updateUserInfo")
    Call<IsSuccessfulResponse> updateAccountInfo(@Header("Authorization") String token,@Body UserInfo userInfo);

    @Multipart
    @POST("/usercenter/v1/user/uploadUserAvatar")
    Call<AvatarUrlResponse> uploadUserAvatar(@Header("Authorization") String token, @Part MultipartBody.Part part);
}
