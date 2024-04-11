package com.xupt3g.searchresultview;

/**
 * 项目名: HeartTrip
 * 文件名: MyDialogFragment
 *
 * @author: lukecc0
 * @data:2024/3/27 下午4:33
 * @about: TODO
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MyDialogFragment extends BottomSheetDialogFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME,R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.popup_layout, container, false);


        // 获取Spinner实例
        Spinner spinner = view.findViewById(R.id.spinner);

        // 定义数据源
        String[] items = {"选项1", "选项2", "选项3", "选项4"};

        // 创建适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);

        // 设置下拉菜单的样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 绑定适配器到Spinner
        spinner.setAdapter(adapter);

        return view;

    }
}
