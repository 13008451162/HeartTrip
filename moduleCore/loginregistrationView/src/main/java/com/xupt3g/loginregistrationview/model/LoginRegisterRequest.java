package com.xupt3g.loginregistrationview.model;

import com.xupt3g.UiTools.RootDirectory;
import com.xupt3g.loginregistrationview.model.retrofit.JWTResponse;
import com.xupt3g.loginregistrationview.model.retrofit.LoginCheckService;
import com.xupt3g.mylibrary1.PublicRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.loginregistrationview.model.LoginRegisterRequest
 *
 * @author: shallew
 * @data:2024/1/26 23:27
 * @about: TODO 验证登录成功的逻辑部分
 */
public class LoginRegisterRequest implements LoginRegisterImpl {
    private JWTResponse iJWT;

    //是否网络请求失败
    private boolean isFailed = false;

    /**
     * @param mobile   用户输入的手机号码
     * @param password 密码
     * @return 返回登录的验证成果
     */
    @Override
    public JWTResponse loginCheck(String mobile, String password) {
        LoginCheckService loginCheckService = (LoginCheckService) PublicRetrofit.create(LoginCheckService.class);

        return new JWTResponse();
//        //获取接口的动态代理对象
//        loginCheckService.loginChecked(mobile, password).enqueue(new Callback<JWTResponse>() {
//            @Override
//            public void onResponse(Call<JWTResponse> call, Response<JWTResponse> response) {
//                JWTResponse jwt = response.body();//得到服务器返回的令牌
//                if (jwt != null) {
//                    iJWT = jwt;//获取了JWT令牌信息
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JWTResponse> call, Throwable t) {
//                //网络请求失败处理
//                isFailed = true;//标记网络请求失败
//            }
//        });
//        //正常取得令牌后isFailed为false，网络请求失败时isFailed为true
//        if (!isFailed && verify(iJWT)) {
//            //验证成功 返回JWT
//            return iJWT;
//        } else {
//            return null;
//        }
    }


    /**
     * @param jwt 令牌
     * TODO 通过令牌进行登录验证
     */
    private boolean verify(JWTResponse jwt) {
        //验证：响应码 200 提示信息 "OK" token 不为空（ != null）
        return jwt.getCode() == 200 && jwt.getMsg().equals("OK") && jwt.getData().getAccessToken() != null;
    }
}
