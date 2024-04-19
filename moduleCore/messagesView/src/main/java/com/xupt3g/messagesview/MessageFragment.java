package com.xupt3g.messagesview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xupt3g.messagesview.Model.Message;
import com.xupt3g.messagesview.Model.MessageBody;
import com.xupt3g.messagesview.Model.Net.ChatInfoTask;
import com.xupt3g.messagesview.Presenter.ChatContract;
import com.xupt3g.messagesview.Presenter.ChatPresenter;
import com.xupt3g.messagesview.View.Adapter.MsgAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.messagesview.MessageFragment
 *
 * @author: shallew
 * @data: 2024/3/18 21:19
 * @about: TODO 聊天GPT推荐页面
 */
@Route(path = "/messagesView/MessageFragment")
public class MessageFragment extends Fragment implements ChatContract.View {
    private View mView;
    private ChatPresenter chatPresenter;

    private MsgAdapter msgAdapter;

    private List<MessageBody> mList;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.message_fragment, container, false);
        ChatInfoTask chatInfoTask = ChatInfoTask.getInstance();
        chatPresenter = new ChatPresenter(chatInfoTask, this);
        setPresenter(chatPresenter);
        mList = new ArrayList<>();
        return mView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText chatEdit = view.findViewById(R.id.chatEdit);
        Button sendButton = view.findViewById(R.id.chatSend);


        recyclerView = view.findViewById(R.id.chatRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        msgAdapter = new MsgAdapter(mList);
        recyclerView.setAdapter(msgAdapter);


        sendButton.setOnClickListener(v -> {
            if (chatEdit.getText() != null) {

                chatPresenter.getMessage(MessageBody.USER, chatEdit.getText().toString());
                mList.add(new MessageBody(MessageBody.USER, chatEdit.getText().toString()));
                chatEdit.setText("");

                //更新消息
                msgAdapter.notifyItemChanged(mList.size() - 1);
                recyclerView.scrollToPosition(mList.size() - 1);

            }
        });

    }

    @Override
    public void onDestroy() {
        chatPresenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void setRecyclerMessage(Message message) {
        mList.add(new MessageBody(MessageBody.ASSISTANT, message.getResult()));
        msgAdapter.notifyItemChanged(mList.size() - 1);
        recyclerView.scrollToPosition(mList.size() - 1);
    }

    @Override
    public void setPresenter(ChatContract.Present presenter) {

    }
}
