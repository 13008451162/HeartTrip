package com.xupt3g.searchresultview.model.net;

import com.xupt3g.searchresultview.model.CountyData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 项目名: HeartTrip
 * 文件名: CountyService
 *
 * @author: lukecc0
 * @data:2024/4/12 下午6:37
 * @about: TODO 使用Retrofit2实现下级行政区的搜索
 */

public interface CountyService {
    /**
     * 使用百度定位搜索数据
     * @param keyword
     * @param sub_admin
     * @param ak
     * @return {@link Observable}<{@link CountyData}>
     */
    @GET("api_region_search/v1/?")
    Observable<CountyData> getCountyData(@Query("keyword") String keyword,
                                         @Query("sub_admin") String sub_admin,
                                         @Query("ak") String ak);
}
