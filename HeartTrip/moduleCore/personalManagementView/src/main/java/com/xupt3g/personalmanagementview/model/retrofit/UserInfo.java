package com.xupt3g.personalmanagementview.model.retrofit;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.personalmanagementview.model.retrofit.UserInfo
 *
 * @author: shallew
 * @data: 2024/2/11 0:11
 * @about: TODO
 */
public class UserInfo {
    /**
     * 用户id
     */
    private int id;
    /**
     * 用户电话号码
     */
    private String mobile;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户性别 男0 女1
     */
    private int sex;
    /**
     * 用户头像 url
     */
    private String avatar;
    /**
     * 用户自我介绍
     */
    private String info;

    public UserInfo() {
    }

    public UserInfo(String nickname, int sex, String avatar, String info) {
        this.nickname = nickname;
        this.sex = sex;
        this.avatar = avatar;
        this.info = info;
    }

    public UserInfo(int id, String mobile, String nickname, int sex, String avatar, String info) {
        this.id = id;
        this.mobile = mobile;
        this.nickname = nickname;
        this.sex = sex;
        this.avatar = avatar;
        this.info = info;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"mobile\":\'" + mobile + "\'" +
                ", \"nickname\":\'" + nickname + "\'" +
                ", \"sex\":" + sex +
                ", \"avatar\":\'" + avatar + "\'" +
                ", \"info\":\'" + info + "\'" +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public int getSex() {
        return sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getInfo() {
        return info;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
