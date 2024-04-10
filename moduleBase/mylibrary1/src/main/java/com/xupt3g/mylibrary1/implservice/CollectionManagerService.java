package com.xupt3g.mylibrary1.implservice;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 项目名: HeartTrip
 * 文件名: com.example.mylibrary1.CollectionManager
 *
 * @author: shallew
 * @data:2024/2/19 16:49
 * @about: TODO 收藏管理对外暴露接口，使用方法将民宿添加至收藏或取消收藏
 */
public interface CollectionManagerService extends IProvider {
    /**
     * 标记收藏数量改动
     */
    static String COLLECTIONS_HAS_CHANGED = "COLLECTIONS_HAS_CHANGED";
    /**
     *
     * @param houseId 民宿id
     * @return 是否成功添加收藏
     * TODO 添加收藏
     */
     MutableLiveData<Boolean> addCollection(LifecycleOwner owner, int houseId);

    /**
     *
     * @param houseId 民宿ID
     * @return 是否成功删除收藏
     * TODO 删除收藏
     */
    MutableLiveData<Boolean> removeCollection(LifecycleOwner owner, int houseId);

    /**
     *
     * @return 返回收藏列表的数量
     * TODO 返回收藏列表的数量
     * 注意：正常时候返回数字>=0，如果返回数字<0(-1)，表示异常情况，可能网络请求失败
     */
    MutableLiveData<Integer> getCollectionsCount(LifecycleOwner owner);

}
