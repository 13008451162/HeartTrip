package com.xupt3g.browsinghistoryview.presenter;

import com.xupt3g.browsinghistoryview.model.BrowsingHistoryManageImpl;
import com.xupt3g.browsinghistoryview.model.BrowsingHistoryRequest;
import com.xupt3g.browsinghistoryview.model.retrofit.HistoryData;
import com.xupt3g.browsinghistoryview.view.BrowsingHistoryUiImpl;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.browsinghistoryview.presenter.ThePresenter
 *
 * @author: shallew
 * @data: 2024/2/28 19:17
 * @about: TODO Presenter层
 */
public class ThePresenter {
    private BrowsingHistoryUiImpl view;
    private BrowsingHistoryManageImpl model;

    public ThePresenter(BrowsingHistoryUiImpl view) {
        this.view = view;
        this.model = new BrowsingHistoryRequest();
    }

    /**
     * TODO 从Model层获取浏览历史集合，如果集合不为空则获取成功，可以调用View层的方法显示到Ui界面上
     */
    public void getHistoryListShowOnUi() {
        List<HistoryData> browsingHistoryList = model.getBrowsingHistoryList();
        if (browsingHistoryList == null) {
            //失败
            return ;
        }
        view.showHistoryListOnUi(browsingHistoryList);
    }

    /**
     *
     * @param houseId 要添加到收藏的民宿ID
     * @return 是否成功
     * TODO 从View层向Model层传递添加收藏的请求
     */
    public boolean passRequestOfAddCollection(int houseId) {
        return model.addToCollections(houseId);
    }

    /**
     *
     * @param houseId 要删除历史记录的民宿ID
     * @return 是否成功
     * TODO 从View层向Model层传递删除一项历史记录的请求
     */
    public boolean passRequestOfRemoveHistory(int houseId) {
        return model.removeHistoryItem(houseId);
    }

    /**
     *
     * @return 是否成功
     * TODO 从View层向Model层传递清空历史记录的请求
     */
    public boolean clearHistoryList() {
        return model.clearBrowsingHistoryList();
    }
}
