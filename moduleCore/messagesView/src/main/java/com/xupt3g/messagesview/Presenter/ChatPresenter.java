package com.xupt3g.messagesview.Presenter;

import android.util.Log;

import com.xupt3g.messagesview.Model.Message;
import com.xupt3g.messagesview.Model.MessageData;
import com.xupt3g.messagesview.Model.Net.ChatInfoTask;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * 项目名: HeartTrip
 * 文件名: ChatPresenter
 *
 * @author: lukecc0
 * @data:2024/4/18 下午5:31
 * @about: TODO 实现聊天功能的Presenter
 */

public class ChatPresenter implements ChatContract.Present<MessageData> {

    private CompositeDisposable compositeDisposable;

    private ChatInfoTask chatInfoTask;

    private ChatContract.View view;

    public ChatPresenter(ChatInfoTask chatInfoTask, ChatContract.View view) {
        this.chatInfoTask = chatInfoTask;
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe(Disposable disposable) {
        if (compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void unsubscribe() {
        if (compositeDisposable != null && compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void getMessage(String role, String message) {

        MessageData messageData = new MessageData(role, message);

        subscribe(chatInfoTask.execute(messageData)
                .onErrorResumeNext(throwable -> {
                    Message message1 = new Message();
                    message1.setResult("网络出现错误请稍后再试");
                    return Observable.just(message1);
                })
                .subscribe(listMessageData -> {
                            chatInfoTask.upDataListMessages(listMessageData);
                            view.setRecyclerMessage(listMessageData);
                        },
                        error -> {
                            throw new RuntimeException("在接受消息时出现异常");
                        }));
    }
}
