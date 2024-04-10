package com.xupt3g.mylibrary1.implservice;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.mylibrary1.implservice.TopCommentGetService
 *
 * @author: shallew
 * @data: 2024/3/24 16:25
 * @about: TODO 获取某民宿的评论区相关信息和第一条评论，目前可知 用于民宿详情页面
 */
public interface TopCommentGetService extends IProvider {
    MutableLiveData<TopCommentData> getTopCommentInfo(LifecycleOwner owner, int houseId);
}
