package com.xupt3g.searchresultview.presenter;

import android.database.Observable;

import com.xupt3g.searchresultview.View.BaseView;
import com.xupt3g.searchresultview.model.CountyData;
import com.xupt3g.searchresultview.model.HousingData;
import com.xupt3g.searchresultview.model.net.KeyWordReq;

import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * 项目名: HeartTrip
 * 文件名: CountyInfoContract
 *
 * @author: lukecc0
 * @data:2024/4/12 下午7:37
 * @about: TODO 房屋搜索Activity管理器
 */

public interface SearchInfoContract {
    /**
     * @author lukecc0
     * @date 2024/04/12
     * TODO 用于管理下级行政区搜索展示
     */
    interface CountyInfoPresenter<T> extends BasePresent {
        /**
         * 通过指定城市搜索下级行政区
         *
         * @param city
         * @return {@link Observable}<{@link T}>
         */
        io.reactivex.rxjava3.core.Observable<T> getCountyData(String city);

        /**
         * 设置城市的下级行政区
         *
         * @param city
         */
        void setDropDownMenu(String city);
    }

    interface houseInfoPresenter<T> extends BasePresent {

        io.reactivex.rxjava3.core.Observable<T> getHousedata(KeyWordReq keyWordReq);


        void setHouseAdapter(io.reactivex.rxjava3.core.Observable<CharSequence> editObservable);
    }

    /**
     * @author lukecc0
     * @date 2024/04/12
     * TODO 管理搜索页面的视图
     */
    interface SearchView extends BaseView {
        /**
         * 初始化下拉选项试图
         */
        void initDropDownMenu(CountyData.DistrictsDTO districtsDTO);

        /**
         * 更新搜索内容
         *
         * @param housingData
         */
        void sethouseView(HousingData housingData);
    }
}
