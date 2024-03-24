package com.xupt3g.homepageview.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * 项目名: HeartTrip
 * 文件名: HomestayListReq
 *
 * @author: lukecc0
 * @data:2024/3/21 下午3:35
 * @about: TODO
 */

@Data
public class HomestayListReq {
    @SerializedName("page")
    private int page;
    @SerializedName("pageSize")
    private int pageSize;
}
