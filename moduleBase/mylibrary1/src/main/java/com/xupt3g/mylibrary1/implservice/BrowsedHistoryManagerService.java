package com.xupt3g.mylibrary1.implservice;

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
    int getBrowsedHistoryCount();

    /**
     *
     * @param houseId 要添加的民宿Id
     * @return 返回是否操作成功
     * TODO 进入民俗详情页之类时调用该方法，将民宿添加到浏览历史列表
     */
    boolean addBrowsedHistory(int houseId);
}
