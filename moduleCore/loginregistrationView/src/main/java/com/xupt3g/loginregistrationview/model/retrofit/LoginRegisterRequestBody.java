package com.xupt3g.loginregistrationview.model.retrofit;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.loginregistrationview.model.retrofit.LoginRegisterRequestBody
 *
 * @author: shallew
 * @data: 2024/3/25 20:37
 * @about: TODO 登录注册请求体
 */
public class LoginRegisterRequestBody {
    private String mobile;
    private String password;

    public LoginRegisterRequestBody(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

}
