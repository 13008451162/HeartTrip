package com.xupt3g.browsinghistoryview.model;

import com.xupt3g.browsinghistoryview.model.retrofit.HistoryData;

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
    List<HistoryData> getBrowsingHistoryList();

    /**
     *
     * @return 返回操作是否成功
     * TODO 请求清空浏览历史数据集合
     */
    boolean clearBrowsingHistoryList();

    /**
     *
     * @param houseId 请求删除的民宿Id
     * @return 返回操作是否成功
     * TODO 请求将指定子项从浏览历史集合中移除
     */
    boolean removeHistoryItem(int houseId);

    /**
     *
     * @param houseId 请求添加到收藏的民宿Id
     * @return 返回操作是否成功
     * TODO 请求将浏览记录子项添加到用户收藏
     */
    boolean addToCollections(int houseId);
}
