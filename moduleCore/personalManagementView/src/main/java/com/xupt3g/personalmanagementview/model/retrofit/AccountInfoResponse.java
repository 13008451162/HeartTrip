package com.xupt3g.personalmanagementview.model.retrofit;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.personalmanagementview.model.retrofit.AccountInfoResponse
 *
 * @author: shallew
 * @data: 2024/2/11 0:08
 * @about: TODO 账号信息Response
 */
@NoArgsConstructor
@Data
public class AccountInfoResponse {


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

    public UserInfo getUserInfo() {
        return data.userInfo;
    }
    public AccountInfoResponse(AccountInfoResponse response) {
        this.code = response.getCode();
        this.msg = response.getMsg();
        this.data = new DataDTO();
        this.data.userInfo = response.getUserInfo();

    }

    public AccountInfoResponse(String msg) {
        this.msg = msg;
    }

    public AccountInfoResponse(int code, String msg, UserInfo userInfo) {
        this.code = code;
        this.msg = msg;
        this.data = new DataDTO();
        this.data.userInfo = userInfo;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.data.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\'" + msg + "\'" +
                ", \"data\":" + data.userInfo +
                '}';
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("userInfo")
        private UserInfo userInfo;
    }
}
