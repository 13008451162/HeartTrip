package com.xupt3g.searchresultview.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: CountyData
 *
 * @author: lukecc0
 * @data:2024/4/12 下午6:43
 * @about: TODO
 */

@NoArgsConstructor
@Data
public class CountyData {

    @SerializedName("status")
    private Integer status;
    @SerializedName("data_version")
    private String dataVersion;
    @SerializedName("result_size")
    private Integer resultSize;
    @SerializedName("districts")
    private DistrictsDTO districts;

    @NoArgsConstructor
    @Data
    public static class DistrictsDTO {

        @SerializedName("districts")
        private List<Districts>  districts;


        public List<Districts> getDistricts() {
            return districts;
        }

        public void setDistricts(List<Districts> districts) {
            this.districts = districts;
        }

        @NoArgsConstructor
        @Data
        public static class Districts {
            @SerializedName("code")
            private String code;
            @SerializedName("name")
            private String name;
            @SerializedName("level")
            private Integer level;
            @SerializedName("districts")
            private List<?> districts;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getLevel() {
                return level;
            }

            public void setLevel(Integer level) {
                this.level = level;
            }

            public List<?> getDistricts() {
                return districts;
            }

            public void setDistricts(List<?> districts) {
                this.districts = districts;
            }

            @Override
            public String toString() {
                return "DistrictsDTO{" +
                        "code='" + code + '\'' +
                        ", name='" + name + '\'' +
                        ", level=" + level +
                        ", districts=" + districts +
                        '}';
            }
        }

    }

    @Override
    public String toString() {
        return "CountyData{" +
                "status=" + status +
                ", dataVersion='" + dataVersion + '\'' +
                ", resultSize=" + resultSize +
                ", districts=" + districts +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Integer getResultSize() {
        return resultSize;
    }

    public void setResultSize(Integer resultSize) {
        this.resultSize = resultSize;
    }

    public DistrictsDTO getDistricts() {
        return districts;
    }

    public void setDistricts(DistrictsDTO districts) {
        this.districts = districts;
    }
}
