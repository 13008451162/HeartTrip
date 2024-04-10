package com.xupt3g.homepageview.presenter;

import android.widget.TextView;

import com.xupt3g.homepageview.model.SearchedLocationData;
import com.xupt3g.homepageview.view.BaseView;

import io.reactivex.rxjava3.core.Observable;

/**
 * 项目名: HeartTrip
 * 文件名: CityInfoContract
 *
 * @author: lukecc0
 * @data:2024/3/17 下午11:22
 * @about: TODO
 */

public interface LocationInfoContract {
    /**
     * 管理Presenter与Model的关系
     *
     * @author lukecc0
     * @date 2024/03/18
     */
    interface Presenter<T> extends BasePresenter {
        /**
         * 获取指定位置的搜索信息
         *
         * @param position 需要搜索的位置
         */
        Observable<T> getLocationData(String position);

        /**
         * 通过输入流处理搜索过程和结构
         *
         * @param sequenceObservable
         */
        void locationSearch(Observable<CharSequence> sequenceObservable);

    }


    /**
     * 管理Presenter与View的关系
     *
     * @author lukecc0
     * @date 2024/03/18
     */
    interface LocationView extends BaseView<Presenter> {

        /**
         * 设置搜索展示的Recycler
         *
         * @param locationDataList 搜索展示的数据
         * @return {@link Observable}<{@link TextView}>
         */
        void searchRecycler(SearchedLocationData locationDataList);

        /**
         * 监听城市选择界面的点击事件
         */
        void returnClickData();


        /**
         * 用于选择历史位置和位置关键词返回事件
         */
        void returnHistoryClickData();
    }
}
