package com.xupt3g.personalmanagementview.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.mylibrary1.PublicRetrofit;
import com.xupt3g.mylibrary1.implservice.BrowsedHistoryManagerService;
import com.xupt3g.mylibrary1.implservice.CollectionManagerService;
import com.xupt3g.mylibrary1.response.FileUploadResponse;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;
import com.xupt3g.personalmanagementview.model.AccountInfoImpl;
import com.xupt3g.personalmanagementview.model.AccountInfoRequest;
import com.xupt3g.personalmanagementview.model.retrofit.AccountInfoResponse;
import com.xupt3g.personalmanagementview.model.retrofit.UserInfo;
import com.xupt3g.personalmanagementview.view.AccountInfoModifyImpl;
import com.xupt3g.personalmanagementview.view.AccountInfoShowImpl;

import okhttp3.MultipartBody;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.personalmanagementview.presenter.AccountInfoGetPresenter
 *
 * @author: shallew
 * @data: 2024/2/12 1:41
 * @about: TODO 账户信息获取的Presenter类
 */
public class AccountInfoPresenter {
    private AccountInfoImpl model;
    private AccountInfoShowImpl view;
    private AccountInfoModifyImpl view2;
    private MutableLiveData<AccountInfoResponse> responseLiveData;
    private BrowsedHistoryManagerService browsedHistoryManagerService;
    private CollectionManagerService collectionManagerService;

    public AccountInfoPresenter(AccountInfoShowImpl view) {
        this.view = view;
        this.model = new AccountInfoRequest();
        if (!BuildConfig.isModule) {
            //集成模式下
            browsedHistoryManagerService = (BrowsedHistoryManagerService)
                    ARouter.getInstance().build("/browsingHistoryView/BrowsingHistoryRequest").navigation();

            collectionManagerService = (CollectionManagerService)
                    ARouter.getInstance().build("/collectionsView/CollectionManagerImpl").navigation();
        }
    }

    public AccountInfoPresenter(AccountInfoModifyImpl view2) {
        this.model = new AccountInfoRequest();
        this.view2 = view2;
    }


    public void accountInfoGet() {
        //使用token在model获取账户信息
        responseLiveData = model.getAccountInfoRequest();
        //从model层进行网络申请
        if (responseLiveData != null) {
            ToastUtils.toast("response获取成功！！！！");
            view.showOnUi();
        }
    }
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            modifyOtherInfo((UserInfo) msg.obj);
        }
    };
    /**
     * @param userInfo 新的账户信息
     * TODO 在View中传入新的账户信息，Presenter会把新的账号信息交给Model层进行申请修改
     */
    public void accountInfoModify(UserInfo userInfo, MultipartBody.Part file) {
        if (file != null) {
            MutableLiveData<FileUploadResponse> imgUploadSuccessLiveData = model.uploadUserAvatarRequest(file);
            imgUploadSuccessLiveData.observe((LifecycleOwner) view2, new Observer<FileUploadResponse>() {
                @Override
                public void onChanged(FileUploadResponse response) {
                    if (response != null && !response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                        if (response.getUrl() != null) {
                            AccountInfoResponse temResponse = responseLiveData.getValue();
                            if (temResponse != null) {
                                temResponse.getUserInfo().setAvatar(response.getUrl());
                                responseLiveData.setValue(temResponse);
                            }
                            userInfo.setAvatar(response.getUrl());
                            Message message = new Message();
                            message.what = 1;
                            message.obj = userInfo;
                            handler.sendMessage(message);
                        } else {
                            view2.modifyFailed();
                            return ;
                        }
                    } else {
                        view2.modifyFailed();
                    }
                }
            });
        } else {
            modifyOtherInfo(userInfo);
        }
    }

    /**
     * TODO 修改头像之外的其他信息
     */
    private void modifyOtherInfo(UserInfo userInfo) {
        MutableLiveData<IsSuccessfulResponse> infoModifySuccessLiveData = model.modifyAccountInfoRequest(userInfo);
        infoModifySuccessLiveData.observe((LifecycleOwner) view2, new Observer<IsSuccessfulResponse>() {
            @Override
            public void onChanged(IsSuccessfulResponse response) {
                if (response != null && !response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    if (response.isSuccess()) {
                        responseLiveData.setValue(new AccountInfoResponse(200, "OK", userInfo));
                        view2.modifySuccessful();
                    } else {
                        view2.modifyFailed();
                    }
                }
            }
        });
    }

    public MutableLiveData<AccountInfoResponse> getResponseLiveData() {
        return responseLiveData;
    }

    public void setResponseLiveData(MutableLiveData<AccountInfoResponse> responseLiveData) {
        this.responseLiveData = responseLiveData;
    }

    public BrowsedHistoryManagerService getBrowsedHistoryManagerService() {
        return browsedHistoryManagerService;
    }

    public CollectionManagerService getCollectionManagerService() {
        return collectionManagerService;
    }
}
