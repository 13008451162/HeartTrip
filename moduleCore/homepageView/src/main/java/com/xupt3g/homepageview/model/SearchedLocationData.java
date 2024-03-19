package com.xupt3g.homepageview.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: SearchedLocationData
 *
 * @author: lukecc0
 * @data:2024/3/17 下午8:41
 * @about: TODO 位置搜索得到的数据
 */

@NoArgsConstructor
@Data
public class SearchedLocationData {


    @SerializedName("status")
    private Integer status;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private ArrayList<ResultDTO> result;

    public void setResultName(String name) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setName(name);
        this.result.add(resultDTO);
    }

    @NoArgsConstructor
    @Data
    public static class ResultDTO {
        @SerializedName("name")
        private String name;
        @SerializedName("location")
        private LocationDTO location;
        @SerializedName("uid")
        private String uid;
        @SerializedName("province")
        private String province;
        @SerializedName("city")
        private String city;
        @SerializedName("district")
        private String district;
        @SerializedName("business")
        private String business;
        @SerializedName("cityid")
        private String cityid;
        @SerializedName("tag")
        private String tag;
        @SerializedName("address")
        private String address;
        @SerializedName("children")
        private List<?> children;
        @SerializedName("adcode")
        private String adcode;

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ResultDTO{" +
                    "name='" + name + '\'' +
                    ", location=" + location +
                    ", uid='" + uid + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", district='" + district + '\'' +
                    ", business='" + business + '\'' +
                    ", cityid='" + cityid + '\'' +
                    ", tag='" + tag + '\'' +
                    ", address='" + address + '\'' +
                    ", children=" + children +
                    ", adcode='" + adcode + '\'' +
                    '}';
        }

        @NoArgsConstructor
        @Data
        public static class LocationDTO {
            @SerializedName("lat")
            private Double lat;
            @SerializedName("lng")
            private Double lng;
        }

        public String getName() {
            return name;
        }

        public LocationDTO getLocation() {
            return location;
        }

        public String getUid() {
            return uid;
        }

        public String getProvince() {
            return province;
        }

        public String getCity() {
            return city;
        }

        public String getDistrict() {
            return district;
        }

        public String getBusiness() {
            return business;
        }

        public String getCityid() {
            return cityid;
        }

        public String getTag() {
            return tag;
        }

        public String getAddress() {
            return address;
        }

        public List<?> getChildren() {
            return children;
        }

        public String getAdcode() {
            return adcode;
        }
    }

    public ArrayList<ResultDTO> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "SearchedLocationData{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
