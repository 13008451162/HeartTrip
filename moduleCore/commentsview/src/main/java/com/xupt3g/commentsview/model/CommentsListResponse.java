package com.xupt3g.commentsview.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.model.CommentsListResponse
 *
 * @author: shallew
 * @data: 2024/3/23 12:23
 * @about: TODO 评论集合Response
 */
@NoArgsConstructor
@Data
public class CommentsListResponse {


    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;

    public CommentsListResponse(String msg) {
        this.msg = msg;
    }

    public CommentsListResponse() {
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<CommentData> getCommentsList() {
        return data.list;
    }

    public float getScore() {
        return Float.parseFloat(data.star);
    }

    public String getTidyRating() {
        return "整洁程度 " + data.tidyRating;
    }

    public String getTrafficRating() {
        return "交通位置 " + data.trafficRating;
    }

    public String getSecurityRating() {
        return "安全程度 " + data.securityRating;
    }

    public String getFoodRating() {
        return "餐饮体验 " + data.foodRating;
    }

    public String getCostRating() {
        return "综合性价比 " + data.costRating;
    }

    public String getCommentCount() {
        return "（共" + data.commentCount + "条点评）";
    }

    public int getHouseId() {
        return data.homestayId;
    }

    public CommentsListResponse(int houseId, float score, float tidyRating, float trafficRating, float securityRating, float foodRating, float costRating, int commentCount, List<CommentData> commentsList) {
        this.data.homestayId = houseId;
        this.data.star = String.valueOf(score);
        this.data.tidyRating = String.valueOf(tidyRating);
        this.data.trafficRating = String.valueOf(trafficRating);
        this.data.securityRating = String.valueOf(securityRating);
        this.data.foodRating = String.valueOf(foodRating);
        this.data.costRating = String.valueOf(costRating);
        this.data.commentCount = commentCount;
        this.data.list = commentsList;
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("homestayId")
        private Integer homestayId;
        @SerializedName("star")
        private String star;
        @SerializedName("tidyRating")
        private String tidyRating;
        @SerializedName("trafficRating")
        private String trafficRating;
        @SerializedName("securityRating")
        private String securityRating;
        @SerializedName("foodRating")
        private String foodRating;
        @SerializedName("costRating")
        private String costRating;
        @SerializedName("commentCount")
        private Integer commentCount;
        @SerializedName("list")
        private List<CommentData> list;

        @Override
        public String toString() {
            return "{" +
                    "\"homestayId\":" + homestayId +
                    ", \"star\":\'" + star + "\'" +
                    ", \"tidyRating\":\'" + tidyRating + "\'" +
                    ", \"trafficRating\":\'" + trafficRating + "\'" +
                    ", \"securityRating\":\'" + securityRating + "\'" +
                    ", \"foodRating\":\'" + foodRating + "\'" +
                    ", \"costRating\":\'" + costRating + "\'" +
                    ", \"commentCount\":" + commentCount +
                    ", \"list\":" + list +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\'" + msg + "\'" +
                ", \"data\":" + data +
                '}';
    }
}
