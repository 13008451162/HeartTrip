package com.xupt3g.collectionsview.view;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.collectionsview.R;
import com.xupt3g.mylibrary1.LoginStatusData;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.collectionsview.view.CollectionsFragment
 *
 * @author: shallew
 * @data:2024/2/15 1:02
 * @about: TODO 收藏页面Fragment
 */
@Route(path = "/collectionsView/CollectionsFragment")
public class CollectionsFragment extends Fragment {
    private View mView;
    private TabLayout tabLayout;
    private ViewPager2 vp2;
    private static List<PagerFragment> fragments;
    private MyPagerAdapter pagerAdapter;
    private String[] tabs = new String[]{"我的收藏", "猜你喜欢"};
    private AppCompatImageView backBtn;
    private boolean isHideBackBtn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isHideBackBtn = getArguments().getBoolean("isHideBackBtn", false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.collection_frag_collections, container, false);

        tabLayout = (TabLayout) mView.findViewById(R.id.easy_indicator);
        vp2 = (ViewPager2) mView.findViewById(R.id.collections_viewpager2);
        backBtn = (AppCompatImageView) mView.findViewById(R.id.collection_backbtn);
        backBtn.setOnClickListener(view -> {
            requireActivity().finish();
        });
        if (isHideBackBtn) {
            backBtn.setVisibility(View.GONE);
        }


        fragments = new ArrayList<>();
        fragments.add(PagerFragment.newInstance(PagerFragment.PAGE_COLLECTIONS));
        fragments.add(PagerFragment.newInstance(PagerFragment.PAGE_GUESS));


        //设置按钮点击重写的回调方法
        fragments.get(0).setRippleViewOnClickListener(new PagerFragment.RippleViewClickedListener() {
            @Override
            public void onClicked() {
                if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
                    ToastUtils.toast("跳转到登陆界面");
                    if (!BuildConfig.isModule) {
                        //集成开发模式
                        ARouter.getInstance().build("/loginregistrationView/LoginActivity").navigation();
                    }
                } else {
                    vp2.setCurrentItem(1, true);
                }
            }
        });


        pagerAdapter = new MyPagerAdapter(requireActivity().getSupportFragmentManager(), getLifecycle(), fragments);
        vp2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        vp2.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, vp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabs[position]);
            }
        }).attach();
        vp2.setOffscreenPageLimit(2);
        vp2.setPageTransformer(new ZoomOutPageTransformer());
        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                PagerFragment fragment = fragments.get(position);
                if (fragment != null) {
                    fragment.scrollToTop();
                }
            }
        });
        vp2.setUserInputEnabled(false);

        return mView;
    }

    public static PagerFragment getCollectionPage() {
        return fragments.get(0);
    }

    public static PagerFragment getGuessPage() {
        return fragments.get(1);
    }

}
