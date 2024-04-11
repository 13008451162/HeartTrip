package com.xupt3g.loginregistrationview.model.retrofit;

import com.xupt3g.mylibrary1.PublicRetrofit;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.loginregistrationview.model.retrofit.JWTResponse
 *
 * @author: shallew
 * @data: 2024/1/26 23:12
 * @about: TODO 接收网络请求响应的JWT令牌
 */
public class JWTResponse {
    private int code;
    private String msg;
    private JwtData data;

    public JWTResponse() {
        this.msg = PublicRetrofit.getErrorMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public JwtData getData() {
        return data;
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
