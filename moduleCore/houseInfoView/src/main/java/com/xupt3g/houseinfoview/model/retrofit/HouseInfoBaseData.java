package com.xupt3g.houseinfoview.model.retrofit;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.model.retrofit.HouseInfoBaseData
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
     * 评星评级
     */
    private float ratingStars;
    /**
     * 评论总数量
     */
    private int commentCount;
    /**
     * 标题下的标签 "精选好房,有停车位,宽松取消,可做饭,可聚会"
     */
    private String titleTags;
    /**
     * 轮播图的图片url
     */
    private String bannerUrls;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 基础设施标签
     */
    private String facilities;
    /**
     * 民宿面积 50平
     */
    private String area;
    /**
     * 房间配置 1室1厅1卫1厨
     */
    private String roomConfig;
    /**
     * 房间清理过程录像的url
     */
    private String cleanVideo;
    /**
     * 房东的头像的Url
     */
    private String hostAvatar;
    /**
     * 房东的昵称
     */
    private String hostNickname;
    /**
     * 折后价
     */
    private int priceAfter;
    /**
     * 折前价
     */
    private int priceBefore;
    //以下暂不需要
    /**
     * 定位地址
     */
    private String location;

    private boolean isCollected;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
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

    public HouseInfoBaseData(int id, String title, String location, float ratingStars, int commentsCount, String titleTags, String[] bannerUrls, String[] baseFacilities, String area, String room, String cleanVideoUrl, String landlordAvatar, String landlordNickname, int priceAfter, int priceBefore) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.ratingStars = ratingStars;
        this.commentCount = commentsCount;
        this.titleTags = titleTags;
        StringBuilder sb = new StringBuilder();
        for (String s:
             bannerUrls) {
            sb.append(s);
            sb.append(",");
        }
        this.bannerUrls = sb.toString().substring(0, sb.length() - 1);
        sb = new StringBuilder();
        for (String s:
             baseFacilities) {
            sb.append(s);
            sb.append(",");
        }
        this.facilities = sb.toString().substring(0, sb.length() - 1);
        this.area = area;
        this.roomConfig = room;
        this.cleanVideo = cleanVideoUrl;
        this.hostAvatar = landlordAvatar;
        this.hostNickname = landlordNickname;
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
        return commentCount;
    }

    public String[] getTitleTags() {
        return titleTags.split(",");
    }

    public String[] getBannerUrls() {
        return bannerUrls.split(",");
    }

    public String[] getBaseFacilities() {
        return facilities.split(",");
    }

    public String getArea() {
        return area;
    }

    public String getRoom() {
        return roomConfig;
    }

    public boolean isCollected() {
        return isCollected;
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
        return cleanVideo;
    }

    public String getLandlordAvatar() {
        return hostAvatar;
    }

    public String getLandlordNickname() {
        return hostNickname;
    }

    public int getPriceAfter() {
        return priceAfter;
    }

    public int getPriceBefore() {
        return priceBefore;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"title\":\'" + title + "\'" +
                ", \"ratingStars\":" + ratingStars +
                ", \"commentsCount\":" + commentCount +
                ", \"titleTags\":\'" + titleTags + "\'" +
                ", \"bannerUrls\":\'" + bannerUrls + "\'" +
                ", \"latitude\":" + latitude +
                ", \"longitude\":" + longitude +
                ", \"baseFacilities\":\'" + facilities + "\'" +
                ", \"area\":\'" + area + "\'" +
                ", \"room\":\'" + roomConfig + "\'" +
                ", \"cleanVideoUrl\":\'" + cleanVideo + "\'" +
                ", \"landlordAvatar\":\'" + hostAvatar + "\'" +
                ", \"landlordNickname\":\'" + hostNickname + "\'" +
                ", \"priceAfter\":" + priceAfter +
                ", \"priceBefore\":" + priceBefore +
                ", \"location\":\'" + location + "\'" +
                '}';
    }
}
