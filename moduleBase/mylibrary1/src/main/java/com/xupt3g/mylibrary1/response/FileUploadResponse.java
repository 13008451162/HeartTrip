package com.xupt3g.mylibrary1.response;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.mylibrary1.response.FileUploadResponse
 *
 * @author: shallew
 * @data: 2024/3/23 13:26
 * @about: TODO 文件上传的网络请求Response 以图片为主
 */
public class FileUploadResponse {
    private int code;
    private String msg;
    private String url;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getUrl() {
        return url;
    }
}
