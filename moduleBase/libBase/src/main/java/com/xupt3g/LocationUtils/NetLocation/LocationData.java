package com.xupt3g.LocationUtils.NetLocation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: LocationData
 *
 * @author: lukecc0
 * @data:2024/4/3 下午7:50
 * @about: TODO 通过经纬度获取位置信息
 */

@NoArgsConstructor
@Data
public class LocationData {

    @SerializedName("status")
    private Integer status;
    @SerializedName("result")
    private ResultDTO result;

    @NoArgsConstructor
    @Data
    public static class ResultDTO {
        @SerializedName("formatted_address")
        private String formattedAddress;
        @SerializedName("edz")
        private EdzDTO edz;
        @SerializedName("business")
        private String business;
        @SerializedName("pois")
        private List<?> pois;
        @SerializedName("roads")
        private List<?> roads;
        @SerializedName("poiRegions")
        private List<?> poiRegions;
        @SerializedName("sematic_description")
        private String sematicDescription;
        @SerializedName("formatted_address_poi")
        private String formattedAddressPoi;
        @SerializedName("cityCode")
        private Integer cityCode;


        @NoArgsConstructor
        @Data
        public static class EdzDTO {
            @SerializedName("name")
            private String name;

            public String getName() {
                return name;
            }
        }

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public EdzDTO getEdz() {
            return edz;
        }

        public String getBusiness() {
            return business;
        }

        public List<?> getPois() {
            return pois;
        }

        public List<?> getRoads() {
            return roads;
        }

        public List<?> getPoiRegions() {
            return poiRegions;
        }

        public String getSematicDescription() {
            return sematicDescription;
        }

        public String getFormattedAddressPoi() {
            return formattedAddressPoi;
        }

        public Integer getCityCode() {
            return cityCode;
        }

        @Override
        public String toString() {
            return "ResultDTO{" +
                    "formattedAddress='" + formattedAddress + '\'' +
                    ", edz=" + edz +
                    ", business='" + business + '\'' +
                    ", pois=" + pois +
                    ", roads=" + roads +
                    ", poiRegions=" + poiRegions +
                    ", sematicDescription='" + sematicDescription + '\'' +
                    ", formattedAddressPoi='" + formattedAddressPoi + '\'' +
                    ", cityCode=" + cityCode +
                    '}';
        }
    }

    public Integer getStatus() {
        return status;
    }

    public ResultDTO getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "LocationData{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }
}
