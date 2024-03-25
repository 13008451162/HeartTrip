package com.xupt3g.collectionsview.collectionModel;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionData;
import com.xupt3g.mylibrary1.implservice.CollectionManagerService;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionsListService;
import com.xupt3g.mylibrary1.PublicRetrofit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Retrofit;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.collectionModel.CollectionManagerImpl
 *
 * @author: shallew
 * @date:2024/2/19 17:10
 * @about: TODO 收藏管理方法
 */
@Route(path = "/collectionsView/CollectionManagerImpl")
public class CollectionManagerImpl implements CollectionManagerService, CollectionsListImpl {

    /**
     * 添加成功之后获取的收藏数据
     */
    private CollectionData collection;

    /**
     * 是否删除成功
     */
    private boolean isRemoveSuccessful = false;

    /**
     * retrofit实例
     */
    private Retrofit retrofit;

    /**
     * 收藏管理服务接口的代理实例
     */
    private CollectionsListService collectionsListService;

    private List<CollectionData> collectionsList;

    @Override
    public void init(Context context) {
        //获取收藏的动态代理对象
        collectionsListService = (CollectionsListService)
                PublicRetrofit.create(CollectionsListService.class);
    }


    /**
     * @param houseId 民宿id
     * @return 是否成功添加至收藏
     * TODO 添加收藏 暴露接口的方法，调用本地方法
     */
    @Override
    public boolean addCollection(int houseId) {
        CollectionData collectionsData = addCollectionToList(houseId);
        return collectionsData != null;
    }

    /**
     *
     * @param houseId 民宿ID
     * @return
     * TODO 本地添加方法
     */
    @Override
    public CollectionData addCollectionToList(int houseId) {
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录");
            return null;
        }


        return new CollectionData(houseId);

//        if (retrofit !=null && collectionsListService != null) {
//            collectionsListService.addCollection(LoginStatusData.getUserToken().getValue(), houseId)
//                    .enqueue(new Callback<CollectionDataResponse>() {
//                        @Override
//                        public void onResponse(Call<CollectionDataResponse> call, Response<CollectionDataResponse> response) {
//                            CollectionDataResponse body = response.body();
//                            if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
//                                collection = body.getCollection();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<CollectionDataResponse> call, Throwable t) {
//                            collection = null;
//                            ToastUtils.toast("添加收藏网络请求失败！");
//                        }
//                    });
//        }

//        if (collection != null && !BuildConfig.isModule) {
//            //添加成功且集成模式下
//            //通知PersonalManagement页面修改 收藏数量
//            EventBus.getDefault().post(CollectionManagerService.COLLECTIONS_HAS_CHANGED);
//        }
//        return collection;
    }

    /**
     * @param houseId 民宿ID
     * @return 是否成功删除收藏
     * TODO 删除收藏 暴露接口方法 调用本地方法实现
     */
    @Override
    public boolean removeCollection(int houseId) {
        return removeCollectionFromList(houseId);
    }

    /**
     * @param houseId 民宿ID
     * @return TODO 本模块 本地方法
     */
    @Override
    public boolean removeCollectionFromList(int houseId) {
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录");
            return false;
        }

        return true;

//        if (retrofit !=null && collectionsListService != null) {
//            collectionsListService.removeCollection(LoginStatusData.getUserToken().getValue(), houseId)
//                    .enqueue(new Callback<IsSuccessfulResponse>() {
//                        @Override
//                        public void onResponse(Call<IsSuccessfulResponse> call, Response<IsSuccessfulResponse> response) {
//                            IsSuccessfulResponse body = response.body();
//                            if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
//                                isRemoveSuccessful = body.isSuccess();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<IsSuccessfulResponse> call, Throwable t) {
//                            isRemoveSuccessful = false;
//                        }
//                    });
//        }
//        if (isRemoveSuccessful && !BuildConfig.isModule) {
//            //移除成功且集成模式下
//            //通知PersonalManagement页面修改收藏数量
//            EventBus.getDefault().post(CollectionManagerService.COLLECTIONS_HAS_CHANGED);
//        }
//
//        return isRemoveSuccessful;
    }

    /**
     *
     * @return 收藏列表的数据数量
     * TODO 暴露接口方法 调用本地方法，获取收藏列表，返回列表的大小
     */
    @Override
    public int getCollectionsCount() {
        List<CollectionData> list = getCollectionsList();
        if (list != null) {
            return list.size();
        } else {
            //返回-1 表示网络请求异常
            return -1;
        }
    }

    /**
     *
     * @return 收藏列表
     * TODO 本地方法 返回收藏列表
     */
    @Override
    public List<CollectionData> getCollectionsList() {
        //如果账户未登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录");
            return null;
        }

        Log.d("requestCOUNT", "getCollectionsList: 申请一次");
        //测试
        collectionsList = new ArrayList<>();
        Collections.addAll(collectionsList, new CollectionData(1), new CollectionData(2), new CollectionData(3),
                new CollectionData(4), new CollectionData(5), new CollectionData(6));
        return collectionsList;


//        collectionsListService.getCollectionsList(LoginStatusData.getUserToken().getValue())
//                .enqueue(new Callback<CollectionsListResponse>() {
//                    @Override
//                    public void onResponse(Call<CollectionsListResponse> call, Response<CollectionsListResponse> response) {
//                        CollectionsListResponse body = response.body();
//                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
//                            collectionsList = body.getList();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<CollectionsListResponse> call, Throwable t) {
//                        collectionsList = null;
//                    }
//                });
//        return collectionsList;
    }
}
