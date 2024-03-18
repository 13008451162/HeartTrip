package com.xupt3g.houseinfoview.presenter;

import androidx.media3.common.C;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xutil.data.BCDUtils;
import com.xupt3g.houseinfoview.model.HouseInfoBaseData;
import com.xupt3g.houseinfoview.model.HouseInfoDataRequest;
import com.xupt3g.houseinfoview.model.HouseInfoGetImpl;
import com.xupt3g.houseinfoview.view.HouseInfoShowImpl;
import com.xupt3g.mylibrary1.CollectionManagerService;
import com.xupt3g.mylibrary1.LoginStatusData;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.presenter.HouseInfoPresenter
 *
 * @author: shallew
 * @data: 2024/3/12 21:03
 * @about: TODO Presenter层的实现类
 */
public class HouseInfoPresenter {
    private HouseInfoGetImpl model;
    private HouseInfoShowImpl view;
    private CollectionManagerService collectionManagerService;

    public HouseInfoPresenter(HouseInfoShowImpl view) {
        this.view = view;
        this.model = new HouseInfoDataRequest();
        if (!BuildConfig.isModule) {
            //组件开发模式
            collectionManagerService = (CollectionManagerService) ARouter.getInstance().build("/collectionsView/CollectionManagerImpl")
                    .navigation();
        }
    }

    /**
     *
     * @param houseId 当前民宿的Id
     * TODO 网络请求获取当前民宿的基本信息数据 并将其显示在Ui上
     */
    public void getHouseInfoBaseData(int houseId) {
        //无需登录
        HouseInfoBaseData houseInfoBaseData = model.getHouseInfoBaseData(houseId);
        //返回可能为空
        if (houseInfoBaseData != null) {
            //成功获取到了数据
            view.houseInfoBaseDataShowOnUi(houseInfoBaseData);
        } else {
            XToastUtils.error("数据获取异常");
        }
    }

    /**
     *
     * @param houseId 民宿Id
     * TODO 将 该民宿添加到收藏列表
     */
    public void addToCollectionList(int houseId) {
        //需要登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            //如果未登录
            view.collectFailed();
        } else {
            //如果已登录
            if (collectionManagerService != null) {
                //已获取服务
                boolean b = collectionManagerService.addCollection(houseId);
                if (b) {
                    view.collectSucceed();
                } else {
                    view.collectFailed();
                }
            } else {
                XToastUtils.error("当前处于组件开发模式，不可添加收藏");
            }
        }
    }

    /**
     *
     * @param houseId 民宿Id
     * TODO 将该民宿移除收藏
     */
    public void removeFromCollectionList(int houseId) {
        //需要登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            //如果未登录
            view.collectFailed();
        } else {
            //如果已登录
            if (collectionManagerService != null) {
                //已获取服务
                boolean b = collectionManagerService.removeCollection(houseId);
                if (b) {
                    view.collectSucceed();
                } else {
                    view.collectFailed();
                }
            } else {
                XToastUtils.error("当前处于组件开发模式，不可移除收藏");
            }
        }
    }
}
