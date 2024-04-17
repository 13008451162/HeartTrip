package com.xupt3g.searchresultview.model.net;

/**
 * 项目名: HeartTrip
 * 文件名: KekWordReq
 *
 * @author: lukecc0
 * @data:2024/4/17 下午2:44
 * @about: TODO 搜索房屋信息的关键字
 */

public class KeyWordReq {
    public KeyWordReq() {
    }

    @Override
    public String toString() {
        return "KeyWordReq{" +
                "location='" + location + '\'' +
                '}';
    }

    public KeyWordReq(String location) {
        this.location = location;
    }

    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
