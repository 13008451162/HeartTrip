package com.xupt3g.collectionsview.collectionModel;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.collectionModel.AddCollectionRequestBody
 *
 * @author: shallew
 * @data: 2024/3/27 9:12
 * @about: TODO 收藏模块 收藏和猜你喜欢的请求体
 */
@NoArgsConstructor
@Data
public class AddCollectionRequestBody {
    @SerializedName("homestayId")
    private Integer homestayId;

    public AddCollectionRequestBody(int homestayId) {
        this.homestayId = homestayId;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" +
                "\"homestayId\":" + homestayId +
                '}';
    }
}
