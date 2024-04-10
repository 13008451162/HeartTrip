package com.xupt3g.personalmanagementview.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xupt3g.mylibrary1.response.FileUploadResponse;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;
import com.xupt3g.personalmanagementview.model.retrofit.AccountInfoResponse;
import com.xupt3g.personalmanagementview.model.retrofit.UserInfo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.personalmanagementview.model.AccountInfoGetImpl
 *
 * @author: shallew
 * @data: 2024/2/11 0:37
 * @about: TODO
 */
public interface AccountInfoImpl {
    /**
     *
     * @return 返回账户信息Response
     * TODO 获取账号信息的申请
     */
    MutableLiveData<AccountInfoResponse> getAccountInfoRequest();

    /**
     *
     * @param userInfo 新的用户信息 自定义类=
     * @return 返回是否操作成功
     * TODO 修改账号信息的申请
     */
    MutableLiveData<IsSuccessfulResponse> modifyAccountInfoRequest(UserInfo userInfo);

    /**
     *
     * @param file 用户头像文件
     * @return 用户新的头像的url
     * TODO 上传用户新的头像
     */
    MutableLiveData<FileUploadResponse> uploadUserAvatarRequest(MultipartBody.Part file);
}
