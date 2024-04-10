package com.xupt3g.loginregistrationview.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.UiTools.RootDirectory;
import com.xupt3g.loginregistrationview.model.retrofit.JWTResponse;
import com.xupt3g.loginregistrationview.model.retrofit.LoginCheckService;
import com.xupt3g.loginregistrationview.model.retrofit.LoginRegisterRequestBody;
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
 * @data: 2024/1/26 23:27
 * @about: TODO 验证登录成功的逻辑部分
 */
public class LoginRegisterRequest implements LoginRegisterImpl {
    private MutableLiveData<JWTResponse> loginLiveData;
    private MutableLiveData<JWTResponse> registerLiveData;
    private LoginCheckService loginCheckService;

    public LoginRegisterRequest() {
        this.loginCheckService = (LoginCheckService) PublicRetrofit.create(LoginCheckService.class);
    }


    /**
     * @param mobile   用户输入的手机号码
     * @param password 密码
     * @return 返回登录的验证成果
     */
    @Override
    public MutableLiveData<JWTResponse> loginCheck(String mobile, String password) {
        loginLiveData = new MutableLiveData<>();
        loginCheckService.loginChecked(new LoginRegisterRequestBody(mobile, password)).enqueue(new Callback<JWTResponse>() {
            @Override
            public void onResponse(Call<JWTResponse> call, Response<JWTResponse> response) {
                JWTResponse jwt = response.body();//得到服务器返回的令牌
                if (jwt != null && jwt.getCode() == 200 && "OK".equals(jwt.getMsg())) {
                    loginLiveData.setValue(jwt);
                } else {
                    loginLiveData.setValue(new JWTResponse());
                }
            }

            @Override
            public void onFailure(Call<JWTResponse> call, Throwable t) {
                //网络请求失败处理
                XToastUtils.error("网络请求失败！");
            }
        });
        return loginLiveData;
    }

    @Override
    public MutableLiveData<JWTResponse> registerNewAccont(String mobile, String password) {
        registerLiveData = new MutableLiveData<>();
        loginCheckService.registerNewAccont(new LoginRegisterRequestBody(mobile, password))
                .enqueue(new Callback<JWTResponse>() {
                    @Override
                    public void onResponse(Call<JWTResponse> call, Response<JWTResponse> response) {
                        JWTResponse body = response.body();
                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                            registerLiveData.setValue(body);
                        } else {
                            registerLiveData.setValue(new JWTResponse());
                        }
                    }

                    @Override
                    public void onFailure(Call<JWTResponse> call, Throwable t) {

                    }
                });

        return registerLiveData;
    }

}
