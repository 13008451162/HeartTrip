package com.xupt3g.messagesview.Presenter;

import com.xupt3g.messagesview.Model.Message;
import com.xupt3g.messagesview.View.BaseView;

/**
 * 项目名: HeartTrip
 * 文件名: ChatContract
 *
 * @author: lukecc0
 * @data:2024/4/18 下午5:03
 * @about: TODO 聊天对话功能的管理类
 */

public interface ChatContract {
    /**
     * 聊天功能
     *
     * @author lukecc0
     * @date 2024/04/18
     */
    interface Present<T> extends BasePresenter {

        void getMessage(String role, String message);
    }

    /**
     * 管理聊天功能的视图
     *
     * @author lukecc0
     * @date 2024/04/18
     */
    interface View extends BaseView<Present> {

        /**
         * 在Ui更新数据
         *
         * @param message
         */
        void setRecyclerMessage(Message message);
    }
}
