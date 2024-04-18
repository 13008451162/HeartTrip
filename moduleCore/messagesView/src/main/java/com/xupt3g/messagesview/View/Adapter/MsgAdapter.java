package com.xupt3g.messagesview.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xupt3g.messagesview.Model.MessageData;
import com.xupt3g.messagesview.R;
import com.xupt3g.messagesview.databinding.ChatItemBinding;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: MsgAdapter
 *
 * @author: lukecc0
 * @data:2024/4/18 下午9:38
 * @about: TODO  聊天功能记录适配器
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<MessageData> mMsgList;

    public MsgAdapter(List<MessageData> mMsgList) {
        this.mMsgList = mMsgList;
    }

    @NonNull
    @Override
    public MsgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.ViewHolder holder, int position) {
        MessageData msg = mMsgList.get(position);
        if (msg.getRole() == MessageData.ASSISTANT) {
            //如果收到消息则显示左布局，不显示右布局
            holder.binding.leftLayout.setVisibility(View.VISIBLE);
            holder.binding.rightLayout.setVisibility(View.GONE);
            holder.binding.leftMsg.setText(msg.getContent());
        } else if (msg.getRole() == MessageData.USER) {
            //如果发出消息则显示右布局，不显示左布局
            holder.binding.leftLayout.setVisibility(View.GONE);
            holder.binding.rightLayout.setVisibility(View.VISIBLE);
            holder.binding.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ChatItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ChatItemBinding.bind(itemView);
        }
    }
}
