package com.xupt3g.browsinghistoryview.model.retrofit;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.model.retrofit.BrowsingHistoryResponse
 *
 * @author: shallew
 * @data: 2024/2/28 16:14
 * @about: TODO 浏览历史集合的Response
 */
public class BrowsingHistoryResponse {
    private int code;
    private String msg;
    private List<HistoryData> historyList;

    public BrowsingHistoryResponse(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<HistoryData> getHistoryDataList() {
        return historyList;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\'" + msg + "\'" +
                ", \"historyList\":" + historyList +
                '}';
    }
}
