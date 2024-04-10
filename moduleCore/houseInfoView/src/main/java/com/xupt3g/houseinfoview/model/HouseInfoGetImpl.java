package com.xupt3g.houseinfoview.model;

import androidx.lifecycle.MutableLiveData;

import com.xupt3g.houseinfoview.model.retrofit.HouseInfoBaseData;
import com.xupt3g.houseinfoview.model.retrofit.HouseInfoBaseDataResponse;
import com.xupt3g.houseinfoview.model.retrofit.RecommendHouse;
import com.xupt3g.houseinfoview.model.retrofit.RecommendHouseResponse;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.model.HouseInfoGetImpl
 *
 * @author: shallew
 * @data: 2024/3/12 21:10
 * @about: TODO Model层暴露接口
 */
public interface HouseInfoGetImpl {
    /**
     *
     * @param houseId 民宿ID
     * @return 获取民宿详情列表 无需登录的
     */
    MutableLiveData<HouseInfoBaseDataResponse> getHouseInfoBaseData(int houseId);

    /**
     * TODO 获取推荐民宿列表
     */
    MutableLiveData<RecommendHouseResponse> getRecommendHousesList();
}
