package com.xupt3g.collectionsview.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.view.MyPagerAdapter
 *
 * @author: shallew
 * @data:2024/2/16 2:20
 * @about: TODO
 */
public class MyPagerAdapter extends FragmentStateAdapter {
        private List<PagerFragment> vfs;

        public MyPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<PagerFragment> vfs) {
            super(fragmentManager, lifecycle);
            //这个构造函数中添加给数据集合赋值的参数
            this.vfs = vfs;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            //该方法返回子项的Fragment
            return vfs.get(position);
        }

        @Override
        public int getItemCount() {
            //子项数目
            return vfs.size();
        }

}
