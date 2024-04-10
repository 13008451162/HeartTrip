package com.xupt3g.collectionsview.guessModel.retrofit;

import com.google.gson.annotations.SerializedName;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionData;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.guessModel.retrofit.GuessListResponse
 *
 * @author: shallew
 * @data:2024/2/20 0:00
 * @about: TODO
 */
@NoArgsConstructor
@Data
public class GuessListResponse {
    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<GuessData> getList() {
        return data.list;
    }

    public GuessListResponse(String msg) {
        this.msg = msg;
    }

    public GuessListResponse() {
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("list")
        private List<GuessData> list;
    }
}
