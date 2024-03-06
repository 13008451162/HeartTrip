package com.xupt3g.mylibrary1;

import androidx.lifecycle.MutableLiveData;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.mylibrary1.LoginStatusData
 *
 * @author: shallew
 * @data:2024/2/7 1:06
 * @about: TODO 记录登陆数据的类 包含登录状态、登录的账户Token
 */
public class LoginStatusData {
    private static MutableLiveData<Boolean> loginStatus = new MutableLiveData<>(true);
    //true表示当前账号已登录
    //false表示当前账号未登录
    //默认状态为true 已登陆

    private static MutableLiveData<String> userToken = new MutableLiveData<>();
    //表示用户登陆后获取的Token
    //记得在登陆成功后将Token赋值给他
    //在退出登陆后恢复默认值null

    public LoginStatusData() {
//        this.loginStatus.setValue(true);
    }

    public static MutableLiveData<String> getUserToken() {
        return userToken;
    }

    public static MutableLiveData<Boolean> getLoginStatus() {

        return loginStatus;
    }

}
