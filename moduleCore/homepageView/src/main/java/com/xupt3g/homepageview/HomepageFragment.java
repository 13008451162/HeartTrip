package com.xupt3g.homepageview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;
import com.xupt3g.homepageview.model.RecommendHomeData;
import com.xupt3g.homepageview.model.net.RecommendInfoTask;
import com.xupt3g.homepageview.presenter.RecommendInfoContrach;
import com.xupt3g.homepageview.presenter.RecommendInfoPresenter;
import com.xupt3g.homepageview.view.Adapter.RecommendAdpter;
import com.xupt3g.homepageview.view.Carousel;
import com.xupt3g.homepageview.view.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * 项目名: HeartTrip
 * 文件名: HomeFragment
 *
 * @author: lukecc0
 * @data:2024/3/14 下午7:29
 * @about: TODO 主展示页面
 */

@Route(path = "/homepageView/HomepageFragment")
public class HomepageFragment extends Fragment implements RecommendInfoContrach.HomeView {

    private int pageNumber;

    MiniLoadingDialog mMiniLoadingDialog;

    RecommendInfoContrach.Presenter mPresenter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private View inflaterView;

    private RecommendAdpter adpter;
    private RecyclerView recyclerView;
    private LottieAnimationView lottieAnimationView;
    private NestedScrollView nestedScrollView;

    public static HomepageFragment newInstance() {
        return new HomepageFragment();
    }

    public HomepageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflaterView = inflater.inflate(R.layout.home_fragment, container, false);
        nestedScrollView = inflaterView.findViewById(R.id.nested_scrollview);

        pageNumber = 1;

        mMiniLoadingDialog = WidgetUtils.getMiniLoadingDialog(getContext());


        TextView textView = inflaterView.findViewById(R.id.Location_view);
        lottieAnimationView = inflaterView.findViewById(R.id.lottie_failure);

        recyclerView = inflaterView.findViewById(R.id.recommendedRoom);
        adpter = new RecommendAdpter();


        //实现MVP绑定
        RecommendInfoTask recommendInfoTask = RecommendInfoTask.getInstance();
        RecommendInfoPresenter infoPresenter = new RecommendInfoPresenter(recommendInfoTask, this);
        this.setPresenter(infoPresenter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adpter);


        //加载推荐数据
        mPresenter.getHomeData(pageNumber);


        textView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        });

        listenForBottomSliding();

        //轮播图
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("https://raw.githubusercontent.com/13008451162/PicImg/main/202403201729241.jpg");
        arrayList.add("https://raw.githubusercontent.com/13008451162/PicImg/main/202403201729258.jpg");
        arrayList.add("https://raw.githubusercontent.com/13008451162/PicImg/main/202403201729937.jpg");
        arrayList.add("https://raw.githubusercontent.com/13008451162/PicImg/main/202403201729595.jpg");
        Carousel carousel = new Carousel(getContext(), inflaterView.findViewById(R.id.index_dot), inflaterView.findViewById(R.id.viewPager2));
        carousel.initViews(arrayList);
        carousel.startAutoScroll();

        return inflaterView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void setPresenter(RecommendInfoContrach.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void revealRecycler(@NonNull RecommendHomeData listDTO) {
        if (listDTO.getData() != null) {
            mMiniLoadingDialog.dismiss();
            lottieAnimationView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            List<RecommendHomeData.DataDTO.ListDTO> list = listDTO.getData().getList();
            adpter.setmHomeList((ArrayList<RecommendHomeData.DataDTO.ListDTO>) list);
        } else {
            lottieAnimationView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void listenForBottomSliding() {

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    // 滑动到了底部
                    Log.d("TAG", "已滑动到底部");
                    mMiniLoadingDialog.show();

                    new Handler().postDelayed(() -> {
                        pageNumber += 1;
                        mPresenter.getHomeData(pageNumber);
                        adpter.notifyItemInserted(adpter.getItemCount() );
                    }, 500);
                }
            }
        });
    }

}
