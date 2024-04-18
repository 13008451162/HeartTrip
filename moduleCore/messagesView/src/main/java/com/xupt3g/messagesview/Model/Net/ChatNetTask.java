package com.xupt3g.messagesview.Model.Net;

import com.xupt3g.messagesview.Model.Message;
import com.xupt3g.messagesview.Model.MessageData;

import io.reactivex.rxjava3.core.Observable;

/**
 * 项目名: HeartTrip
 * 文件名: ChatNetTask
 *
 * @author: lukecc0
 * @data:2024/4/18 下午5:08
 * @about: TODO 对话聊天请求工具
 */

public interface ChatNetTask<T> {
    /**
     * 发送聊天消息
     *
     * @param messageData
     * @return {@link Observable}<{@link T}>
     */
    Observable<T> execute(MessageData messageData);


    /**
     * 更新数据列表
     *
     * @param message
     */
    void upDataListMessages(Message message);
}