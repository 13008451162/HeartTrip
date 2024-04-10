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
            @SerializedName("ratingStars")
            private Double ratingStars;
            @SerializedName("commentCount")
            private Integer commentCount;
            @SerializedName("titleTags")
            private String titleTags;
            @SerializedName("bannerUrls")
            private String bannerUrls;
            @SerializedName("latitude")
            private String latitude;
            @SerializedName("longitude")
            private String longitude;
            @SerializedName("location")
            private String location;
            @SerializedName("facilities")
            private String facilities;
            @SerializedName("cover")
            private String cover;
            @SerializedName("area")
            private String area;
            @SerializedName("roomConfig")
            private String roomConfig;
            @SerializedName("cleanVideo")
            private String cleanVideo;
            @SerializedName("homestayBusinessId")
            private Integer homestayBusinessId;
            @SerializedName("hostId")
            private Integer hostId;
            @SerializedName("hostAvatar")
            private String hostAvatar;
            @SerializedName("hostNickname")
            private String hostNickname;
            @SerializedName("rowState")
            private Integer rowState;
            @SerializedName("priceBefore")
            private Integer priceBefore;
            @SerializedName("priceAfter")
            private Integer priceAfter;

            @Override
            public String toString() {
                return "ListDTO{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", ratingStars=" + ratingStars +
                        ", commentCount=" + commentCount +
                        ", titleTags='" + titleTags + '\'' +
                        ", bannerUrls='" + bannerUrls + '\'' +
                        ", latitude='" + latitude + '\'' +
                        ", longitude='" + longitude + '\'' +
                        ", location='" + location + '\'' +
                        ", facilities='" + facilities + '\'' +
                        ", cover='" + cover + '\'' +
                        ", area='" + area + '\'' +
                        ", roomConfig='" + roomConfig + '\'' +
                        ", cleanVideo='" + cleanVideo + '\'' +
                        ", homestayBusinessId=" + homestayBusinessId +
                        ", hostId=" + hostId +
                        ", hostAvatar='" + hostAvatar + '\'' +
                        ", hostNickname='" + hostNickname + '\'' +
                        ", rowState=" + rowState +
                        ", priceBefore=" + priceBefore +
                        ", priceAfter=" + priceAfter +
                        '}';
            }

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

            public Double getRatingStars() {
                return ratingStars;
            }

            public void setRatingStars(Double ratingStars) {
                this.ratingStars = ratingStars;
            }

            public Integer getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(Integer commentCount) {
                this.commentCount = commentCount;
            }

            public String getTitleTags() {
                return titleTags;
            }

            public void setTitleTags(String titleTags) {
                this.titleTags = titleTags;
            }

            public String getBannerUrls() {
                return bannerUrls;
            }

            public void setBannerUrls(String bannerUrls) {
                this.bannerUrls = bannerUrls;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getFacilities() {
                return facilities;
            }

            public void setFacilities(String facilities) {
                this.facilities = facilities;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getRoomConfig() {
                return roomConfig;
            }

            public void setRoomConfig(String roomConfig) {
                this.roomConfig = roomConfig;
            }

            public String getCleanVideo() {
                return cleanVideo;
            }

            public void setCleanVideo(String cleanVideo) {
                this.cleanVideo = cleanVideo;
            }

            public Integer getHomestayBusinessId() {
                return homestayBusinessId;
            }

            public void setHomestayBusinessId(Integer homestayBusinessId) {
                this.homestayBusinessId = homestayBusinessId;
            }

            public Integer getHostId() {
                return hostId;
            }

            public void setHostId(Integer hostId) {
                this.hostId = hostId;
            }

            public String getHostAvatar() {
                return hostAvatar;
            }

            public void setHostAvatar(String hostAvatar) {
                this.hostAvatar = hostAvatar;
            }

            public String getHostNickname() {
                return hostNickname;
            }

            public void setHostNickname(String hostNickname) {
                this.hostNickname = hostNickname;
            }

            public Integer getRowState() {
                return rowState;
            }

            public void setRowState(Integer rowState) {
                this.rowState = rowState;
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

        @Override
        public String toString() {
            return "DataDTO{" +
                    "list=" + list +
                    '}';
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }
    }

    @Override
    public String toString() {
        return "RecommendHomeData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
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
}
