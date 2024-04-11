package com.xupt3g.houseinfoview.model.retrofit;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.model.retrofit.RecommendHouse
 *
 * @author: shallew
 * @data: 2024/3/11 21:23
 * @about: TODO 推荐的民宿 数据实体类
 */
public class RecommendHouse {
    private int id;
    private boolean isCollected;
    private String cover;
    private String title;
    private String intro;
    private String location;
    private int priceBefore;
    private int priceAfter;

    public RecommendHouse(int id, boolean isCollected, String cover, String title, String intro, String location, int priceBefore, int priceAfter) {
        this.id = id;
        this.isCollected = isCollected;
        this.cover = cover;
        this.title = title;
        this.intro = intro;
        this.location = location;
        this.priceBefore = priceBefore;
        this.priceAfter = priceAfter;
    }

    public RecommendHouse() {
        this.isCollected = false;
    }

    public int getId() {
        return id;
    }

    public boolean isCollected() {
        return isCollected;
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

    public void setCollected(boolean collected) {
        isCollected = collected;
    }
}
