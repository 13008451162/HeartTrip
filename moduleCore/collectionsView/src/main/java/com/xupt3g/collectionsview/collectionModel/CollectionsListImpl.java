package com.xupt3g.collectionsview.collectionModel;

import androidx.lifecycle.MutableLiveData;

import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionData;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionDataResponse;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionsListResponse;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.collectionModel.CollectionsListImpl
 *
 * @author: shallew
 * @data:2024/2/19 21:50
 * @about: TODO
 */
public interface CollectionsListImpl {

    /**
     *
     * @param houseId 民宿ID
     * @return 是否成功添加到收藏
     * TODO 添加收藏
     */
    MutableLiveData<CollectionDataResponse> addCollectionToList(int houseId);

    /**
     *
     * @param houseId 民宿ID
     * @return 是否成功移除收藏
     * TODO 移除收藏
     */
    MutableLiveData<IsSuccessfulResponse> removeCollectionFromList(int houseId);

    /**
     *
     * @return 收藏列表
     * TODO 获取收藏列表
     */
    MutableLiveData<CollectionsListResponse> getCollectionsList();
}
