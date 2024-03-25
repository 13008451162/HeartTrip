package com.xupt3g.personalmanagementview.presenter;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.mylibrary1.implservice.BrowsedHistoryManagerService;
import com.xupt3g.mylibrary1.implservice.CollectionManagerService;
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

    /**
     * @param userInfo 新的账户信息
     * TODO 在View中传入新的账户信息，Presenter会把新的账号信息交给Model层进行申请修改
     */
    public void accountInfoModify(UserInfo userInfo, MultipartBody.Part file) {
        if (file != null) {
            String url = model.uploadUserAvatarRequest(file);
            if (url != null) {
                AccountInfoResponse response = responseLiveData.getValue();
                if (response != null) {
                    response.getUserInfo().setAvatar(url);
                    responseLiveData.setValue(response);
                }
                userInfo.setAvatar(url);
            } else {
                view2.modifyFailed();
                return ;
            }
        }
        boolean b = model.modifyAccountInfoRequest(userInfo);
        if (b) {
            responseLiveData.setValue(new AccountInfoResponse(200, "OK", userInfo));
            view2.modifySuccessful();
        } else {
            view2.modifyFailed();
        }


//        if (userInfo == null && file != null) {
//            //只改头像
//            String avatarUrl = model.uploadUserAvatarRequest(file);
//
//            if (avatarUrl != null) {
//                //把新的头像Url赋值给
//                if (responseLiveData != null) {
//                    AccountInfoResponse response = responseLiveData.getValue();
//                    if (response != null) {
//                        response.getUserInfo().setAvatar(avatarUrl);
//                        responseLiveData.setValue(response);
//                    }
//                }
//                //修改成功
//                view2.modifySuccessful();
//            } else {
//                view2.modifyFailed();
//            }
//        } else if (userInfo != null && file == null) {
//            //只修改其他信息
//            boolean b = model.modifyAccountInfoRequest(userInfo);
//            if (b) {
//                responseLiveData.setValue(new AccountInfoResponse(200, "OK", userInfo));
//                view2.modifySuccessful();
//            } else {
//                view2.modifyFailed();
//            }
//        } else if (userInfo != null && file != null) {
//            //两个信息都修改
//            boolean b = model.modifyAccountInfoRequest(userInfo);
//            if (b) {
//                //如果修改成功再去修改头像
//                String s = model.uploadUserAvatarRequest(file);
//                if (s != null && responseLiveData != null) {
//                    AccountInfoResponse value = responseLiveData.getValue();
//                    if (value != null) {
//                        //两个申请返回都成功
//                        value.setUserInfo(userInfo);
//                        value.getUserInfo().setAvatar(s);
//                        responseLiveData.setValue(value);
//                    }
//                    view2.modifySuccessful();
//                }
//            } else {
//                view2.modifyFailed();
//            }
//        }
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
