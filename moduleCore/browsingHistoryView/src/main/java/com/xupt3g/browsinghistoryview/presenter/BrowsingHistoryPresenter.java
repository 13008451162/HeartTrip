package com.xupt3g.browsinghistoryview.presenter;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.browsinghistoryview.model.BrowsingHistoryManageImpl;
import com.xupt3g.browsinghistoryview.model.BrowsingHistoryRequest;
import com.xupt3g.browsinghistoryview.model.retrofit.BrowsingHistoryResponse;
import com.xupt3g.browsinghistoryview.view.BrowsingHistoryUiImpl;
import com.xupt3g.mylibrary1.PublicRetrofit;
import com.xupt3g.mylibrary1.implservice.CollectionManagerService;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;


/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.presenter.ThePresenter
 *
 * @author: shallew
 * @data: 2024/2/28 19:17
 * @about: TODO Presenter层
 */
public class BrowsingHistoryPresenter {
    private final BrowsingHistoryUiImpl view;
    private final BrowsingHistoryManageImpl model;

    public BrowsingHistoryPresenter(BrowsingHistoryUiImpl view) {
        this.view = view;
        this.model = new BrowsingHistoryRequest();
    }

    /**
     * TODO 从Model层获取浏览历史集合，如果集合不为空则获取成功，可以调用View层的方法显示到Ui界面上
     */
    public void getHistoryListShowOnUi() {
        MutableLiveData<BrowsingHistoryResponse> historyListLivaData = model.getBrowsingHistoryList();
        historyListLivaData.observe((LifecycleOwner) view, new Observer<BrowsingHistoryResponse>() {
            @Override
            public void onChanged(BrowsingHistoryResponse response) {
                if (!response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    //response正常 未出错
                    view.showHistoryListOnUi(response.getHistoryDataList());
                }
            }
        });
    }

    /**
     * @param houseId 要添加到收藏的民宿ID
     * @return 是否成功
     * TODO 从View层向Model层传递添加收藏的请求
     */
    public MutableLiveData<Boolean> passRequestOfAddCollection(int houseId, LifecycleOwner owner) {
        if (!BuildConfig.isModule) {
            CollectionManagerService collectionManagerService = (CollectionManagerService) ARouter.getInstance().build("/collectionsView/CollectionManagerImpl").navigation();
            return collectionManagerService.addCollection(owner, houseId);
        } else {
            ToastUtils.toast("非集成模式下不能添加！");
            return new MutableLiveData<>(false);
        }
    }

    /**
     * @param houseId 要删除历史记录的民宿ID
     * @return 是否成功
     * TODO 从View层向Model层传递删除一项历史记录的请求
     */
    public MutableLiveData<IsSuccessfulResponse>  passRequestOfRemoveHistory(int houseId) {
        Log.d("remoteHistory", "passRequestOfRemoveHistory: " + houseId);
        return model.removeHistoryItem(houseId);
    }

    /**
     * @return 是否成功
     * TODO 从View层向Model层传递清空历史记录的请求
     */
    public boolean clearHistoryList() {
        boolean[] result = new boolean[]{false};
        MutableLiveData<IsSuccessfulResponse> clearSuccessLiveData = model.clearBrowsingHistoryList();
        clearSuccessLiveData.observe((LifecycleOwner) view, new Observer<IsSuccessfulResponse>() {
            @Override
            public void onChanged(IsSuccessfulResponse response) {
                if (!response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    //response返回正常
                    if (response.isSuccess()) {
                        result[0] = true;
                    }
                }
            }
        });
        return result[0];
    }
}
