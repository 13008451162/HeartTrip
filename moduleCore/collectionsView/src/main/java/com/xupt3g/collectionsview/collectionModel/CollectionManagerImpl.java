package com.xupt3g.collectionsview.collectionModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.libbase.BuildConfig;
import com.xuexiang.xui.utils.XToastUtils;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionDataResponse;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionsListResponse;
import com.xupt3g.mylibrary1.implservice.CollectionManagerService;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionsListService;
import com.xupt3g.mylibrary1.PublicRetrofit;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private MutableLiveData<CollectionDataResponse> collectionLiveData;

    /**
     * 是否删除成功
     */
    private MutableLiveData<IsSuccessfulResponse> removeSuccessLiveData;

    /**
     * 收藏管理服务接口的代理实例
     */
    private CollectionsListService collectionsListService;

    private MutableLiveData<CollectionsListResponse> collectionsListLiveData;

    @Override
    public void init(Context context) {
        //获取收藏的动态代理对象
        if (collectionsListService == null) {
            collectionsListService = (CollectionsListService)
                    PublicRetrofit.create(CollectionsListService.class);
        }
    }

    public CollectionManagerImpl() {
        collectionsListService = (CollectionsListService)
                PublicRetrofit.create(CollectionsListService.class);
    }

    /**
     * @param houseId 民宿id
     * @return 是否成功添加至收藏
     * TODO 添加收藏 暴露接口的方法，调用本地方法
     */
    @Override
    public MutableLiveData<Boolean> addCollection(LifecycleOwner owner, int houseId) {
        MutableLiveData<CollectionDataResponse> collectionLiveData = addCollectionToList(houseId);
        MutableLiveData<Boolean> resultLivaData = new MutableLiveData<>();
        collectionLiveData.observe(owner, new Observer<CollectionDataResponse>() {
            @Override
            public void onChanged(CollectionDataResponse response) {
                if (response != null && !response.getMsg().equals(PublicRetrofit.getErrorMsg()) && response.getCollection() != null) {
                    Log.d("collectIsLogged", "addToCollectionList: resultLivaData.setValue(true);");
                    if (response.getCollection() != null) {
                        resultLivaData.setValue(true);
                    } else {
                        resultLivaData.setValue(false);
                    }
                } else {
                    resultLivaData.setValue(false);
                }
            }
        });
        return resultLivaData;
    }

    /**
     * @param houseId 民宿ID
     * @return TODO 本地添加方法
     */
    @Override
    public MutableLiveData<CollectionDataResponse> addCollectionToList(int houseId) {
        collectionLiveData = new MutableLiveData<>();
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录");
            collectionLiveData.setValue(new CollectionDataResponse(PublicRetrofit.getErrorMsg()));
            return collectionLiveData;
        }
        collectionsListService.addCollection(LoginStatusData.getUserToken().getValue(), new AddCollectionRequestBody(houseId))
                .enqueue(new Callback<CollectionDataResponse>() {
                    @Override
                    public void onResponse(Call<CollectionDataResponse> call, Response<CollectionDataResponse> response) {
                        CollectionDataResponse body = response.body();
                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                            collectionLiveData.setValue(body);
                            if (body.getCollection() != null && !BuildConfig.isModule) {
                                //添加成功且集成模式下
                                //通知PersonalManagement页面修改 收藏数量
                                EventBus.getDefault().post(CollectionManagerService.COLLECTIONS_HAS_CHANGED);
                            }
                        } else {
                            collectionLiveData.setValue(new CollectionDataResponse(PublicRetrofit.getErrorMsg()));
                        }
                    }

                    @Override
                    public void onFailure(Call<CollectionDataResponse> call, Throwable t) {
                        collectionLiveData.setValue(new CollectionDataResponse(PublicRetrofit.getErrorMsg()));
                        ToastUtils.toast("添加收藏网络请求失败！");
                    }
                });
        return collectionLiveData;
    }

    /**
     * @param houseId 民宿ID
     * @return 是否成功删除收藏
     * TODO 删除收藏 暴露接口方法 调用本地方法实现
     */
    @Override
    public MutableLiveData<Boolean> removeCollection(LifecycleOwner owner, int houseId) {
        MutableLiveData<IsSuccessfulResponse> removeSuccessLiveData = removeCollectionFromList(houseId);
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        removeSuccessLiveData.observe(owner, new Observer<IsSuccessfulResponse>() {
            @Override
            public void onChanged(IsSuccessfulResponse response) {
                if (response != null && !response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    resultLiveData.setValue(response.isSuccess());
                }
            }
        });
        return resultLiveData;
    }

    /**
     * @param houseId 民宿ID
     * @return TODO 本模块 本地方法
     */
    @Override
    public MutableLiveData<IsSuccessfulResponse> removeCollectionFromList(int houseId) {
        removeSuccessLiveData = new MutableLiveData<>();
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录");
            removeSuccessLiveData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
            return removeSuccessLiveData;
        }

        collectionsListService.removeCollection(LoginStatusData.getUserToken().getValue(), new RemoveCollectionRequestBody(houseId))
                .enqueue(new Callback<IsSuccessfulResponse>() {
                    @Override
                    public void onResponse(Call<IsSuccessfulResponse> call, Response<IsSuccessfulResponse> response) {
                        IsSuccessfulResponse body = response.body();
                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                            removeSuccessLiveData.setValue(body);
                            if (body.isSuccess() && !BuildConfig.isModule) {
                                //移除成功且集成模式下
                                //通知PersonalManagement页面修改收藏数量
                                EventBus.getDefault().post(CollectionManagerService.COLLECTIONS_HAS_CHANGED);
                            }
                        } else {
                            removeSuccessLiveData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
                        }
                    }

                    @Override
                    public void onFailure(Call<IsSuccessfulResponse> call, Throwable t) {
                        removeSuccessLiveData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
                        XToastUtils.error("网络请求失败！");
                    }
                });

        return removeSuccessLiveData;
    }

    /**
     * @return 收藏列表的数据数量
     * TODO 暴露接口方法 调用本地方法，获取收藏列表，返回列表的大小
     */
    @Override
    public MutableLiveData<Integer> getCollectionsCount(LifecycleOwner owner) {
        MutableLiveData<CollectionsListResponse> collectionsListLiveData = getCollectionsList();
        MutableLiveData<Integer> resultLiveData = new MutableLiveData<>(-1);
        collectionsListLiveData.observe(owner, new Observer<CollectionsListResponse>() {
            @Override
            public void onChanged(CollectionsListResponse response) {
                if (response != null && !response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    if (response.getList() == null) {
                        resultLiveData.setValue(0);
                    } else {
                        resultLiveData.setValue(response.getList().size());
                    }
                }
            }
        });
        return resultLiveData;
    }

    /**
     * @return 收藏列表
     * TODO 本地方法 返回收藏列表
     */
    @Override
    public MutableLiveData<CollectionsListResponse> getCollectionsList() {
        collectionsListLiveData = new MutableLiveData<>();
        //如果账户未登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            ToastUtils.toast("尚未登录");
            collectionsListLiveData.setValue(new CollectionsListResponse(PublicRetrofit.getErrorMsg()));
            return collectionsListLiveData;
        }
        collectionsListService.getCollectionsList(LoginStatusData.getUserToken().getValue())
                .enqueue(new Callback<CollectionsListResponse>() {
                             @Override
                             public void onResponse(Call<CollectionsListResponse> call, Response<CollectionsListResponse> response) {
                                 CollectionsListResponse body = response.body();
                                 if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                                     collectionsListLiveData.setValue(body);
                                 } else {
                                     collectionsListLiveData.setValue(new CollectionsListResponse(PublicRetrofit.getErrorMsg()));
                                 }
                             }
                             @Override
                             public void onFailure(Call<CollectionsListResponse> call, Throwable t) {
                                 collectionsListLiveData.setValue(new CollectionsListResponse(PublicRetrofit.getErrorMsg()));
                                 XToastUtils.error("网络请求失败！");
                             }
                         });
        return collectionsListLiveData;
    }
}
