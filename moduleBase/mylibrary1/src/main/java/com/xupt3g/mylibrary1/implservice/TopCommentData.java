package com.xupt3g.mylibrary1.implservice;

import java.util.Arrays;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.mylibrary1.implservice.TopCommentData
 *
 * @author: shallew
 * @data: 2024/3/24 16:29
 * @about: TODO 用于民宿详情页面的热门评论实体类
 */
public class TopCommentData {
    private float houseScore;
    private String userAvatar;
    private String userPostTime;
    private String userNickname;
    private String userRating;
    private String content;
    private String[] picturesUrls;
    private String commentCount;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public TopCommentData(String msg) {
        this.msg = msg;
    }

    public TopCommentData(String userAvatar, String userPostTime, String userNickname, String userRating, String content, String[] picturesUrls) {
        this.houseScore = houseScore;
        this.userAvatar = userAvatar;
        this.userPostTime = userPostTime;
        this.userNickname = userNickname;
        this.userRating = userRating;
        this.content = content;
        this.picturesUrls = picturesUrls;
        this.commentCount = commentCount;
        this.msg = "OK";
    }

    public float getHouseScore() {
        return houseScore;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public String getUserPostTime() {
        return userPostTime;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getContent() {
        return content;
    }

    public String[] getPicturesUrls() {
        return picturesUrls;
    }

    public String getCommentCount() {
        return commentCount;
    }

    @Override
    public String toString() {
        return "{" +
                "\"houseScore\":" + houseScore +
                ", \"userAvatar\":\'" + userAvatar + "\'" +
                ", \"userPostTime\":\'" + userPostTime + "\'" +
                ", \"userNickname\":\'" + userNickname + "\'" +
                ", \"userRating\":\'" + userRating + "\'" +
                ", \"content\":\'" + content + "\'" +
                ", \"picturesUrls\":" + Arrays.toString(picturesUrls) +
                ", \"commentCount\":\'" + commentCount + "\'" +
                '}';
    }
}
