package com.xupt3g.loginregistrationview.model.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.loginregistrationview.model.retrofit.LoginCheckService
 *
 * @author: shallew
 * @data:2024/1/26 22:56
 * @about: TODO 登录验证网络请求接口 Retrofit
 */
public interface LoginCheckService {

    /**
     *
     * @param mobile 手机号码
     * @param password 密码
     * @return
     * TODO 验证是否能够成功登录或注册
     */
    @FormUrlEncoded
    @POST("/usercenter/v1/user/register")
    Call<JWTResponse> loginChecked(@Field("mobile") String mobile, @Field("password") String password);

}
