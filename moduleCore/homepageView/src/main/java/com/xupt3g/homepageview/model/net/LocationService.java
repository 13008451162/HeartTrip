package com.xupt3g.homepageview.model.net;

import com.xupt3g.homepageview.model.SearchedLocationData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 项目名: HeartTrip
 * 文件名: CityNetTask
 *
 * @author: lukecc0
 * @data:2024/3/17 下午10:09
 * @about: TODO 使用retrofit2进行位置搜索请求
 */

public interface LocationService {
    String ak = "uYs2CqYgjjJsxaFuNbi0eMJ6Gapwf99Z";

    /**
     * 全国位置查询
     *
     * @param query     待查询位置
     * @param region
     * @param cityLimit
     * @param output
     * @param ak
     * @return {@link Observable}<{@link SearchedLocationData}>
     */

    @GET("place/v2/suggestion?")
    Observable<SearchedLocationData> getLocation(@Query("query") String query,
                                                 @Query("region") String region,
                                                 @Query("city_limit") boolean cityLimit,
                                                 @Query("output") String output,
                                                 @Query("ak") String ak);

    /**
     * 指定范围查询
     *
     * @param region 范围
     * @param query  待查询位置
     * @return {@link Observable}<{@link SearchedLocationData}>
     */
    @GET("https://api.map.baidu.com/place/v2/suggestion?query={query}&region={region}&city_limit=true&output=json&ak=" + ak)
    Observable<SearchedLocationData> getLocation(@Path("region") String region, @Path("query") String query);
}
