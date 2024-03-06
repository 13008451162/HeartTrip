package com.xupt3g.collectionsview.collectionModel.retrofit;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.collectionModel.retrofit.CollectionDataResponse
 *
 * @author: shallew
 * @data:2024/2/21 2:03
 * @about: TODO
 */
public class CollectionDataResponse {
    private int code;
    private String msg;
    private CollectionData collection;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public CollectionData getCollection() {
        return collection;
    }
}
