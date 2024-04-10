package com.xupt3g.collectionsview.guessModel.retrofit;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.guessModel.retrofit.GuessData
 *
 * @author: shallew
 * @data:2024/2/19 23:57
 * @about: TODO
 */
public class GuessData {
    /**
     * 民宿ID
     */
    private int id;

    /**
     * 是否被收藏
     */
    private boolean isCollected;
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

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"isCollected\":" + isCollected +
                ", \"cover\":\'" + cover + "\'" +
                ", \"title\":\'" + title + "\'" +
                ", \"intro\":\'" + intro + "\'" +
                ", \"location\":\'" + location + "\'" +
                ", \"priceBefore\":" + priceBefore +
                ", \"priceAfter\":" + priceAfter +
                '}';
    }

    public int getId() {
        return id;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
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
}
