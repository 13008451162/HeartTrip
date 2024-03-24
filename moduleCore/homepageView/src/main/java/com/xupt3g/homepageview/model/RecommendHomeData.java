package com.xupt3g.homepageview.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: RecommendHomeData
 *
 * @author: lukecc0
 * @data:2024/3/21 下午1:51
 * @about: TODO
 */

@NoArgsConstructor
@Data
public class RecommendHomeData {
    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("list")
        private List<ListDTO> list;

        @NoArgsConstructor
        @Data
        public static class ListDTO {
            @SerializedName("id")
            private Integer id;
            @SerializedName("title")
            private String title;
            @SerializedName("cover")
            private String cover;
            @SerializedName("intro")
            private String intro;
            @SerializedName("location")
            private String location;
            @SerializedName("homestayBusinessId")
            private Integer homestayBusinessId;
            @SerializedName("userId")
            private Integer userId;
            @SerializedName("rowState")
            private Integer rowState;
            @SerializedName("ratingStars")
            private Integer ratingStars;
            @SerializedName("priceBefore")
            private Integer priceBefore;
            @SerializedName("priceAfter")
            private Integer priceAfter;
        }
    }

    public List<DataDTO.ListDTO>  getDataDTOList() {
        return data.getList();
    }
}
