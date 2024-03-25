package com.xupt3g.browsinghistoryview.model;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.browsinghistoryview.model.retrofit.BrowsingHistoryManageService;
import com.xupt3g.browsinghistoryview.model.retrofit.HistoryData;
import com.xupt3g.mylibrary1.implservice.BrowsedHistoryManagerService;
import com.xupt3g.mylibrary1.implservice.CollectionManagerService;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xupt3g.mylibrary1.PublicRetrofit;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.model.BrowsingHistoryRequest
 *
 * @author: shallew
 * @data: 2024/2/28 16:59
 * @about: TODO Model层的实现类 进行对浏览记录数据的请求
 */
@Route(path = "/browsingHistoryView/BrowsingHistoryRequest")
public class BrowsingHistoryRequest implements BrowsingHistoryManageImpl, BrowsedHistoryManagerService {
    private final BrowsingHistoryManageService browsingHistoryManageService;
    private List<HistoryData> historyDataList;
    private boolean isClearSuccessful;
    private boolean isRemoveSuccessful;
    private boolean isAddSuccessful;

    public BrowsingHistoryRequest() {
        //获取收藏的动态代理对象
        browsingHistoryManageService = (BrowsingHistoryManageService) PublicRetrofit.create(BrowsingHistoryManageService.class);
    }

    /**
     *
     * @return 返回历史记录集合
     * TODO 获取历史记录集合 本地方法
     */
    @Override
    public List<HistoryData> getBrowsingHistoryList() {
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录");
            return null;
        }
        historyDataList = new ArrayList<>();

        try {
            historyDataList.add(new HistoryData("2024/2/25"));
            historyDataList.add(new HistoryData("2024/2/26"));
            historyDataList.add(new HistoryData("2024/2/27"));
            historyDataList.add(new HistoryData("2024/2/28"));


            historyDataList.add(new HistoryData("2024/2/11"));
            historyDataList.add(new HistoryData("2024/2/12"));
            historyDataList.add(new HistoryData("2024/2/13"));
            historyDataList.add(new HistoryData("2024/2/14"));

            historyDataList.add(new HistoryData("2023/12/12"));
            historyDataList.add(new HistoryData("2023/12/13"));
            historyDataList.add(new HistoryData("2023/12/14"));

            historyDataList.add(new HistoryData("2021/12/29"));
            historyDataList.add(new HistoryData("2021/12/30"));
        } catch (ParseException e) {
            XToastUtils.error("时间转化错误！");
        }

        return historyDataList;

//        browsingHistoryManageService.getBrowsingHistoryList(LoginStatusData.getUserToken().getValue())
//                .enqueue(new Callback<BrowsingHistoryResponse>() {
//                    @Override
//                    public void onResponse(Call<BrowsingHistoryResponse> call, Response<BrowsingHistoryResponse> response) {
//                        BrowsingHistoryResponse body = response.body();
//                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
//                            historyDataList = body.getHistoryDataList();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<BrowsingHistoryResponse> call, Throwable t) {
//                        ToastUtils.toast("网络请求失败！");
//                    }
//                });
//        return historyDataList;
    }

    /**
     *
     * @return 是否成功清空历史记录
     * TODO 清空历史记录 本地方法
     */
    @Override
    public boolean clearBrowsingHistoryList() {
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录");
            return false;
        }

        return true;

//        browsingHistoryManageService.clearBrowsingHistoryList(LoginStatusData.getUserToken().getValue())
//                .enqueue(new Callback<IsSuccessfulResponse>() {
//                    @Override
//                    public void onResponse(Call<IsSuccessfulResponse> call, Response<IsSuccessfulResponse> response) {
//                        IsSuccessfulResponse body = response.body();
//                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
//                            isClearSuccessful = body.isSuccess();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<IsSuccessfulResponse> call, Throwable t) {
//                        isClearSuccessful = false;
//                        ToastUtils.toast("网络请求失败！");
//                    }
//                });

