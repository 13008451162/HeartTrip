package com.xupt3g.houseinfoview.model;

import com.xupt3g.houseinfoview.model.RecommendHouse;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.model.RecommendHouseResponse
 *
 * @author: shallew
 * @data: 2024/3/11 22:21
 * @about: TODO 推荐民宿的response类型
 */
public class RecommendHouseResponse {
    private int code;
    private String msg;
    private List<RecommendHouse> list;

    public RecommendHouseResponse(List<RecommendHouse> recommendHouse) {
        this.code = 200;
        this.msg = "OK";
        this.list = recommendHouse;
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
        return list;
    }
}
