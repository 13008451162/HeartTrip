package com.xupt3g.messagesview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.messagesview.MessageFragment
 *
 * @author: shallew
 * @data: 2024/3/18 21:19
 * @about: TODO
 */
@Route(path = "/messagesView/MessageFragment")
public class MessageFragment extends Fragment {
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.message_fragment, container, false);

        return mView;
    }
}
