package com.xupt3g.searchresultview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.xuexiang.xui.widget.picker.XRangeSlider;
import com.xuexiang.xui.widget.spinner.DropDownMenu;
import com.xupt3g.UiTools.UiTool;
import com.xupt3g.searchresultview.View.Adapter.HousesAdapter;
import com.xupt3g.searchresultview.View.Adapter.ListDropDownAdapter;
import com.xupt3g.searchresultview.View.Adapter.PriceRangeAdapter;
import com.xupt3g.searchresultview.databinding.ActivitySearchResultBinding;
import com.xupt3g.searchresultview.model.CountyData;
import com.xupt3g.searchresultview.model.HousingData;
import com.xupt3g.searchresultview.model.net.CountyInfoTask;
import com.xupt3g.searchresultview.model.net.HouseInfoTask;
import com.xupt3g.searchresultview.presenter.CountyInfoPresent;
import com.xupt3g.searchresultview.presenter.HouseInfoPresent;
import com.xupt3g.searchresultview.presenter.SearchInfoContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;


@Route(path = "/searchResultView/SearchResultActivit")
public class SearchResultActivit extends AppCompatActivity implements SearchInfoContract.SearchView {

    private ActivitySearchResultBinding searchResultBinding;

    PublishSubject publishSubject = PublishSubject.create();

    private CountyInfoPresent countyInfoPresent;

    private HouseInfoPresent houseInfoPresent;
    private DropDownMenu mDropDownMenu;

    //,"位置区域","筛选条件"
    private String[] mHeaders = {"推荐排序", "价格范围", "位置区域"};
    private List<View> mPopupViews = new ArrayList<>();

    //
    private String[] mRecommend = {"推荐排序", "距离优先", "好评优先", "低价优先", "高价优先"};
    private String[] mPrice = {"￥150以下", "￥150-￥200", "￥200-￥300",
            "￥300-￥400", "￥400-￥600", "￥600以上"};

    public final int REQUEST_CODE = 1;

    @Autowired(name = "position")
    public String position;

    @Autowired(name = "city")
    public String city;

    @Autowired(name = "dateStart")
    public String dateStart;

    @Autowired(name = "dateEnd")
    public String dateEnd;


    private ActivityResultLauncher<Intent> resultLauncher;
    private RecyclerView recyclerView;
    private HousesAdapter housesAdapter;
    private Observable<CharSequence> bObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchResultBinding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        setContentView(searchResultBinding.getRoot());

        ARouter.getInstance().inject(this);

        //初始化次级行政区城市加载框架
        CountyInfoTask infoTask = CountyInfoTask.getInstance();
        CountyInfoPresent cInfoPresent = new CountyInfoPresent(infoTask, this);

        HouseInfoTask houseInfoTask = HouseInfoTask.getInstance();
        HouseInfoPresent hInfoPresent = new HouseInfoPresent(houseInfoTask, this);

        setPresent(cInfoPresent);
        setPresent(hInfoPresent);

        //设置透明状态栏
        UiTool.setImmersionBar(this, true);

        if (city != null) {
            searchResultBinding.searchBarView.cityView.setText(city);
            cInfoPresent.setDropDownMenu(city);

            bObservable = Observable.just(city);

            hInfoPresent.subscribe(publishSubject.hide().subscribe(o -> {
                Observable<CharSequence> observable = Observable.just(city);
                hInfoPresent.setHouseAdapter(observable);
            }));
        }

        if (position != null) {
            searchResultBinding.searchBarView.searchEditText.setText(position);
        }

        if (dateStart != null && dateEnd != null) {
            setDataStartAndEnd(dateStart, dateEnd);
        }

        EditText editText = searchResultBinding.searchBarView.searchEditText;

        @io.reactivex.rxjava3.annotations.NonNull Observable<CharSequence> editObservable =
                RxTextView.textChanges(editText)
                        .filter(sequence -> {
                            if (sequence.toString().length() > 0) {
                                return true;
                            } else {
                                hInfoPresent.setHouseAdapter(bObservable);
                                return false;
                            }
                        })
                        .debounce(1_000, TimeUnit.MICROSECONDS)
                        .retry(2);

        hInfoPresent.subscribe(publishSubject.hide().subscribe(o -> {
            hInfoPresent.setHouseAdapter(Observable.combineLatest(
                    bObservable,
                    editObservable,
                    (a, b) -> a.toString() + b.toString()));
        }));

        searchResultBinding.searchBarView.date.setOnClickListener(v -> {
            //传递标识符到需要跳转的Activity
            ARouter.getInstance().build("/houseInfoView/ChooseTimeCalendarActivity").navigation(this, REQUEST_CODE);
        });


