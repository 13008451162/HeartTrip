package com.xupt3g.personalmanagementview.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.mylibrary1.IsSuccessfulResponse;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xupt3g.mylibrary1.PublicRetrofit;
import com.xupt3g.personalmanagementview.model.retrofit.AccountInfoGetService;
import com.xupt3g.personalmanagementview.model.retrofit.AccountInfoResponse;
import com.xupt3g.personalmanagementview.model.retrofit.AvatarUrlResponse;
import com.xupt3g.personalmanagementview.model.retrofit.UserInfo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    private AccountInfoResponse accountInfo;
    private boolean isUpdateSuccessful;
    /**
     * 用户新的头像的url 替换成功 != null
     */
    private String newAvatarUrl;
    private final AccountInfoGetService accountInfoGetService;

    public AccountInfoRequest() {
        accountInfoGetService = (AccountInfoGetService) PublicRetrofit.create(AccountInfoGetService.class);
    }

    /**
     *
     * @return 返回获取到的账户信息 结果可能为空
     * TODO 获取账户信息
     */
    @Override
    public MutableLiveData<AccountInfoResponse> getAccountInfoRequest() {
        final MutableLiveData<AccountInfoResponse>[] mutableLiveData = new MutableLiveData[]{new MutableLiveData<>()};
        //需要登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录！");
            return null;
        }

        //测试数据
        mutableLiveData[0].setValue(new AccountInfoResponse(200, "OK", new UserInfo(
                114514, "18629627346", "告别寒冷冬季", 0, "https://i0.hdslb.com/bfs/face/e8daef29f566331fa57b94b1f5c5c326107776f3.jpg", "孤睾战神"
        )));

//        //获取接口的动态代理对象
//        accountInfoGetService.getAccountInfo(LoginStatusData.getUserToken().getValue())
//                .enqueue(new Callback<AccountInfoResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<AccountInfoResponse> call, Response<AccountInfoResponse> response) {
//                AccountInfoResponse body = response.body();
//                if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
//                    mutableLiveData[0].setValue(body);
//                }
//
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<AccountInfoResponse> call, Throwable t) {
//                ToastUtils.toast("数据请求失败！");
//                mutableLiveData[0] = null;
//            }
//        });

        return mutableLiveData[0];
    }

    /**
     *
     * @param userInfo 新的用户信息 自定义类
     * @return 返回是否操作成功
     * TODO 更新用户信息 不更新用户头像
     */
    @Override
    public boolean modifyAccountInfoRequest(UserInfo userInfo) {
        //需要登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录！");
            return false;
        }

        return true;

//        //传入用户TOKEN和新的用户信息对象 其中avatar == null
//        accountInfoGetService.updateAccountInfo(LoginStatusData.getUserToken().getValue(), userInfo)
//                .enqueue(new Callback<IsSuccessfulResponse>() {
//                    @Override
//                    public void onResponse(Call<IsSuccessfulResponse> call, Response<IsSuccessfulResponse> response) {
//                        IsSuccessfulResponse body = response.body();
//                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
//                            isUpdateSuccessful = body.isSuccess();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<IsSuccessfulResponse> call, Throwable t) {
//                        ToastUtils.toast("网络请求失败！");
//                        isUpdateSuccessful = false;
//                    }
//                });
//        return isUpdateSuccessful;
    }

    /**
     *
     * @param file 用户头像文件
     * @return 返回新的用户头像的url 结果可能为空
     * TODO 更新用户头像
     */
    @Override
    public String uploadUserAvatarRequest(MultipartBody.Part file) {
        //需要登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录！");
            return null;
        }

        //测试 作为新的头像
        return "https://i0.hdslb.com/bfs/face/403cfdd078739a5142325934f05c3ac9f7787879.jpg";

//        accountInfoGetService.uploadUserAvatar(LoginStatusData.getUserToken().getValue(), file)
//                .enqueue(new Callback<AvatarUrlResponse>() {
//                    @Override
//                    public void onResponse(Call<AvatarUrlResponse> call, Response<AvatarUrlResponse> response) {
//                        AvatarUrlResponse body = response.body();
//                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
//                            newAvatarUrl = body.getUrl();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<AvatarUrlResponse> call, Throwable t) {
//                        ToastUtils.toast("网络请求失败！");
//                        newAvatarUrl = null;
//                    }
//                });
//        return newAvatarUrl;
    }


}
