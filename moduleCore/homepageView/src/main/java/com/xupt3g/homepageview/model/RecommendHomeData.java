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

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public Integer getHomestayBusinessId() {
                return homestayBusinessId;
            }

            public void setHomestayBusinessId(Integer homestayBusinessId) {
                this.homestayBusinessId = homestayBusinessId;
            }

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public Integer getRowState() {
                return rowState;
            }

            public void setRowState(Integer rowState) {
                this.rowState = rowState;
            }

            public Integer getRatingStars() {
                return ratingStars;
            }

            public void setRatingStars(Integer ratingStars) {
                this.ratingStars = ratingStars;
            }

            public Integer getPriceBefore() {
                return priceBefore;
            }

            public void setPriceBefore(Integer priceBefore) {
                this.priceBefore = priceBefore;
            }

            public Integer getPriceAfter() {
                return priceAfter;
            }

            public void setPriceAfter(Integer priceAfter) {
                this.priceAfter = priceAfter;
            }
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public List<DataDTO.ListDTO>  getDataDTOList() {
        return data.getList();
    }
}
