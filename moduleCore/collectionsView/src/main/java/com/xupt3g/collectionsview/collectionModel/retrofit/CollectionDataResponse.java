package com.xupt3g.collectionsview.collectionModel.retrofit;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.collectionModel.retrofit.CollectionDataResponse
 *
 * @author: shallew
 * @data: 2024/2/21 2:03
 * @about: TODO
 */
@NoArgsConstructor
@Data
public class CollectionDataResponse {


    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;

    public CollectionDataResponse(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public CollectionData getCollection() {
        return data.homestay;
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("homestay")
        private CollectionData homestay;

        @Override
        public String toString() {
            return "{" +
                    "\"homestay\":" + homestay +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\'" + msg + "\'" +
                ", \"data\":" + data +
                '}';
    }
}
