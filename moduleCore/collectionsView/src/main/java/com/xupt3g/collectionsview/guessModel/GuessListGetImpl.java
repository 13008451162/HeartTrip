package com.xupt3g.collectionsview.guessModel;

import androidx.lifecycle.MutableLiveData;

import com.xupt3g.collectionsview.guessModel.retrofit.GuessData;
import com.xupt3g.collectionsview.guessModel.retrofit.GuessListResponse;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.guessModel.GuessListGetImpl
 *
 * @author: shallew
 * @data:2024/2/20 0:08
 * @about: TODO Model暴露接口
 */
public interface GuessListGetImpl {

    /**
     *
     * TODO 接收View层传来的userToken，进行网络请求
     */
    MutableLiveData<GuessListResponse> getGuessList();
}
