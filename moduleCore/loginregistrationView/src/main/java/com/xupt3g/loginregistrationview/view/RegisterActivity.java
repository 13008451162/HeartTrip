package com.xupt3g.loginregistrationview.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.ImageButton;

import com.example.libbase.BuildConfig;
import com.xupt3g.loginregistrationview.model.retrofit.JwtData;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.google.android.material.snackbar.Snackbar;
import com.xuexiang.xui.utils.SnackbarUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.button.ButtonView;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;
import com.xuexiang.xui.widget.edittext.PasswordEditText;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.edittext.materialedittext.validation.RegexpValidator;
import com.xupt3g.UiTools.UiTool;
import com.xupt3g.loginregistrationview.R;
import com.xupt3g.loginregistrationview.presenter.LoginRegisterPresenter;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.loginregistrationview.view.RegisterActivity
 *
 * @author: shallew
 * @data: 2024/1/29 0:07
 * @about: TODO View层注册活动界面
 */
public class RegisterActivity extends AppCompatActivity implements LoginRegisterViewImpl {
    private MaterialEditText phoneNumberInput;
    private PasswordEditText passwordInput;
    private PasswordEditText passwordConfirmInput;
    private MiniLoadingDialog mLoadingDialog;
    private LoginRegisterPresenter registerPresenter;
    private ImageButton backButton;

