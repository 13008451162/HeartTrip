package com.xupt3g.personalmanagementview.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xupt3g.mylibrary1.PublicRetrofit;
import com.xupt3g.mylibrary1.response.FileUploadResponse;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;
import com.xupt3g.personalmanagementview.model.retrofit.AccountInfoGetService;
import com.xupt3g.personalmanagementview.model.retrofit.AccountInfoResponse;
import com.xupt3g.personalmanagementview.model.retrofit.UserInfo;

import java.io.File;
import java.io.IOException;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.personalmanagementview.model.AccountInfoRequest
 *
 * @author: shallew
 * @data: 2024/2/11 0:32
 * @about: TODO 请求并保存账号信息数据
 */
public class AccountInfoRequest implements AccountInfoImpl {
    private MutableLiveData<AccountInfoResponse> accountInfoLiveData;
    private MutableLiveData<FileUploadResponse> fileUploadLiveData;
    private MutableLiveData<IsSuccessfulResponse> modifyAccountInfoLiveData;

    private final AccountInfoGetService accountInfoGetService;

    public AccountInfoRequest() {
        accountInfoGetService = (AccountInfoGetService)
                PublicRetrofit.create(AccountInfoGetService.class);
    }

    /**
     *
     * @return 返回获取到的账户信息 结果可能为空
     * TODO 获取账户信息
     */
    @Override
    public MutableLiveData<AccountInfoResponse> getAccountInfoRequest() {
        accountInfoLiveData = new MutableLiveData<>();
        //需要登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录！");
            accountInfoLiveData.setValue(new AccountInfoResponse(PublicRetrofit.getErrorMsg()));
            return accountInfoLiveData;
        }
        //获取接口的动态代理对象
        accountInfoGetService.getAccountInfo(LoginStatusData.getUserToken().getValue())
                .enqueue(new Callback<AccountInfoResponse>() {
            @Override
            public void onResponse(@NonNull Call<AccountInfoResponse> call, Response<AccountInfoResponse> response) {
                AccountInfoResponse body = response.body();
                if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                    Log.d("AccountInfoResponse", "onResponse: " + body);
                    accountInfoLiveData.setValue(body);
                } else {
                    accountInfoLiveData.setValue(new AccountInfoResponse(PublicRetrofit.getErrorMsg()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccountInfoResponse> call, Throwable t) {
                XToastUtils.error("数据请求失败！");
                accountInfoLiveData.setValue(new AccountInfoResponse(PublicRetrofit.getErrorMsg()));
            }
        });

        return accountInfoLiveData;
    }

    /**
     *
     * @param userInfo 新的用户信息 自定义类
     * @return 返回是否操作成功
     * TODO 更新用户信息 不更新用户头像
     */
    @Override
    public MutableLiveData<IsSuccessfulResponse> modifyAccountInfoRequest(UserInfo userInfo) {
        modifyAccountInfoLiveData = new MutableLiveData<>();
        //需要登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录！");
            modifyAccountInfoLiveData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
            return modifyAccountInfoLiveData;
        }

        //传入用户TOKEN和新的用户信息对象 其中avatar == null
        accountInfoGetService.updateAccountInfo(LoginStatusData.getUserToken().getValue(), userInfo)
                .enqueue(new Callback<IsSuccessfulResponse>() {
                    @Override
                    public void onResponse(Call<IsSuccessfulResponse> call, Response<IsSuccessfulResponse> response) {
                        IsSuccessfulResponse body = response.body();
                        Log.d("modifyTAG", "onResponse: " + body);
                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                            modifyAccountInfoLiveData.setValue(body);
                        } else {
                            modifyAccountInfoLiveData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
                        }
                    }

                    @Override
                    public void onFailure(Call<IsSuccessfulResponse> call, Throwable t) {
                        XToastUtils.error("网络请求失败！所有信息");
                        modifyAccountInfoLiveData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
                    }
                });
        return modifyAccountInfoLiveData;
    }

    /**
     *
     * @param file 用户头像文件
     * @return 返回新的用户头像的url 结果可能为空
     * TODO 更新用户头像
     */
    @Override
    public MutableLiveData<FileUploadResponse> uploadUserAvatarRequest(MultipartBody.Part file) {
        fileUploadLiveData = new MutableLiveData<>();
        //需要登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录！");
            fileUploadLiveData.setValue(new FileUploadResponse(PublicRetrofit.getErrorMsg()));
            return fileUploadLiveData;
        }

        //测试 作为新的头像
//        return "https://i0.hdslb.com/bfs/face/403cfdd078739a5142325934f05c3ac9f7787879.jpg";

        accountInfoGetService.uploadUserAvatar(LoginStatusData.getUserToken().getValue(), file)
                .enqueue(new Callback<FileUploadResponse>() {
                    @Override
                    public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
                        FileUploadResponse body = response.body();
                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                            fileUploadLiveData.setValue(body);
                        } else {
                            fileUploadLiveData.setValue(new FileUploadResponse(PublicRetrofit.getErrorMsg()));
                        }
                    }

                    @Override
                    public void onFailure(Call<FileUploadResponse> call, Throwable t) {
                        XToastUtils.error("网络请求失败！头像");
                        fileUploadLiveData.setValue(new FileUploadResponse(PublicRetrofit.getErrorMsg()));
                    }
                });
        return fileUploadLiveData;
    }
}
