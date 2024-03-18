package com.xupt3g.houseinfoview.model;

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
     * @return 获取民宿详情列表
     */
    HouseInfoBaseData getHouseInfoBaseData(int houseId);

    /**
     * TODO 获取推荐民宿列表
     * @return
     */
    List<RecommendHouse> getRecommendHousesList();
}
