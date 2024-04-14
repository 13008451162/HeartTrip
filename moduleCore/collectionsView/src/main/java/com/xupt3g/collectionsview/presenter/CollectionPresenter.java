package com.xupt3g.collectionsview.presenter;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.collectionsview.collectionModel.CollectionManagerImpl;
import com.xupt3g.collectionsview.collectionModel.CollectionsListImpl;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionDataResponse;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionsListResponse;
import com.xupt3g.collectionsview.guessModel.GuessListGetImpl;
import com.xupt3g.collectionsview.guessModel.GuessListGetRequest;
import com.xupt3g.collectionsview.guessModel.retrofit.GuessListResponse;
import com.xupt3g.collectionsview.view.CollectionsGuessManagerImpl;
import com.xupt3g.mylibrary1.PublicRetrofit;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;

import java.util.ArrayList;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.presenter.ThePresenter
 *
 * @author: shallew
 * @data:2024/2/19 23:31
 * @about: TODO
 */
public class CollectionPresenter {
    /**
     * Model层收藏暴露接口对象
     */
    private final CollectionsListImpl collectionsListImpl;
    /**
     * Model层猜你喜欢暴露接口对象
     */
    private final GuessListGetImpl guessListImpl;
    /**
     * View层数据集合展示、收藏添加和删除 接口对象
     */
    private final CollectionsGuessManagerImpl collectionsGuessManager;


    public CollectionPresenter(CollectionsGuessManagerImpl collectionsShow) {
        this.collectionsListImpl = new CollectionManagerImpl();
        this.guessListImpl = new GuessListGetRequest();
        this.collectionsGuessManager = collectionsShow;
    }

    /**
     * TODO 从CollectionsListImpl中获取收藏集合，显示在View上
     */
    public void showCollections() {
        MutableLiveData<CollectionsListResponse> collectionsListLiveData = collectionsListImpl.getCollectionsList();
        collectionsListLiveData.observe((LifecycleOwner) collectionsGuessManager, new Observer<CollectionsListResponse>() {
            @Override
            public void onChanged(CollectionsListResponse response) {
                if (response != null && !response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    collectionsGuessManager.showCollectionsList(response.getList());
                } else {
                    collectionsGuessManager.showCollectionsList(new ArrayList<>());
                }
            }
        });
    }

    /**
     * TODO 从GuessListGetImpl中获取猜你喜欢集合，并显示在View上
     */
    public void showGuessList() {
        //无需登录
        MutableLiveData<GuessListResponse> guessListLiveData = guessListImpl.getGuessList();
        guessListLiveData.observe((LifecycleOwner) collectionsGuessManager, new Observer<GuessListResponse>() {
            @Override
            public void onChanged(GuessListResponse response) {
                if (response != null && !response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    if (response.getList() != null) {
                        collectionsGuessManager.showGuessList(response.getList());
                    } else {
                        XToastUtils.error("返回的猜你喜欢列表为空");
                    }
                }
            }
        });

    }

    /**
     * @param houseId 民宿ID
     * @return 是否成功添加收藏
     * TODO 在View层调用该方法，传入民宿ID，对该民宿进行添加到收藏列表的操作
     */
    public MutableLiveData<CollectionDataResponse> addCollection(int houseId) {
        return collectionsListImpl.addCollectionToList(houseId);
    }

    /**
     * @param houseId 民宿ID
     * @return 是否成功移除收藏
     */
    public MutableLiveData<IsSuccessfulResponse> removeCollection(int houseId) {
        //在收藏页面可以进行删除操作
        return collectionsListImpl.removeCollectionFromList(houseId);
    }
}
