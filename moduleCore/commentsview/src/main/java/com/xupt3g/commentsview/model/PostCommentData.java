package com.xupt3g.commentsview.model;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.model.PostCommentData
 *
 * @author: shallew
 * @data: 2024/3/23 18:56
 * @about: TODO
 */
public class PostCommentData {

    /**
     * 民宿ID
     */
    private int houseId;
    /**
     * 评论ID
     */
    private int commentId;
    /**
     * 点评时间 "2020年09月留下点评"
     */
    private String commentedTime;
    /**
     * 点评内容
     */
    private String commentContent;
    /**
     * 图片url
     */
    private String pictureUrls;
    /**
     * 民宿总评分
     */
    private String score;
    /**
     * 整洁程度评分
     */
    private String tidyRating;
    /**
     * 交通位置评分
     */
    private String trafficRating;
    /**
     * 安全程度评分
     */
    private String securityRating;
    /**
     * 餐饮体验评分
     */
    private String foodRating;
    /**
     * 综合性价比评分
     */
    private String costRating;

    public PostCommentData(int houseId, String commentContent, float score, float tidyRating, float trafficRating, float securityRating, float foodRating, float costRating) {
        this.houseId = houseId;
        this.commentContent = commentContent;
        this.score = String.valueOf(score);
        this.tidyRating = String.valueOf(tidyRating);
        this.trafficRating = String.valueOf(trafficRating);
        this.securityRating = String.valueOf(securityRating);
        this.foodRating = String.valueOf(foodRating);
        this.costRating = String.valueOf(costRating);
    }

    public void setCommentedTime(String commentedTime) {
        this.commentedTime = commentedTime;
    }

    public void setPictureUrls(String pictureUrls) {

        this.pictureUrls = pictureUrls;
    }
}
