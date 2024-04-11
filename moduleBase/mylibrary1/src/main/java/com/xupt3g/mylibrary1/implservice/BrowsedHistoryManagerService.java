package com.xupt3g.mylibrary1.implservice;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.mylibrary1.implservice.BrowsedHistoryManagerService
 *
 * @author: shallew
 * @data: 2024/2/29 21:52
 * @about: TODO 浏览历史对外暴露接口
 */
public interface BrowsedHistoryManagerService extends IProvider {
    /**
     * 标记浏览历史改动
     */
    static String BROWSED_HISTORY_HAS_CHANGED = "BROWSED_HISTORY_HAS_CHANGED";

    /**
     *
     *  @return 返回浏览历史列表的数量
     * TODO 获取浏览历史数据的数量
     * 注意：正常时候返回数字>=0，如果返回数字<0(-1)，表示异常情况，可能网络请求失败
     */
    MutableLiveData<Integer> getBrowsedHistoryCount(LifecycleOwner owner);
}
