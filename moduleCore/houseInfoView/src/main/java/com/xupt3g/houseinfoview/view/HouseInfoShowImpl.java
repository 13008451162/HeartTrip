package com.xupt3g.houseinfoview.view;

import com.xupt3g.houseinfoview.model.retrofit.HouseInfoBaseData;
import com.xupt3g.houseinfoview.model.retrofit.RecommendHouse;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.view.HouseInfoShowImpl
 *
 * @author: shallew
 * @data: 2024/3/12 21:41
 * @about: TODO View层对外暴露接口
 */
public interface HouseInfoShowImpl {
    /**
     *
     * @param houseInfoBaseData 民俗详情的基本数据
     * TODO 将民宿详情的基本数据展示到Ui上
     */
    void houseInfoBaseDataShowOnUi(HouseInfoBaseData houseInfoBaseData);

    /**
     * TODO 民宿收藏失败
     */
    void collectFailed(boolean notlogged);

    /**
     * TODO 民宿收藏成功
     */
    void collectSucceed();

    /**
     * TODO 显示推荐列表
     */
    void recommendHouseListShow(List<RecommendHouse> list);
}
