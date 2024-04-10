package com.xupt3g.collectionsview.collectionModel.retrofit;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.model.retrofit.CollectionsData
 *
 * @author: shallew
 * @data: 2024/2/18 1:45
 * @about: TODO
 */
public class CollectionData {
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
     * 评星评分
     */
    private float ratingStars;
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
    private int rowState = 1;

    public int getRowState() {
        return rowState;
    }

    public CollectionData(int id) {
        this.id = id;
    }

    public CollectionData() {
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

    public float getRatingstarts() {
        return ratingStars;
    }

    public int getPriceBefore() {
        return priceBefore;
    }

    public int getPriceAfter() {
        return priceAfter;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"cover\":\'" + cover + "\'" +
                ", \"title\":\'" + title + "\'" +
                ", \"intro\":\'" + intro + "\'" +
                ", \"location\":\'" + location + "\'" +
                ", \"ratingstarts\":" + ratingStars +
                ", \"priceBefore\":" + priceBefore +
                ", \"priceAfter\":" + priceAfter +
                ", \"rowState\":" + rowState +
                '}';
    }
}
