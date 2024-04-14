package com.xupt3g.browsinghistoryview.model.retrofit;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.model.retrofit.HistoryData
 *
 * @author: shallew
 * @data: 2024/2/25 21:55
 * @about: TODO 历史数据子项实体类 和收藏模块中CollectionData一样
 */
public class HistoryData {

    /**
     * 民宿ID
     */
    private int id;
    /**
     * 封面
     */
    private String cover;
    /**
     * 标题
     */
    private String title;
    /**
     * 简介
     */
    private String intro;
    /**
     * 定位，省份
     */
    private String location;
    /**
     * 折前价格
     */
    private int priceBefore;
    /**
     * 折后价格
     */
    private int priceAfter;
    /**
     * 上架状态 0：下架 1：上架
     */
    private int rowState;
    /**
     * 上次浏览的时间 y/M/d
     */
    private long lastBrowsingTime;

    public int getRowState() {
        return rowState;
    }

    public HistoryData() {
    }

    public HistoryData(String browsingTime) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("y/M/d");
        Date date = simpleDateFormat.parse(browsingTime);
        this.lastBrowsingTime = date != null ? date.getTime() : 0L;
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

    public long getBrowsingTime() throws ParseException {
        return lastBrowsingTime;
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
