package com.xupt3g.loginregistrationview.model.retrofit;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.loginregistrationview.model.retrofit.Data
 *
 * @author: shallew
 * @data:2024/1/26 23:15
 * @about: TODO 接收JWT令牌信息
 */
public class Data {
    private String accessToken;// jwt令牌
    private long accessExpire;// token过期时间
    private long refreshAfter;// 置换token的时间

    public String getAccessToken() {
        return accessToken;
    }

    public long getAccessExpire() {
        return accessExpire;
    }

    public long getRefreshAfter() {
        return refreshAfter;
    }

    @Override
    public String toString() {
        return "{" +
                "\"accessToken\":\'" + accessToken + "\'" +
                ", \"accessExpire\":" + accessExpire +
                ", \"refreshAfter\":" + refreshAfter +
                '}';
    }
}
