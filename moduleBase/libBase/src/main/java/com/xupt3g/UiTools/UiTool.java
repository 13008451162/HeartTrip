package com.xupt3g.UiTools;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.gyf.immersionbar.ImmersionBar;

/**
 *   项目名: HeartTrip
 *   文件名: UiTools.UiTools
 *   @author: lukecc0
 *   @data:2024/1/24 上午3:04
 *   @about: TODO 管理全局的Ui状态，例如沉浸式布局
*/

public class UiTool {
    /**
     * @param activity
     * @param isDarkFont  true-黑，false-白
     *  TODO 调用沉浸式状态栏
     */


    public static void setImmersionBar(@NonNull Activity activity, boolean isDarkFont){
        ImmersionBar.with(activity)
                .transparentStatusBar()
                .statusBarDarkFont(isDarkFont)
                .init();

    }
}
