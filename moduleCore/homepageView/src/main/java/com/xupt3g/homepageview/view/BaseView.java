package com.xupt3g.homepageview.view;

/**
 * 项目名: HeartTrip
 * 文件名: BaseView
 *
 * @author: lukecc0
 * @data:2024/3/17 下午11:31
 * @about: TODO
 */

public interface BaseView<T> {

    /**
     * 为视图绑定presenter
     * @param presenter
     */
    void setPresenter(T presenter);
}
