package com.xupt3g.hearttrip;

import static com.xuexiang.xutil.tip.ToastUtils.toast;

import android.app.Application;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;

import com.xuexiang.xui.XUI;

import java.util.List;

/**
 * @author lukecc0
 * @date 2024/01/24
 */
public class HeartTripApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        onCreateARouter();
        onCreateXUI();

    }



    /**
     * 初始化ARouter设置
     */
    private void onCreateARouter() {
        if (isModule()) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
    }


    /**
     * 初始化XUi框架
     */
    private void onCreateXUI(){
        XUI.init(this);
        XUI.debug(true);
    }

    private boolean isModule() {
        return !BuildConfig.isModule;
    }


}
