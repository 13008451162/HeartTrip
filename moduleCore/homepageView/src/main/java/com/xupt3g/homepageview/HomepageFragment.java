package com.xupt3g.homepageview;

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
 * 文件名: com.xupt3g.homepageview.HomepageFragment
 *
 * @author: shallew
 * @data: 2024/3/18 21:07
 * @about: TODO
 */
@Route(path = "/homepageView/HomepageFragment")
public class HomepageFragment extends Fragment {
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.homepage_fragment, container, false);

        return mView;
    }
}
