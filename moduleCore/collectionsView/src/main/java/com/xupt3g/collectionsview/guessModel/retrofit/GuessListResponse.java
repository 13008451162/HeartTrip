package com.xupt3g.collectionsview.guessModel.retrofit;

import com.xupt3g.collectionsview.guessModel.retrofit.GuessData;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.guessModel.retrofit.GuessListResponse
 *
 * @author: shallew
 * @data:2024/2/20 0:00
 * @about: TODO
 */
public class GuessListResponse {
    private int code;
    private String msg;
    private List<GuessData> list;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<GuessData> getList() {
        return list;
    }
}