    private ButtonView registerButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_register);

        //View实例化
        phoneNumberInput = (MaterialEditText) findViewById(R.id.phoneEdit);
        passwordInput = (PasswordEditText) findViewById(R.id.passwordEdit);
        passwordConfirmInput = (PasswordEditText) findViewById(R.id.passwordConfirmEdit);
        registerButton = (ButtonView) findViewById(R.id.registerBtn);
        backButton = (ImageButton) findViewById(R.id.back_btn);


        backButton.setOnClickListener(view -> {
            finish();
        });//返回键功能设置

        registerPresenter = new LoginRegisterPresenter(this);//Presenter层的实例

        //注册按钮点击监听（密码与确认密码是否相同，密码必须大于8位小于16位，执行注册网络请求）
        registerButton.setOnClickListener(view -> {
            if (passwordInput.length() < 8 || passwordInput.length() > 16) {
                //如果密码长度不符合8-16位
                passwordInput.setError("密码长度须满足8到16位");
                passwordInput.requestFocus();//获取焦点，显示Error
                return ;
            }
            if (!passwordInput.getText().toString().equals(passwordConfirmInput.getText().toString())) {
                //如果两次密码输入的不一样
                passwordConfirmInput.setError("两次密码输入不一样，请重新确认密码！");
                passwordConfirmInput.requestFocus();//获取焦点，显示Error
                return;
            }
            if (phoneNumberInput.length() == 11
                    && passwordInput.length() >= 8 && passwordInput.length()<=16
                    && passwordInput.getText().toString().equals(passwordConfirmInput.getText().toString())) {
                lightTheButton();
                //满足注册条件 向服务器请求Token
                registerPresenter.loginOrRegister(phoneNumberInput.getText().toString(),
                        passwordInput.getText().toString(), LoginRegisterPresenter.OPTION_REGISTER);
            }
        });

        phoneNumberInput.setAutoValidate(true);//开启自动校验
        phoneNumberInput.setShowClearButton(true);
        phoneNumberInput.addValidator(new RegexpValidator("请输入中国大陆（+86）的11位手机号", "^1(3[0-9]|5[0-3,5-9]|7[1-3,5-8]|8[0-9])\\d{8}$"));
        //手机号码EditText动态监听（点亮按钮）
        phoneNumberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lightTheButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        passwordInput.setIsAsteriskStyle(true);

        //输入密码EditText的动态监听（点亮注册按钮，密码不能为空，密码只能包含数字字母和特殊字符号）
        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    //密码清空了
                    passwordInput.setError("密码不能为空");
                    return ;
                }
                String regex = "^[0-9a-zA-Z!@#$%^&*()_+~\\-=/\\[\\]{}|:;\"'<>,.?]+$";
                if (!Pattern.matches(regex, charSequence)) {
                    passwordInput.setError("请检查密码，密码只能包含数字、大小写字母和一些特殊符号");
                    return ;
                }
                lightTheButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //确认密码EditText的动态监听（点亮注册按钮，确认密码只能包含数字字母和特殊字符号）
        passwordConfirmInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String regex = "^[0-9a-zA-Z!@#$%^&*()_+~\\-=/\\[\\]{}|:;\"'<>,.?]+$";
                if (charSequence.length() > 0 &&!Pattern.matches(regex, charSequence)) {
                    passwordConfirmInput.setError("请检查密码，密码只能包含数字、大小写字母和一些特殊符号");
                    return ;
                }
                lightTheButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        UiTool.setImmersionBar(this, false);//沉浸式状态栏
    }

    /**
     * TODO 显示加载动画
     */
    @Override
    public void showProgress() {
        mLoadingDialog = WidgetUtils.getMiniLoadingDialog(RegisterActivity.this);
        mLoadingDialog.setCancelable(false);//设置不可点击取消
        Window window = mLoadingDialog.getWindow();
        if (window != null) {
            //设置dialog周围activity背景的透明度，[0f,1f]，0全透明，1不透明黑
            window.setDimAmount(0.4f);
        }
        mLoadingDialog.show();
    }

    /**
     * TODO 隐藏加载动画
     */
    @Override
    public void hideProgress() {
        if (mLoadingDialog.isLoading()) {
            //取消动画
            mLoadingDialog.dismiss();
            mLoadingDialog.recycle();
        }
    }

    /**
     * TODO 注册成功响应并跳转至HomepageView
     */
    @Override
    public void loginSuccess(JwtData data) {
        //创建线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                2,
                2,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        //线程池提交任务
        pool.submit(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("loggedInState", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                //记录登陆成功
                edit.putBoolean("isLoggedIn", true);
                //记录登录密钥
                edit.putString("userToken", data.getAccessToken());
                edit.apply();
                Log.d("rememberLoggedInState", "run: 记忆登录成功");

            }
        });
        //销毁线程池
        pool.shutdown();

        SnackbarUtils.Custom(getWindow().getDecorView(), "注册成功！请妥善保管您的密码！", 1500)
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
                            //保存登录信息 LiveData赋值
                            LoginStatusData.getLoginStatus().setValue(true); //设置登录状态为 已登录
                            LoginStatusData.getUserToken().setValue(data.getAccessToken()); //保存用户token
                            Intent intent = getIntent();
                            //注册成功就像intent中放入一个boolean
                            intent.putExtra("IsSuccessful", true);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                })
                .gravityFrameLayout(Gravity.TOP)
                .show();
    }

    /**
     * TODO 注册失败，向用户提示
     */
    @Override
    public void loginFailed() {
        SnackbarUtils.Custom(getWindow().getDecorView(), "注册失败！请检查手机号输入错误或者手机号已经注册！", 1500)
                .warning()
                .radius(25.5f)
                .messageCenter()
                .gravityFrameLayout(Gravity.TOP).show();
    }

    public void lightTheButton() {
        if (passwordInput.length() >= 8 && passwordInput.length() <= 16
                && passwordConfirmInput.length() >= 8 && passwordConfirmInput.length() <= 16
                && phoneNumberInput.getText().length() == 11) {
            //如果手机号11位且两个密码不为空 点亮注册按钮
            registerButton.setSolidColor(Color.parseColor("#2BCFB8"));
            registerButton.setSelectedSolidColor(Color.parseColor("#05BCA2"));
        } else {
            registerButton.setSolidColor(Color.parseColor("#9DA7B0"));
            registerButton.setSelectedSolidColor(Color.parseColor("#87919A"));
        }
    }
}