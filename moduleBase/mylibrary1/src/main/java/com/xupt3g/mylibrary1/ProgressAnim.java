package com.xupt3g.mylibrary1;

import android.content.Context;
import android.view.Window;

import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.mylibrary1.ProgressAnim
 *
 * @author: shallew
 * @data: 2024/2/12 0:17
 * @about: TODO 加载动画的显示和隐藏
 */
public class ProgressAnim {
    private static MiniLoadingDialog mLoadingDialog;

    public static void showProgress(Context context) {
        mLoadingDialog = WidgetUtils.getMiniLoadingDialog(context);
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
    public static void hideProgress() {
        if (mLoadingDialog.isLoading()) {
            //取消动画
            mLoadingDialog.dismiss();
            mLoadingDialog.recycle();
        }
    }
}
