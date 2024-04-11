package com.xupt3g.commentsview.model;

import com.xupt3g.commentsview.PictureInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.model.CommentData
 *
 * @author: shallew
 * @data: 2024/3/20 22:46
 * @about: TODO 评论相关数据
 */
public class CommentData {
    /**
     * 民宿ID
     */
    private int homestayId;
    /**
     * 评论ID
     */
    private int id;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 点评时间 "2020年09月留下点评"
     */
    private String commentTime;
    /**
     * 点评分数 "他给出5.0分的评价"
     */
    private String star;
    /**
     * 点评内容
     */
    private String content;
    /**
     * 图片url
     */
    private String imageUrls;
    private int likeCount;

    public int getLikeCount() {
        return likeCount;
    }

    public String getUserAvatar() {
        return avatar;
    }

    public String getUserNickname() {
        return nickname;
    }

    public String getCommentedTime() {
        return commentTime;
    }

    public String getCommentedScore() {
        return "他给出" + star + "分的评价";
    }

    public String getCommentContent() {
        return content;
    }


    public int getCommentId() {
        return id;
    }

    public CommentData(String commentContent) {
        this.content = commentContent;
    }

    public int getHouseId() {
        return homestayId;
    }

    public List<PictureInfo> getPictureUrls() {
        //假数据
        imageUrls = "https://www.sohodd.com/wp-content/uploads/2019/12/32-WUSH-Inn_DESHIN.jpg" +
                "," +
                "https://pic2.zhimg.com/v2-e393246739167cdddc617fd9fc898239_r.jpg" +
                "," +
                "https://img.zcool.cn/community/011bfb57d60f120000012e7eec5adf.jpg@2o.jpg" +
                "," +
                "https://img.zcool.cn/community/01054f5cce728da801214168103462.jpg@3000w_1l_0o_100sh.jpg" +
                "," +
                "https://img.zcool.cn/community/0128926088ffbc11013f4720d7d7a7.jpg@1280w_1l_2o_100sh.jpg";
        String[] split = imageUrls.split(",");
        List<PictureInfo> pictureInfoArr = new ArrayList<>();
        for (String s : split) {
            pictureInfoArr.add(new PictureInfo(s));
        }
        return pictureInfoArr;
    }

    @Override
    public String toString() {
        return "{" +
                "\"homestayId\":" + homestayId +
                ", \"id\":" + id +
                ", \"avatar\":\'" + avatar + "\'" +
                ", \"nickname\":\'" + nickname + "\'" +
                ", \"commentTime\":\'" + commentTime + "\'" +
                ", \"star\":\'" + star + "\'" +
                ", \"content\":\'" + content + "\'" +
                ", \"imageUrls\":\'" + imageUrls + "\'" +
                ", \"likeCount\":" + likeCount +
                '}';
    }
}
