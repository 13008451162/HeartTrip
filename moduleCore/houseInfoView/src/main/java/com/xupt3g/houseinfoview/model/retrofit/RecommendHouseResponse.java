package com.xupt3g.houseinfoview.model.retrofit;

import com.baidu.location.c.d;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.model.retrofit.RecommendHouseResponse
 *
 * @author: shallew
 * @data: 2024/3/11 22:21
 * @about: TODO 推荐民宿的response类型
 */
@NoArgsConstructor
@Data
public class RecommendHouseResponse {

    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;

    public RecommendHouseResponse(List<RecommendHouse> recommendHouse) {
        this.code = 200;
        this.msg = "OK";
        this.data.list = recommendHouse;
    }

    public RecommendHouseResponse(String msg) {
        this.msg = msg;
    }

    public RecommendHouseResponse() {
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<RecommendHouse> getRecommendHouse() {
        return data.list;
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("list")
        private List<RecommendHouse> list;

        @Override
        public String toString() {
            return "{" +
                    "\"list\":" + list +
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
