package com.xupt3g.browsinghistoryview.view;

import com.xupt3g.browsinghistoryview.model.retrofit.HistoryData;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.view.BrowsingHistoryUiImpl
 *
 * @author: shallew
 * @data: 2024/2/28 19:00
 * @about: TODO View层对外暴露的接口
 */
public interface BrowsingHistoryUiImpl {
    /**
     *
     * @param historyDataList 从Model层进行网络请求获取的浏览历史集合
     * TODO 在Ui上展示获取到的历史数据
     */
    void showHistoryListOnUi(List<HistoryData> historyDataList);


}
