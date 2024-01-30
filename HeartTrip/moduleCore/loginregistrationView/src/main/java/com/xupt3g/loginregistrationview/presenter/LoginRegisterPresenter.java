package com.xupt3g.loginregistrationview.presenter;

import com.xupt3g.loginregistrationview.model.LoginRegisterImpl;
import com.xupt3g.loginregistrationview.model.LoginRegisterRequest;
import com.xupt3g.loginregistrationview.model.retrofit.JWTResponse;
import com.xupt3g.loginregistrationview.view.LoginRegisterViewImpl;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.loginregistrationview.presenter.LoginPresenter
 *
 * @author: shallew
 * @data: 2024/1/29 23:39
 * @about: TODO 登录的Presenter层
 */
public class LoginRegisterPresenter {
    private final LoginRegisterViewImpl loginView;
    private final LoginRegisterImpl loginCheck;

    public LoginRegisterPresenter(LoginRegisterViewImpl loginView) {
        this.loginView = loginView;
        this.loginCheck = new LoginRegisterRequest();
    }

    /**
     *
     * @param mobile 手机号码
     * @param password 密码
     * TODO 从View层接收用户输入，并调用Model层的方法验证登录
     */
    public void login(String mobile, String password) {
        loginView.showProgress();//开始加载，等待动画显示
        JWTResponse jwtResponse = loginCheck.loginCheck(mobile, password);
        if (jwtResponse != null) {
            //登录成功
            //加载完成，隐藏等待动画
            loginView.hideProgress();
            //登录成功，跳转至HomepageView
            loginView.loginSuccess(jwtResponse.getData().getAccessToken());
        } else {
            //登录失败
            //加载完成，隐藏等待动画
            loginView.hideProgress();
            //登录失败，留在当前页面
            loginView.loginFailed();
        }
    }
}
