package com.xupt3g.browsinghistoryview.model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.browsinghistoryview.model.retrofit.BrowsingHistoryManageService;
import com.xupt3g.browsinghistoryview.model.retrofit.BrowsingHistoryResponse;
import com.xupt3g.browsinghistoryview.model.retrofit.HistoryData;
import com.xupt3g.browsinghistoryview.model.retrofit.HistoryRequestBody;
import com.xupt3g.mylibrary1.implservice.BrowsedHistoryManagerService;
import com.xupt3g.mylibrary1.implservice.CollectionManagerService;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xupt3g.mylibrary1.PublicRetrofit;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.model.BrowsingHistoryRequest
 *
 * @author: shallew
 * @data: 2024/2/28 16:59
 * @about: TODO Model层的实现类 进行对浏览记录数据的请求
 */
@Route(path = "/browsingHistoryView/BrowsingHistoryRequest")
public class BrowsingHistoryRequest implements BrowsingHistoryManageImpl, BrowsedHistoryManagerService {
    private BrowsingHistoryManageService browsingHistoryManageService;
    private MutableLiveData<BrowsingHistoryResponse> historyListLiveData;
    private MutableLiveData<IsSuccessfulResponse> clearSuccessLivaData;
    private MutableLiveData<IsSuccessfulResponse> removeSuccessLiveData;

    public BrowsingHistoryRequest() {
        //获取收藏的动态代理对象
        browsingHistoryManageService = (BrowsingHistoryManageService) PublicRetrofit.create(BrowsingHistoryManageService.class);
    }

    /**
     * @return 返回历史记录集合
     * TODO 获取历史记录集合 本地方法
     */
    @Override
    public MutableLiveData<BrowsingHistoryResponse> getBrowsingHistoryList() {
        historyListLiveData = new MutableLiveData<>();
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录");
            historyListLiveData.setValue(new BrowsingHistoryResponse(PublicRetrofit.getErrorMsg()));
            return historyListLiveData;
        }

        browsingHistoryManageService.getBrowsingHistoryList(LoginStatusData.getUserToken().getValue())
                .enqueue(new Callback<BrowsingHistoryResponse>() {
                    @Override
                    public void onResponse(Call<BrowsingHistoryResponse> call, Response<BrowsingHistoryResponse> response) {
                        BrowsingHistoryResponse body = response.body();
                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                            historyListLiveData.setValue(body);
                        } else {
                            historyListLiveData.setValue(new BrowsingHistoryResponse(PublicRetrofit.getErrorMsg()));
                        }
                        Log.d("TAG321", "onResponse: " + response.body());
                    }

                    @Override
                    public void onFailure(Call<BrowsingHistoryResponse> call, Throwable t) {
                        ToastUtils.toast("网络请求失败！");
                        historyListLiveData.setValue(new BrowsingHistoryResponse(PublicRetrofit.getErrorMsg()));
                    }
                });
        return historyListLiveData;
    }

    /**
     * @return 是否成功清空历史记录
     * TODO 清空历史记录 本地方法
     */
    @Override
    public MutableLiveData<IsSuccessfulResponse> clearBrowsingHistoryList() {
        clearSuccessLivaData = new MutableLiveData<>();
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录");
            clearSuccessLivaData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
            return clearSuccessLivaData;
        }
        browsingHistoryManageService.clearBrowsingHistoryList(LoginStatusData.getUserToken().getValue())
                .enqueue(new Callback<IsSuccessfulResponse>() {
                    @Override
                    public void onResponse(Call<IsSuccessfulResponse> call, Response<IsSuccessfulResponse> response) {
                        IsSuccessfulResponse body = response.body();
                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                            clearSuccessLivaData.setValue(body);
                            if (body.isSuccess() && !BuildConfig.isModule) {
                                //如果清空成功且集成模式
                                //通知PersonalManagement页面修改浏览历史数量
                                EventBus.getDefault().post(BrowsedHistoryManagerService.BROWSED_HISTORY_HAS_CHANGED);
                            }
                        } else {
                            clearSuccessLivaData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
                        }
                    }

                    @Override
                    public void onFailure(Call<IsSuccessfulResponse> call, Throwable t) {
                        clearSuccessLivaData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
                        ToastUtils.toast("网络请求失败！");
                    }
                });
        return clearSuccessLivaData;
    }

    /**
     * @return 是否成功移除历史记录子项
     * TODO 移除历史记录子项 本地方法
     */
    @Override
    public MutableLiveData<IsSuccessfulResponse> removeHistoryItem(int id) {
        Log.d("remoteHistory", "removeHistoryItem: " + id);
        removeSuccessLiveData = new MutableLiveData<>();
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录");
            removeSuccessLiveData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
            return removeSuccessLiveData;
        }
        browsingHistoryManageService.removeHistoryFromList(LoginStatusData.getUserToken().getValue(), new HistoryRequestBody(id))
                .enqueue(new Callback<IsSuccessfulResponse>() {
                    @Override
                    public void onResponse(Call<IsSuccessfulResponse> call, Response<IsSuccessfulResponse> response) {
                        IsSuccessfulResponse body = response.body();
                        Log.d("remoteHistory", "removeHistoryItem: " + response.body());

                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                            removeSuccessLiveData.setValue(body);
                            if (body.isSuccess() && !BuildConfig.isModule) {
                                //如果移除成功且集成模式
                                //通知PersonalManagement页面修改浏览历史数量
                                EventBus.getDefault().post(BrowsedHistoryManagerService.BROWSED_HISTORY_HAS_CHANGED);
                            }
                        } else {
                            removeSuccessLiveData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
                        }
                    }

                    @Override
                    public void onFailure(Call<IsSuccessfulResponse> call, Throwable t) {
                        ToastUtils.toast("网络请求失败！");
                        removeSuccessLiveData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
                    }
                });
        return removeSuccessLiveData;
    }

    /**
     * TODO 暴露接口方法 获取浏览历史数据的数量
     */
    @Override
    public MutableLiveData<Integer> getBrowsedHistoryCount(LifecycleOwner owner) {
        MutableLiveData<Integer> resultLivaData = new MutableLiveData<>();
        MutableLiveData<BrowsingHistoryResponse> liveData = getBrowsingHistoryList();
        liveData.observe(owner, new Observer<BrowsingHistoryResponse>() {

            @Override
            public void onChanged(BrowsingHistoryResponse browsingHistoryResponse) {
                if (!browsingHistoryResponse.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    //response正常 未出错
                    List<HistoryData> historyDataList = browsingHistoryResponse.getHistoryDataList();
                    if (historyDataList != null) {
                        resultLivaData.setValue((Integer) historyDataList.size());
                    } else {
                        resultLivaData.setValue(0);
                    }
                }
            }
        });
        return resultLivaData;
    }

    @Override
    public void init(Context context) {
        if (browsingHistoryManageService == null) {
            browsingHistoryManageService = (BrowsingHistoryManageService) PublicRetrofit.create(BrowsingHistoryManageService.class);
        }
    }

}
