package com.xupt3g.houseinfoview.model;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.model.HouseInfoBaseDataResponse
 *
 * @author: shallew
 * @data: 2024/3/12 20:48
 * @about: TODO
 */
public class HouseInfoBaseDataResponse {
    private int code;
    private String msg;
    private HouseInfoBaseData houseInfoBaseData;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public HouseInfoBaseData getHouseInfoBaseData() {
        return houseInfoBaseData;
    }
}
