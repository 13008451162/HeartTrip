package com.xupt3g.mylibrary1.response;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.mylibrary1.response.IsSuccessfulResponse
 *
 * @author: shallew
 * @data: 2024/2/21 2:00
 * @about: TODO 操作是否成功的response
 */
@NoArgsConstructor
@Data
public class IsSuccessfulResponse {


    @SerializedName("code")
    private Integer code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private DataDTO data;

    public IsSuccessfulResponse(String msg) {
        this.msg = msg;
    }

    public IsSuccessfulResponse() {
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSuccess() {
        return data.success;
    }

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("success")
        private Boolean success;

        @Override
        public String toString() {
            return "{" +
                    "\"success\":" + success +
                    '}';
        }
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
