package com.xupt3g.browsinghistoryview.model.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.model.retrofit.BrowsingHistoryResponse
 *
 * @author: shallew
 * @data: 2024/2/28 16:14
 * @about: TODO 浏览历史集合的Response
 */
@NoArgsConstructor
@Data
public class BrowsingHistoryResponse {


    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;

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
        return data.historyList;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\'" + msg + "\'" +
                ", \"historyList\":" + data.historyList +
                '}';
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("historyList")
        private List<HistoryData> historyList;

    }
}
