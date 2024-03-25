package com.xupt3g.commentsview.model;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.model.CommentsListResponse
 *
 * @author: shallew
 * @data: 2024/3/23 12:23
 * @about: TODO 评论集合Response
 */
public class CommentsListResponse {
    private int code;
    private String msg;
    /**
     * 民宿的ID
     */
    private int houseId;
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
    /**
     * 该民宿点评数
     */
    private int commentCount;
    private List<CommentData> commentsList;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<CommentData> getCommentsList() {
        return commentsList;
    }

    public float getScore() {
        return Float.parseFloat(score);
    }

    public String getTidyRating() {
        return "整洁程度 " + tidyRating;
    }

    public String getTrafficRating() {
        return "交通位置 " + trafficRating;
    }

    public String getSecurityRating() {
        return "安全程度 " + securityRating;
    }

    public String getFoodRating() {
        return "餐饮体验 " + foodRating;
    }

    public String getCostRating() {
        return "综合性价比 " + costRating;
    }

    public String getCommentCount() {
        return "（共" + commentCount + "条点评）";
    }

    public int getHouseId() {
        return houseId;
    }

    public CommentsListResponse(int houseId, float score, float tidyRating, float trafficRating, float securityRating, float foodRating, float costRating, int commentCount, List<CommentData> commentsList) {
        this.houseId = houseId;
        this.score = String.valueOf(score);
        this.tidyRating = String.valueOf(tidyRating);
        this.trafficRating = String.valueOf(trafficRating);
        this.securityRating = String.valueOf(securityRating);
        this.foodRating = String.valueOf(foodRating);
        this.costRating = String.valueOf(costRating);
        this.commentCount = commentCount;
        this.commentsList = commentsList;
    }
}
