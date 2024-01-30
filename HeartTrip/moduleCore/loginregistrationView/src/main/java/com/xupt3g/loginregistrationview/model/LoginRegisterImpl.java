package com.xupt3g.loginregistrationview.model;

import com.xupt3g.loginregistrationview.model.retrofit.JWTResponse;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.loginregistrationview.model.LoginRegisterImpl
 *
 * @author: shallew
 * @data:2024/1/26 23:21
 * @about: TODO 提供给Model层的接口
 */
public interface LoginRegisterImpl {
    /**
     *
     * @param mobile 用户输入的手机号码
     * @param password 密码
     * @return 是否成功验证登录 false账号或密码错误 true登录成功
     */
    JWTResponse loginCheck(String mobile, String password);

}
