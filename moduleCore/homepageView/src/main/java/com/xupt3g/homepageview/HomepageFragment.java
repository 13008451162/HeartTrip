package com.xupt3g.homepageview;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jakewharton.rxbinding4.view.RxView;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.MiniLoadingDialog;
import com.xupt3g.LocationUtils.LocationListener;
import com.xupt3g.LocationUtils.LocationService;
import com.xupt3g.homepageview.model.RecommendHomeData;
import com.xupt3g.homepageview.model.net.RecommendInfoTask;
import com.xupt3g.homepageview.presenter.RecommendInfoContrach;
import com.xupt3g.homepageview.presenter.RecommendInfoPresenter;
import com.xupt3g.homepageview.view.Adapter.RecommendAdpter;
import com.xupt3g.homepageview.view.Carousel;
import com.xupt3g.homepageview.view.SearchActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    /**
     * 向城市选择的Fragment页面传递数据
     */
    public static final String FLAG = "INDICATION";

    private int pageNumber;

    MiniLoadingDialog mMiniLoadingDialog;

    RecommendInfoContrach.Presenter mPresenter;

    private View inflaterView;

    private RecommendAdpter adpter;
    private RecyclerView recyclerView;
    private LottieAnimationView lottieAnimationView;
    private NestedScrollView nestedScrollView;
    private TextView cityTextView;
    private TextView attractionsTextView;
    private TextView specifyView;

    public static HomepageFragment newInstance() {
        return new HomepageFragment();
    }

    public HomepageFragment() {
    }

    private ActivityResultLauncher<Intent> resultLauncher;
    private ActivityResultLauncher<Intent> resultSearchLauncher;

    //使用百度定位Sdk
    LocationListener locationListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resultSearchLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //判断是否传入正确的result
                        if (result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();   //获取上一个活动返回的Intent
                            //判断上一个活动的Intent是否存在，存在则在日志中输入
                            if (intent != null) {
                                String str = intent.getStringExtra("data_return");
                                specifyView.setText(str);
                            }
                        }
                    }
                });

        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        //判断是否传入正确的result
                        if (result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();   //获取上一个活动返回的Intent
                            //判断上一个活动的Intent是否存在，存在则在日志中输入
                            if (intent != null) {
                                String str = intent.getStringExtra("data_return");
                                cityTextView.setText(str);
                            }
                        }
                    }
                });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        locationListener = LocationListener.getInstance();

        try {
            LocationService locationService = LocationService.getInstance();
            locationService.init(getContext().getApplicationContext());
            LocationService.start(locationListener);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        inflaterView = inflater.inflate(R.layout.home_fragment, container, false);
        nestedScrollView = inflaterView.findViewById(R.id.nested_scrollview);

        pageNumber = 1;

        mMiniLoadingDialog = WidgetUtils.getMiniLoadingDialog(getContext());


        cityTextView = inflaterView.findViewById(R.id.Location_view);
        lottieAnimationView = inflaterView.findViewById(R.id.lottie_failure);

        attractionsTextView = inflaterView.findViewById(R.id.specify_search);


        Button button = inflaterView.findViewById(R.id.search_button);

        TextView currentView = inflaterView.findViewById(R.id.currentLocation);
        specifyView = inflaterView.findViewById(R.id.specify_search);

        recyclerView = inflaterView.findViewById(R.id.recommendedRoom);
        adpter = new RecommendAdpter();


        //实现MVP绑定
        RecommendInfoTask recommendInfoTask = RecommendInfoTask.getInstance();
        RecommendInfoPresenter infoPresenter = new RecommendInfoPresenter(recommendInfoTask, this);
        this.setPresenter(infoPresenter);


        //加载推荐数据
        mPresenter.getHomeData(pageNumber);

        //初始化当前位置
        mPresenter.subscribe(locationListener.getLocData()
                .subscribe(locdata -> cityTextView.setText(locdata.getCity())));


        //防止点击过快发生重复创建Activity
        mPresenter.subscribe(RxView.clicks(cityTextView)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    intent.putExtra(FLAG, false);
                    resultLauncher.launch(intent);
                }));


        //防止点击过快发生重复创建Activity
        mPresenter.subscribe(RxView.clicks(attractionsTextView)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(v -> {
                    Intent intent = new Intent(getContext(), SearchActivity.class);
                    intent.putExtra(FLAG, true);
                    resultSearchLauncher.launch(intent);
                }));


        //更新当前位置
        currentView.setOnClickListener(v -> mPresenter.subscribe(locationListener.getLocData()
                .subscribe(bdLocation -> cityTextView.setText(bdLocation.getCity()))));


        listenForBottomSliding();

        initCarousel();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传递一个位置信息
                ARouter.getInstance().build("/searchResultView/SearchResultActivit")
                        .withString("position", specifyView.getText().toString())
                        .withString("city", cityTextView.getText().toString())
                        .withString("date", "3.14-3.16")
                        .navigation();
            }
        });


        return inflaterView;
    }

    /**
     * 加载轮播图
     */
    private void initCarousel() {
        //轮播图
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("https://raw.githubusercontent.com/13008451162/PicImg/main/202403201729241.jpg");
        arrayList.add("https://raw.githubusercontent.com/13008451162/PicImg/main/202403201729258.jpg");
        arrayList.add("https://raw.githubusercontent.com/13008451162/PicImg/main/202403201729937.jpg");
        arrayList.add("https://raw.githubusercontent.com/13008451162/PicImg/main/202403201729595.jpg");
        Carousel carousel = new Carousel(getContext(), inflaterView.findViewById(R.id.index_dot), inflaterView.findViewById(R.id.viewPager2));
        carousel.initViews(arrayList);
        carousel.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
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
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            recyclerView.setAdapter(adpter);
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
                    mMiniLoadingDialog.show();

                    new Handler().postDelayed(() -> {
                        pageNumber += 1;
                        mPresenter.getHomeData(pageNumber);
                        adpter.notifyItemInserted(adpter.getItemCount());
                    }, 500);
                }
            }
        });
    }

}
