package com.xupt3g.loginregistrationview.presenter;


import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.xupt3g.loginregistrationview.model.LoginRegisterImpl;
import com.xupt3g.loginregistrationview.model.LoginRegisterRequest;
import com.xupt3g.loginregistrationview.model.retrofit.JWTResponse;
import com.xupt3g.loginregistrationview.view.LoginRegisterViewImpl;
import com.xupt3g.mylibrary1.PublicRetrofit;

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
    public static final int OPTION_LOGIN = 0;
    public static final int OPTION_REGISTER = 1;

    public LoginRegisterPresenter(LoginRegisterViewImpl loginView) {
        this.loginView = loginView;
        this.loginCheck = new LoginRegisterRequest();
    }

    /**
     * @param mobile   手机号码
     * @param password 密码
     *                 TODO 从View层接收用户输入，并调用Model层的方法验证登录
     */
    public void loginOrRegister(String mobile, String password, int option) {
        loginView.showProgress();//开始加载，等待动画显示
        MutableLiveData<JWTResponse> jwtResponseLiveData;
        if (option == OPTION_LOGIN) {
            jwtResponseLiveData = loginCheck.loginCheck(mobile, password);
        } else {
            jwtResponseLiveData = loginCheck.registerNewAccont(mobile, password);
        }
        jwtResponseLiveData.observe((LifecycleOwner) loginView, jwtResponse -> {
            if (!PublicRetrofit.getErrorMsg().equals(jwtResponse.getMsg())) {
                Log.d("UserTokenGet", "login: " + jwtResponse.toString());
                //登录成功
                //加载完成，隐藏等待动画
                loginView.hideProgress();
                //登录成功，跳转至HomepageView
                loginView.loginSuccess(jwtResponse.getData());
            } else {
                //登录失败
                //加载完成，隐藏等待动画
                loginView.hideProgress();
                //登录失败，留在当前页面
                loginView.loginFailed();
            }
        });
    }

}
