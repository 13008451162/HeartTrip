package com.xupt3g.collectionsview.presenter;

import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.collectionsview.collectionModel.CollectionManagerImpl;
import com.xupt3g.collectionsview.collectionModel.CollectionsListImpl;
import com.xupt3g.collectionsview.collectionModel.retrofit.CollectionData;
import com.xupt3g.collectionsview.guessModel.GuessListGetImpl;
import com.xupt3g.collectionsview.guessModel.GuessListGetRequest;
import com.xupt3g.collectionsview.guessModel.retrofit.GuessData;
import com.xupt3g.collectionsview.view.CollectionsGuessManagerImpl;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.presenter.ThePresenter
 *
 * @author: shallew
 * @data:2024/2/19 23:31
 * @about: TODO
 */
public class ThePresenter {
    /**
     * Model层收藏暴露接口对象
     */
    private final CollectionsListImpl collectionsListImpl;
    /**
     * Model层猜你喜欢暴露接口对象
     */
    private final GuessListGetImpl guessListImpl;
    /**
     * View层数据集合展示、收藏添加和删除 接口对象
     */
    private final CollectionsGuessManagerImpl collectionsGuessManager;


    public ThePresenter(CollectionsGuessManagerImpl collectionsShow) {
        this.collectionsListImpl = new CollectionManagerImpl();
        this.guessListImpl = new GuessListGetRequest();
        this.collectionsGuessManager = collectionsShow;
    }

    /**
     * TODO 从CollectionsListImpl中获取收藏集合，显示在View上
     */
    public void showCollections() {
        List<CollectionData> dataList1 = collectionsListImpl.getCollectionsList();
        if (dataList1 != null) {
            collectionsGuessManager.showCollectionsList(dataList1);

        } else {
            ToastUtils.toast("d1==null");
        }
    }

    /**
     * TODO 从GuessListGetImpl中获取猜你喜欢集合，并显示在View上
     */
    public void showGuessList() {
        //无需登录
        List<GuessData> dataList1 = guessListImpl.getGuessList();
        if (dataList1 != null) {
            collectionsGuessManager.showGuessList(dataList1);
        } else {
            ToastUtils.toast("guessList==null");
        }
    }

    /**
     * @param houseId 民宿ID
     * @return 是否成功添加收藏
     * TODO 在View层调用该方法，传入民宿ID，对该民宿进行添加到收藏列表的操作
     */
    public CollectionData addCollection(int houseId) {
//        在猜你喜欢页面可以进行添加操作
        CollectionData collectionData = collectionsListImpl.addCollectionToList(houseId);
//        if (collectionData != null && !BuildConfig.isModule) {
//            //集成
//            //通知个人管理页面 收藏数量改动
//            EventBus.getDefault().post(CollectionManagerService.COLLECTIONS_HAS_CHANGED);
//        }
        return collectionData;
    }

    /**
     *
     * @param houseId 民宿ID
     * @return 是否成功移除收藏
     */
    public boolean removeCollection(int houseId) {

        //在收藏页面可以进行删除操作
        return collectionsListImpl.removeCollectionFromList(houseId);
    }
}
