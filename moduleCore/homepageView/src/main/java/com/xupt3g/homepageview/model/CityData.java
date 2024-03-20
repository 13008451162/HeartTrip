package com.xupt3g.homepageview.model;

/**
 * 项目名: HeartTrip
 * 文件名: SimpleData
 *
 * @author: lukecc0
 * @data:2024/3/14 上午11:03
 * @about: TODO  存储城市信息
 */

public class CityData {

    int type;
    String string;


    public CityData(int type, String string) {
        this.type = type;
        this.string = string;
    }

    @Override
    public String toString() {
        return "SimpleData{" +
                "type=" + type +
                ", string='" + string + '\'' +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}