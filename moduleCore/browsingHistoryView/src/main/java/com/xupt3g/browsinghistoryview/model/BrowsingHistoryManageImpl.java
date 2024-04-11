package com.xupt3g.browsinghistoryview.model;

import androidx.lifecycle.MutableLiveData;

import com.xupt3g.browsinghistoryview.model.retrofit.BrowsingHistoryResponse;
import com.xupt3g.browsinghistoryview.model.retrofit.HistoryData;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.model.BrowsingHistoryManageImpl
 *
 * @author: shallew
 * @data: 2024/2/28 17:00
 * @about: TODO Model层对外接口
 */
public interface BrowsingHistoryManageImpl {

    /**
     *
     * @return 返回浏览历史集合
     * TODO 获取浏览历史集合数据 可能返回为空
     */
    MutableLiveData<BrowsingHistoryResponse> getBrowsingHistoryList();

    /**
     *
     * @return 返回操作是否成功
     * TODO 请求清空浏览历史数据集合
     */
    MutableLiveData<IsSuccessfulResponse> clearBrowsingHistoryList();

    /**
     *
     * @param houseId 请求删除的民宿Id
     * @return 返回操作是否成功
     * TODO 请求将指定子项从浏览历史集合中移除
     */
    MutableLiveData<IsSuccessfulResponse> removeHistoryItem(int houseId);

}
