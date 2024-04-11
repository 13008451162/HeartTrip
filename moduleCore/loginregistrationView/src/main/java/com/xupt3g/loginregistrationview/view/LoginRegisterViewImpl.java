package com.xupt3g.loginregistrationview.view;

import com.xupt3g.loginregistrationview.model.retrofit.JwtData;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.loginregistrationview.view.LoginRegisterViewImpl
 *
 * @author: shallew
 * @data: 2024/1/29 0:07
 * @about: TODO View层的接口 封装View的方法
 */
public interface LoginRegisterViewImpl {
    void showProgress();//显示等待过程动画
    void hideProgress();//隐藏等待动画
    void loginSuccess(JwtData jwtData);//登录成功提示，跳转至HomepageView token表示用户登录的token
    void loginFailed();//登录失败提示


}