        searchResultBinding.searchBarView.imageButton.setOnClickListener(v -> finish());
    }


    @Override
    public void initDropDownMenu(@NonNull CountyData.DistrictsDTO districtsDTO) {

        //初始化弹出框
        mDropDownMenu = findViewById(R.id.ddm_content);

        ListDropDownAdapter mCityAdapter;
        ListDropDownAdapter mCountyAdapter;
        PriceRangeAdapter mPriceAdapter;

        //init 推荐排序
        final ListView ageView = new ListView(SearchResultActivit.this);
        ageView.setDividerHeight(0);
        mCityAdapter = new ListDropDownAdapter(SearchResultActivit.this, mRecommend);
        mCityAdapter.setSelectPosition(0);
        ageView.setAdapter(mCityAdapter);


        //init 价格范围
        final View priceRangeView = getLayoutInflater().inflate(R.layout.layout_price_choose, null);
        GridView gridView = priceRangeView.findViewById(R.id.constellation);
        XRangeSlider xrsBubble = priceRangeView.findViewById(R.id.xrs_bubble);
        mPriceAdapter = new PriceRangeAdapter(SearchResultActivit.this, xrsBubble, mPrice);
        gridView.setAdapter(mPriceAdapter);

        //init 位置区域
        final ListView countyView = new ListView(SearchResultActivit.this);
        countyView.setDividerHeight(0);
        // 创建一个字符串数组，大小与 DistrictsDTO 数组相同
        String[] districtsArray = new String[districtsDTO.getSubDistricts().size()];
        int i = 0;
        // 遍历 DistrictsDTO 数组，提取区域名称并存储到字符串数组中
        for (CountyData.DistrictsDTO.SubDistrictsDTO subDistrictsDTO : districtsDTO.getSubDistricts()) {
            districtsArray[i++] = subDistrictsDTO.getName();
        }

        mCountyAdapter = new ListDropDownAdapter(SearchResultActivit.this, districtsArray);
        countyView.setAdapter(mCountyAdapter);


        //init mPopupViews
        mPopupViews.add(ageView);
        mPopupViews.add(priceRangeView);
        mPopupViews.add(countyView);


        //init 点击事件
        ageView.setOnItemClickListener((parent, view, position, id) -> {
            mCityAdapter.setSelectPosition(position);
            mDropDownMenu.setTabMenuText(position == 0 ? mHeaders[0] : mRecommend[position]);
            mDropDownMenu.closeMenu();
        });

        gridView.setOnItemClickListener((parent, view, position, id) -> mPriceAdapter.setSelectPosition(position));

        xrsBubble.setOnRangeSliderListener(new XRangeSlider.OnRangeSliderListener() {
            @Override
            public void onMaxChanged(XRangeSlider slider, int maxValue) {
                mDropDownMenu.setTabMenuText("￥" + slider.getSelectedMin() + "-￥" + slider.getSelectedMax());
            }

            @Override
            public void onMinChanged(XRangeSlider slider, int minValue) {
                mDropDownMenu.setTabMenuText("￥" + slider.getSelectedMin() + "-￥" + slider.getSelectedMax());
            }
        });

        countyView.setOnItemClickListener((parent, view, position, id) -> {
            mCountyAdapter.setSelectPosition(position);
            mDropDownMenu.setTabMenuText(position == 0 ? mHeaders[2] : districtsArray[position]);
            mDropDownMenu.closeMenu();
        });

        //展示数据
        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        housesAdapter = new HousesAdapter();
        recyclerView.setAdapter(housesAdapter);

        //init context view
        recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //初始化下拉框
        mDropDownMenu.setDropDownMenu(mHeaders, mPopupViews, recyclerView);

        publishSubject.onNext(true);
    }

    @Override
    public void sethouseView(HousingData listDTO) {
        housesAdapter.setmList((ArrayList<HousingData.ListDTO>) listDTO.getList());
        housesAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresent(Object present) {
        if (present instanceof CountyInfoPresent) {
            countyInfoPresent = (CountyInfoPresent) present;
        } else if (present instanceof HouseInfoPresent) {
            houseInfoPresent = (HouseInfoPresent) present;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String[] timeResult = data.getStringArrayExtra("data_return");

                setDataStartAndEnd(timeResult[0], timeResult[1]);
            }
        }
    }

    /**
     * 设置住店时间和离店时间
     *
     * @param start
     * @param end
     */
    private void setDataStartAndEnd(String start, String end) {
        // 指定日期格式化模式
        SimpleDateFormat inputFormatter = new SimpleDateFormat("M月d日", Locale.CHINA);
        SimpleDateFormat outputFormatter = new SimpleDateFormat("MM.dd");

        try {
            // 将文字日期解析为Date对象
            Date date1 = inputFormatter.parse(start.toString());
            Date date2 = inputFormatter.parse(end.toString());

            // 将日期格式化为指定格式的字符串
            String formattedDate1 = outputFormatter.format(date1);
            String formattedDate2 = outputFormatter.format(date2);

            // 输出结果
            searchResultBinding.searchBarView.checkInDate.setText("入:" + formattedDate1);
            searchResultBinding.searchBarView.departureDate.setText("离:" + formattedDate2);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


//    private MyDialogFragment init() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        MyDialogFragment myDialogFragment = (MyDialogFragment) fragmentManager.findFragmentById((int) R.layout.popup_layout);
//
//        if (myDialogFragment == null) {
//            myDialogFragment = new MyDialogFragment();
//            FragmentTransaction ft = fragmentManager.beginTransaction();
//            ft.add(R.id.search_fragment, myDialogFragment);
//            ft.commit();
//        }
//
//        return myDialogFragment;
//    }
}