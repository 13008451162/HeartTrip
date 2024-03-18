package com.xupt3g.houseinfoview.model;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.model.HouseInfoBaseData
 *
 * @author: shallew
 * @data: 2024/3/12 19:26
 * @about: TODO 民宿详情的基本信息
 */
public class HouseInfoBaseData {
    /**
     * 当前民宿的id
     */
    private int id;
    /**
     * 标题
     */
    private String title;
    /**
     * 定位地址
     */
    private String location;
    /**
     * 评星评级
     */
    private float ratingStars;
    /**
     * 评论总数量
     */
    private int commentsCount;
    /**
     * 标题下的标签 "精选好房-有停车位-宽松取消-可做饭-可聚会"
     */
    private String titleTags;
    /**
     * 轮播图的图片url
     */
    private String[] bannerUrls;
    /**
     * 基础设施标签
     */
    private String[] baseFacilities;
    /**
     * 民宿面积 50平
     */
    private String area;
    /**
     * 房间配置 1室1厅1卫1厨
     */
    private String room;
//    /**
//     * 卫浴设施标签
//     */
//    private String[] bathFacilities;
//    /**
//     * 服务设施标签
//     */
//    private String[] serviceFacilities;
//    /**
//     * 居家设施标签
//     */
//    private String[] livingFacilities;
//    /**
//     * 安全设施标签
//     */
//    private String[] safetyFacilities;
//    /**
//     * 周边设施标签
//     */
//    private String[] surroundingFacilities;
    /**
     * 房间清理过程录像的url
     */
    private String cleanVideoUrl;
    /**
     * 房东的头像的Url
     */
    private String landlordAvatar;
    /**
     * 房东的昵称
     */
    private String landlordNickname;
    /**
     * 折后价
     */
    private int priceAfter;
    /**
     * 折前价
     */
    private int priceBefore;

    public HouseInfoBaseData(int id, String title, String location, float ratingStars, int commentsCount, String titleTags, String[] bannerUrls, String[] baseFacilities, String area, String room, String cleanVideoUrl, String landlordAvatar, String landlordNickname, int priceAfter, int priceBefore) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.ratingStars = ratingStars;
        this.commentsCount = commentsCount;
        this.titleTags = titleTags;
        this.bannerUrls = bannerUrls;
        this.baseFacilities = baseFacilities;
        this.area = area;
        this.room = room;
        this.cleanVideoUrl = cleanVideoUrl;
        this.landlordAvatar = landlordAvatar;
        this.landlordNickname = landlordNickname;
        this.priceAfter = priceAfter;
        this.priceBefore = priceBefore;
    }

    public HouseInfoBaseData() {
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public float getRatingStars() {
        return ratingStars;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public String getTitleTags() {
        return titleTags;
    }

    public String[] getBannerUrls() {
        return bannerUrls;
    }

    public String[] getBaseFacilities() {
        return baseFacilities;
    }

    public String getArea() {
        return area;
    }

    public String getRoom() {
        return room;
    }

//    public String[] getBathFacilities() {
//        return bathFacilities;
//    }
//
//    public String[] getServiceFacilities() {
//        return serviceFacilities;
//    }
//
//    public String[] getLivingFacilities() {
//        return livingFacilities;
//    }
//
//    public String[] getSafetyFacilities() {
//        return safetyFacilities;
//    }
//
//    public String[] getSurroundingFacilities() {
//        return surroundingFacilities;
//    }

    public String getCleanVideoUrl() {
        return cleanVideoUrl;
    }

    public String getLandlordAvatar() {
        return landlordAvatar;
    }

    public String getLandlordNickname() {
        return landlordNickname;
    }

    public int getPriceAfter() {
        return priceAfter;
    }

    public int getPriceBefore() {
        return priceBefore;
    }

}
