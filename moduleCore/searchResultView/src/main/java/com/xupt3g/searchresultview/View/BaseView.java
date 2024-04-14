package com.xupt3g.searchresultview.View;

/**
 * 项目名: HeartTrip
 * 文件名: BaseView
 *
 * @author: lukecc0
 * @data:2024/4/12 下午7:11
 * @about: TODO View的基类
 */

public interface BaseView<T> {
    /**
     * 注入Presenter
     * @param present
     */
    void setPresent(T present);
}