//        if (isClearSuccessful && !BuildConfig.isModule) {
//            //如果清空成功且集成模式
//            //通知PersonalManagement页面修改浏览历史数量
//            EventBus.getDefault().post(BrowsedHistoryManagerService.BROWSED_HISTORY_HAS_CHANGED);
//        }
//
//        return isClearSuccessful;
    }

    /**
     *
     * @param houseId 请求删除的民宿Id
     * @return 是否成功移除历史记录子项
     * TODO 移除历史记录子项 本地方法
     */
    @Override
    public boolean removeHistoryItem(int houseId) {
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录");
            return false;
        }

        return true;

//        browsingHistoryManageService.removeHistoryFromList(LoginStatusData.getUserToken().getValue(), houseId)
//                .enqueue(new Callback<IsSuccessfulResponse>() {
//                    @Override
//                    public void onResponse(Call<IsSuccessfulResponse> call, Response<IsSuccessfulResponse> response) {
//                        IsSuccessfulResponse body = response.body();
//                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
//                            isRemoveSuccessful = body.isSuccess();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<IsSuccessfulResponse> call, Throwable t) {
//                        ToastUtils.toast("网络请求失败！");
//                        isRemoveSuccessful = false;
//                    }
//                });
//        if (isRemoveSuccessful && !BuildConfig.isModule) {
//            //如果移除成功且集成模式
//            //通知PersonalManagement页面修改浏览历史数量
//            EventBus.getDefault().post(BrowsedHistoryManagerService.BROWSED_HISTORY_HAS_CHANGED);
//        }
//
//        return isRemoveSuccessful;
    }

    /**
     *
     * @param houseId 请求添加到收藏的民宿Id
     * @return 是否成功将民宿添加到收藏
     * TODO 将民宿添加到收藏
     */
    @Override
    public boolean addToCollections(int houseId) {
        if (!BuildConfig.isModule) {
            CollectionManagerService collectionManagerService = (CollectionManagerService) ARouter.getInstance().build("/collectionsView/CollectionManagerImpl").navigation();
            return collectionManagerService.addCollection(houseId);
        } else {
            ToastUtils.toast("非集成模式下不能添加！");
            return false;
        }
    }

    /**
     * TODO 暴露接口方法 获取浏览历史数据的数量
     */
    @Override
    public int getBrowsedHistoryCount() {
        List<HistoryData> list = getBrowsingHistoryList();
        if (list != null) {
            return list.size();
        } else {
            return -1;
        }
    }

    /**
     *
     * @param houseId 要添加的民宿Id
     * @return 是否操作成功
     * TODO 暴露接口方法 将民宿添加到浏览历史列表
     * 该接口在本地不会直接使用，所以直接在重写的该方法中实现网络请求
     */
    @Override
    public boolean addBrowsedHistory(int houseId) {
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            //如果用户未登录 不能添加
            ToastUtils.toast("尚未登录！");
            return false;
        }

        return true;

//        browsingHistoryManageService.addHistoryToList(LoginStatusData.getUserToken().getValue(), houseId, System.currentTimeMillis())
//                .enqueue(new Callback<IsSuccessfulResponse>() {
//                    @Override
//                    public void onResponse(Call<IsSuccessfulResponse> call, Response<IsSuccessfulResponse> response) {
//                        IsSuccessfulResponse body = response.body();
//                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
//                            isAddSuccessful = body.isSuccess();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<IsSuccessfulResponse> call, Throwable t) {
//                        ToastUtils.toast("网络请求失败！");
//                        isAddSuccessful = false;
//                    }
//                });
//        if (isAddSuccessful && !BuildConfig.isModule) {
//            //如果移除成功且集成模式
//            //通知PersonalManagement页面修改浏览历史数量
//            EventBus.getDefault().post(BrowsedHistoryManagerService.BROWSED_HISTORY_HAS_CHANGED);
//        }
//
//        return isAddSuccessful;
    }

    @Override
    public void init(Context context) {

    }
}
