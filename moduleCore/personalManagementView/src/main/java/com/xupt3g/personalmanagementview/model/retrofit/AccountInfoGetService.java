package com.xupt3g.personalmanagementview.model.retrofit;

import com.xupt3g.mylibrary1.response.FileUploadResponse;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
     * @param userToken 用户登录从服务器获取的token
     * @return 返回用户信息Response
     */
    @POST("/usercenter/v1/user/detail")
    Call<AccountInfoResponse> getAccountInfo(@Header("Authorization") String userToken);

    /**
     *
     * @param userToken 用户登录时从服务器获取的token
     * @param userInfo 更新后的账户信息
     * @return 希望能返回TRUE作为更新成功的表示，FALSE作为更新失败的标志
     * TODO 修改用户除头像以外的信息
     */
    @POST("/usercenter/v1/user/updateUserInfo")
    Call<IsSuccessfulResponse> updateAccountInfo(@Header("Authorization") String userToken,@Body UserInfo userInfo);

    @Multipart
    @POST("/usercenter/v1/upload")
    Call<FileUploadResponse> uploadUserAvatar(@Header("Authorization") String userToken, @Part MultipartBody.Part part);
}
