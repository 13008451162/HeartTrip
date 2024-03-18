package com.xupt3g.loginregistrationview;

import android.app.Application;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.XUI;


/**
 * 项目名: HeartTrip
 * 文件名: loginApp
 *
 * @author: lukecc0
 * @data: 2024/1/24 下午4:29
 * @about: TODO
 */

public class loginApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        isDebug();

    }

    /**
     * 组件模式下需要运行
     */
    private void isDebug(){
        // BuildConfig 来自于  com.example.libbase.BuildConfig;
        if (BuildConfig.isModule){
            onCreateXUI();
        }
    }

    /**
     * 初始化XUi框架
     */
    private void onCreateXUI(){
        XUI.init(this);
        XUI.debug(true);
    }

}
