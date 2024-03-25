package com.xupt3g.homepageview.presenter;

import com.xupt3g.homepageview.model.RecommendHomeData;
import com.xupt3g.homepageview.view.BaseView;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 项目名: HeartTrip
 * 文件名: RecommendInfoContrach
 *
 * @author: lukecc0
 * @data:2024/3/21 下午7:41
 * @about: TODO 管理网络接口和首页View
 */

public interface RecommendInfoContrach {

    interface Presenter<T> extends BasePresenter {
        /**
         * 从网络请求信息
         *
         * @param page
         */
        void getHomeData(int page);
    }

    interface HomeView extends BaseView<Presenter> {
        /**
         * 在Recycle中加载房屋信息
         *
         * @param listDTO
         */
        void revealRecycler(RecommendHomeData listDTO);

        /**
         * 用于监听底部滑动，滑动到底部继续加载数据
         */
        void listenForBottomSliding();
    }
}
