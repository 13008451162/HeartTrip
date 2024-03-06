package com.xupt3g.collectionsview.collectionModel.retrofit;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.model.retrofit.CollectionsListResponse
 *
 * @author: shallew
 * @data:2024/2/18 1:43
 * @about: TODO Retrofit用收藏列表Response
 */
public class CollectionsListResponse {
    private int code;
    private String msg;
    private List<CollectionData> list;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<CollectionData> getList() {
        return list;
    }
}
