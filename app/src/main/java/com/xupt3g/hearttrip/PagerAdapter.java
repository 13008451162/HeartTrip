package com.xupt3g.hearttrip;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.hearttrip.PagerAdapter
 *
 * @author: shallew
 * @data: 2024/3/18 21:41
 * @about: TODO Viewpager的翻页适配器
 */
public class PagerAdapter extends FragmentStateAdapter {
    private List<Fragment> fragments;

    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Fragment> fragments) {
        super(fragmentManager, lifecycle);
        //这个构造函数中添加给数据集合赋值的参数
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //该方法返回子项的Fragment
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        //子项数目
        return fragments.size();
    }

}