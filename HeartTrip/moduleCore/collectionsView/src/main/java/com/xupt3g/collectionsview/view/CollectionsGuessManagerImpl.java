package com.xupt3g.collectionsview.view;

import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionData;
import com.xupt3g.collectionsview.guessModel.retrofit.GuessData;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.view.CollectionsShowImpl
 *
 * @author: shallew
 * @data:2024/2/19 22:55
 * @about: TODO
 */
public interface CollectionsGuessManagerImpl {
    /**
     *
     * @param collectionsDataList 收藏数据集合
     * TODO 在View中显示收藏集合页面
     */
    void showCollectionsList(List<CollectionData> collectionsDataList);

    /**
     *
     * @param guessDataList 猜你喜欢数据集合
     * TODO 在View中显示猜你喜欢集合页面
     */
    void showGuessList(List<GuessData> guessDataList);
}
