package com.xupt3g.houseinfoview.presenter;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.utils.XToastUtils;
import com.xupt3g.houseinfoview.model.HouseInfoDataRequest;
import com.xupt3g.houseinfoview.model.HouseInfoGetImpl;
import com.xupt3g.houseinfoview.model.retrofit.HouseInfoBaseDataResponse;
import com.xupt3g.houseinfoview.model.retrofit.RecommendHouseResponse;
import com.xupt3g.houseinfoview.view.HouseInfoShowImpl;
import com.xupt3g.mylibrary1.PublicRetrofit;
import com.xupt3g.mylibrary1.implservice.CollectionManagerService;
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
        //可登录 直接调用该方法 交给Model层去判断是否已登录
        MutableLiveData<HouseInfoBaseDataResponse> houseInfoLiveData = model.getHouseInfoBaseData(houseId);
        houseInfoLiveData.observe((LifecycleOwner) view, new Observer<HouseInfoBaseDataResponse>() {
            @Override
            public void onChanged(HouseInfoBaseDataResponse response) {
                //返回可能为空
                if (response != null && !response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    if (response.getHouseInfoBaseData() != null) {
                        //成功获取到了数据
                        view.houseInfoBaseDataShowOnUi(response.getHouseInfoBaseData());
                    } else {
                        XToastUtils.error("数据获取异常");
                    }
                }
            }
        });
        recommendListShowOnUi();
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
            view.collectFailed(true);
        } else {
            //如果已登录
            if (collectionManagerService != null) {
                //已获取服务
                MutableLiveData<Boolean> booleanLiveData = collectionManagerService.addCollection((LifecycleOwner) view, houseId);
                booleanLiveData.observe((LifecycleOwner) view, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            view.collectSucceed();
                        } else {
                            view.collectFailed(false);
                        }
                    }
                });
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
            view.collectFailed(true);
        } else {
            //如果已登录
            if (collectionManagerService != null) {
                //已获取服务
                MutableLiveData<Boolean> booleanLiveData = collectionManagerService.removeCollection((LifecycleOwner) view, houseId);
                booleanLiveData.observe((LifecycleOwner) view, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            view.collectSucceed();
                        } else {
                            view.collectFailed(false);
                        }
                    }
                });
            } else {
                XToastUtils.error("当前处于组件开发模式，不可移除收藏");
            }
        }
    }

    public void recommendListShowOnUi() {
        //无需登录
        MutableLiveData<RecommendHouseResponse> recommendListLiveData = model.getRecommendHousesList();
        recommendListLiveData.observe((LifecycleOwner) view, new Observer<RecommendHouseResponse>() {
            @Override
            public void onChanged(RecommendHouseResponse response) {
                if (response != null && !response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    view.recommendHouseListShow(response.getRecommendHouse());
                }
            }
        });
    }
}
