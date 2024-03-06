package com.xupt3g.loginregistrationview.view;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.ImageButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xupt3g.mylibrary1.ProgressAnim;
import com.google.android.material.snackbar.Snackbar;
import com.xuexiang.xui.utils.SnackbarUtils;
import com.xuexiang.xui.widget.button.ButtonView;
import com.xuexiang.xui.widget.edittext.PasswordEditText;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.edittext.materialedittext.validation.RegexpValidator;
import com.xupt3g.UiTools.UiTool;
import com.xupt3g.loginregistrationview.R;
import com.xupt3g.loginregistrationview.presenter.LoginRegisterPresenter;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.loginregistrationview.view.LoginActivity
 *
 * @author: shallew
 * @data: 2024/1/29 0:07
 * @about: TODO View层登录活动界面
 */

@Route(path = "/loginRegistration/LoginActivity")
public class LoginActivity extends AppCompatActivity implements LoginRegisterViewImpl {


    private MaterialEditText phoneNumberInput;
    private PasswordEditText passwordInput;
    private ButtonView loginButton;
    private LoginRegisterPresenter loginPresenter;
    private ButtonView registerButton;
    private ImageButton backButton;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_login);

        //View实例化
        phoneNumberInput = (MaterialEditText) findViewById(R.id.phoneEdit);
        loginButton = (ButtonView) findViewById(R.id.loginBtn);
        passwordInput = (PasswordEditText) findViewById(R.id.passwordEdit);
        registerButton = (ButtonView) findViewById(R.id.registerBtn);
        backButton = (ImageButton) findViewById(R.id.back_btn);

        //返回按钮监听注册
        backButton.setOnClickListener(view -> {
            finish();
        });

        //注册按钮监听注册（跳转至注册页面）
        registerButton.setOnClickListener(view -> {
            //点击了注册按钮
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        //Presenter层的实例
        loginPresenter = new LoginRegisterPresenter(this);

        //登陆按钮的监听注册（解决按钮点击一次就恢复到原来style的情况，执行登陆验证网络请求）
        loginButton.setOnClickListener(view -> {
            if (Objects.requireNonNull(phoneNumberInput.getText()).length() == 11
                    && !passwordInput.getText().toString().trim().isEmpty()
                    && passwordInput.getText().length() >= 8 && passwordInput.getText().length() <= 16) {
                //11位手机号且密码不为空且密码8到16位 可登录
                loginButton.setSolidColor(Color.parseColor("#3E99D2"));
                loginButton.setSelectedSolidColor(Color.parseColor("#2488C6"));
                //暴力解决按钮点击一次就恢复到原来的style的情况 按钮颜色不变

                //进行登录验证
                loginPresenter.login(phoneNumberInput.getText().toString(), passwordInput.getText().toString());
            }
        });

        //开启自动校验
        phoneNumberInput.setAutoValidate(true);
        phoneNumberInput.setShowClearButton(true);
        phoneNumberInput.addValidator(new RegexpValidator("请输入中国大陆（+86）的11位手机号", "^1(3[0-9]|5[0-3,5-9]|7[1-3,5-8]|8[0-9])\\d{8}$"));
        //手机号码EditText的动态监听（点亮按钮）
        phoneNumberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //文本编辑框内容改变时调用
                lightTheButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        passwordInput.setIsAsteriskStyle(false);
        //密码EditText的动态监听（判断输入字符是否合法，点亮按钮，密码不能为空）
        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (Pattern.matches("[\u4E00-\u9FA5]", charSequence)) {
//                    //判断字符串中存在不存在中文字符
//
//                }^[0-9a-zA-Z!@#$%^&*()_+~\\-=/\\[\\]{}\\|:;\"\'<>,.?]+$
                String regex = "^[0-9a-zA-Z!@#$%^&*()_+~\\-=/\\[\\]{}|:;\"'<>,.?]+$";
                if (!Pattern.matches(regex, charSequence)) {
                    passwordInput.setError("请检查密码，密码只能包含数字、大小写字母和一些特殊符号");
                }
                if (charSequence.length() == 0) {
                    //密码清空了
                    passwordInput.setError("密码不能为空");
                }
                lightTheButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //沉浸式状态栏
        UiTool.setImmersionBar(this, false);


    }

    /**
     * TODO 显示加载动画
     */
    @Override
    public void showProgress() {

        ProgressAnim.showProgress(LoginActivity.this);
    }

    /**
     * TODO 隐藏加载动画
     */
    @Override
    public void hideProgress() {
        ProgressAnim.hideProgress();
    }

    /**
     * TODO 登录成功响应并跳转至HomepageView
     */
    @Override
    public void loginSuccess(String token) {
        SnackbarUtils.Short(getWindow().getDecorView(), "登录成功！欢迎使用随心游！")
                .confirm()
                .messageCenter()
                .radius(25.5f)
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onShown(Snackbar sb) {
                        // Snackbar显示时执行的操作
                    }

                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        // Snackbar消失时执行的操作
                        //跳转到Homepage模块
                        if (!BuildConfig.isModule) {
                            //保存登录信息 给LiveData loginStatus userToken赋值
                            LoginStatusData.getLoginStatus().setValue(true);//loginStatus设为true，更改登陆状态为 已登陆
                            LoginStatusData.getUserToken().setValue(token);
                            //集成模式开发下才能尝试跳转
                            ARouter.getInstance().build("/homepage/HomepageActivity")
                                    .navigation();
                        }
                    }
                })
                .gravityFrameLayout(Gravity.TOP)
                .show();
    }

    /**
     * TODO 登录失败，向用户提示
     */
    @Override
    public void loginFailed() {
        SnackbarUtils.Short(getWindow().getDecorView(), "登录失败！请检查手机号和密码！")
                .warning()
                .radius(25.5f)
                .messageCenter()
                .gravityFrameLayout(Gravity.TOP).show();
    }

    public void lightTheButton() {
        if (phoneNumberInput.length() == 11
                && !passwordInput.getText().toString().trim().isEmpty()
                && passwordInput.getText().length() >= 8 && passwordInput.getText().length() <= 16) {
            //达到11位手机号长度且密码不为空且密码8到16位 点亮登录按钮
            loginButton.setSolidColor(Color.parseColor("#3E99D2"));
            loginButton.setSelectedSolidColor(Color.parseColor("#2488C6"));
        } else {
            loginButton.setSolidColor(Color.parseColor("#9DA7B0"));
            loginButton.setSelectedSolidColor(Color.parseColor("#87919A"));
        }
    }

}