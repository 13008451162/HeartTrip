package com.xupt3g.commentsview.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.model.PostCommentData
 *
 * @author: shallew
 * @data: 2024/3/23 18:56
 * @about: TODO
 */

@NoArgsConstructor
@Data
public class PostCommentData {


    @SerializedName("homestayId")
    private Integer homestayId;
    @SerializedName("commentTime")
    private String commentTime;
    @SerializedName("content")
    private String content;
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
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("imageUrls")
    private String imageUrls;

    public PostCommentData(int houseId, String commentContent, float score, float tidyRating, float trafficRating, float securityRating, float foodRating, float costRating) {
        this.homestayId = houseId;
        this.content = commentContent;
        this.star = String.valueOf(score);
        this.tidyRating = String.valueOf(tidyRating);
        this.trafficRating = String.valueOf(trafficRating);
        this.securityRating = String.valueOf(securityRating);
        this.foodRating = String.valueOf(foodRating);
        this.costRating = String.valueOf(costRating);
    }

    public void setCommentedTime(String commentedTime) {
        this.commentTime = commentedTime;
    }

    public void setPictureUrls(String pictureUrls) {

        this.imageUrls = pictureUrls;
    }

    @Override
    public String toString() {
        return "{" +
                "\"homestayId\":" + homestayId +
                ", \"commentTime\":\'" + commentTime + "\'" +
                ", \"content\":\'" + content + "\'" +
                ", \"star\":\'" + star + "\'" +
                ", \"tidyRating\":\'" + tidyRating + "\'" +
                ", \"trafficRating\":\'" + trafficRating + "\'" +
                ", \"securityRating\":\'" + securityRating + "\'" +
                ", \"foodRating\":\'" + foodRating + "\'" +
                ", \"costRating\":\'" + costRating + "\'" +
                ", \"nickname\":\'" + nickname + "\'" +
                ", \"avatar\":\'" + avatar + "\'" +
                ", \"imageUrls\":\'" + imageUrls + "\'" +
                '}';
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
