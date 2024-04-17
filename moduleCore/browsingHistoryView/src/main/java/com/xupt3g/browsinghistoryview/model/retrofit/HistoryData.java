package com.xupt3g.browsinghistoryview.model.retrofit;


import android.util.Log;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.model.retrofit.HistoryData
 *
 * @author: shallew
 * @data: 2024/2/25 21:55
 * @about: TODO 历史数据子项实体类 和收藏模块中CollectionData一样
 */
@NoArgsConstructor
@Data
public class HistoryData {


    @SerializedName("id")
    private Integer id;
    @SerializedName("homestayId")
    private Integer homestayId;
    @SerializedName("title")
    private String title;
    @SerializedName("cover")
    private String cover;
    @SerializedName("intro")
    private String intro;
    @SerializedName("location")
    private String location;
    @SerializedName("rowState")
    private Integer rowState;
    @SerializedName("priceBefore")
    private Integer priceBefore;
    @SerializedName("priceAfter")
    private Integer priceAfter;
    @SerializedName("lastBrowsingTime")
    private Integer lastBrowsingTime;

    public int getRowState() {
        return rowState;
    }

    public HistoryData() {
    }


    public int getId() {
        return id;
    }

    public String getCover() {
        return cover;
    }

    public String getTitle() {
        return title;
    }

    public String getIntro() {
        return intro;
    }

    public String getLocation() {
        return location;
    }

    public int getPriceBefore() {
        return priceBefore;
    }

    public int getPriceAfter() {
        return priceAfter;
    }

    public long getBrowsingTime() {
        return lastBrowsingTime;
    }

    public Integer getHomestayId() {
        return homestayId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"cover\":\'" + cover + "\'" +
                ", \"title\":\'" + title + "\'" +
                ", \"intro\":\'" + intro + "\'" +
                ", \"location\":\'" + location + "\'" +
                ", \"priceBefore\":" + priceBefore +
                ", \"priceAfter\":" + priceAfter +
                ", \"rowState\":" + rowState +
                ", \"lastBrowsingTime\":" + lastBrowsingTime +
                '}';
    }
}
