package com.xupt3g.personalmanagementview.model.retrofit;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.personalmanagementview.model.retrofit.AccountInfoResponse
 *
 * @author: shallew
 * @data: 2024/2/11 0:08
 * @about: TODO 账号信息Response
 */
public class AccountInfoResponse {
    private int code;
    private String msg;
    private UserInfo userInfo;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
    public AccountInfoResponse(AccountInfoResponse response) {
        this.code = response.getCode();
        this.msg = response.getMsg();
        this.userInfo = new UserInfo();
        this.userInfo.setId(response.getUserInfo().getId());
        this.userInfo.setAvatar(response.getUserInfo().getAvatar());
        this.userInfo.setNickname(response.getUserInfo().getNickname());
        this.userInfo.setMobile(response.getUserInfo().getMobile());
        this.userInfo.setSex(response.getUserInfo().getSex());
        this.userInfo.setInfo(response.getUserInfo().getInfo());

    }

    public AccountInfoResponse(int code, String msg, UserInfo userInfo) {
        this.code = code;
        this.msg = msg;
        this.userInfo = userInfo;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
