package com.xupt3g.mylibrary1.response;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.mylibrary1.response.FileUploadResponse
 *
 * @author: shallew
 * @data: 2024/3/23 13:26
 * @about: TODO 文件上传的网络请求Response 以图片为主
 */
@NoArgsConstructor
@Data
public class FileUploadResponse {
    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;

    public FileUploadResponse(String msg) {
        this.msg = msg;
    }

    public FileUploadResponse() {
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getUrl() {
        return data.url;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\'" + msg + "\'" +
                ", \"url\":\'" + data.url + "\'" +
                '}';
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("url")
        private String url;
    }
}
