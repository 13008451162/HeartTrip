package com.xupt3g.browsinghistoryview.view;

import com.xupt3g.browsinghistoryview.model.retrofit.HistoryData;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.view.StickyItem
 *
 * @author: shallew
 * @data:2024/2/24 21:18
 * @about: TODO
 */
public class StickyItem {

    /**
     * 是否顶部粘连
     */
    private boolean mIsHeadSticky;
    /**
     * 顶部标题
     */
    private String mHeadTitle;

    /**
     * 历史记录子项
     */
    private HistoryData historyData;


    public StickyItem(String headTitle) {
        mHeadTitle = headTitle;
        mIsHeadSticky = true;
    }

    public StickyItem(HistoryData historyData) {
        this.historyData = historyData;
    }

    public boolean isHeadSticky() {
        return mIsHeadSticky;
    }

    public String getHeadTitle() {
        return mHeadTitle;
    }


    public HistoryData getHistoryData() {
        return historyData;
    }
}
