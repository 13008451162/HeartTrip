package com.xupt3g.houseinfoview.model.retrofit;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.model.retrofit.HouseInfoBaseDataResponse
 *
 * @author: shallew
 * @data: 2024/3/12 20:48
 * @about: TODO
 */
@NoArgsConstructor
@Data
public class HouseInfoBaseDataResponse {

    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private HouseInfoBaseData data;

    public HouseInfoBaseDataResponse(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public HouseInfoBaseData getHouseInfoBaseData() {
        return data;
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
