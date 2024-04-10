package com.xupt3g.homepageview.view;

import static com.xuexiang.xutil.XUtil.runOnUiThread;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.xupt3g.homepageview.HomepageFragment;
import com.xupt3g.homepageview.R;
import com.xupt3g.homepageview.model.CityOfCollect;
import com.xupt3g.homepageview.model.SearchedLocationData;
import com.xupt3g.homepageview.model.room.Data.HistoryData;
import com.xupt3g.homepageview.model.room.HistoryDatabase;
import com.xupt3g.homepageview.presenter.LocationInfoContract;
import com.xupt3g.homepageview.view.Adapter.CityAdapter;
import com.xupt3g.homepageview.view.Adapter.HistoryAdapter;
import com.xupt3g.homepageview.view.Adapter.SearchedAdpter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 项目名: HeartTrip
 * 文件名: CityPickerFragment
 *
 * @author: lukecc0
 * @data:2024/3/7 下午6:59
 * @about: TODO  选择城市
 */

public class CityPickerFragment extends Fragment implements LocationInfoContract.LocationView {


    private View historyView;
    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;
    private HistoryDatabase DB;

    public interface onFragmentCityListener {
        void onClickCity(String cityText);
    }


    private onFragmentCityListener mListener;

    private LocationInfoContract.Presenter mPresenter;
    private View indlaterView;
    private RecyclerView cityRecycler;
    private RecyclerView searchRecycler;
    private CityAdapter cityAdapter;
    private SearchedAdpter searchAdpter;
    private LottieAnimationView animationView;

    /**
     * @param tag false表示加载城市页，true表示加载搜索历史页
     * @return {@link CityPickerFragment}
     */
    public static CityPickerFragment newInstance(boolean tag) {

        CityPickerFragment cityPickerFragment = new CityPickerFragment();
        Bundle args = new Bundle();
        args.putBoolean(HomepageFragment.FLAG, tag);
        cityPickerFragment.setArguments(args);
        return cityPickerFragment;
    }

    public CityPickerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentCityListener) {
            mListener = (onFragmentCityListener) context;
        } else {
            throw new RuntimeException(context.toString() + " 与onFragmentCityListener类型不符合");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        indlaterView = inflater.inflate(R.layout.city_picke_fragment, container, false);

        EditText editText = indlaterView.findViewById(R.id.search_editText);
        ImageButton backButton = indlaterView.findViewById(R.id.imageButton);
        View cityLayoutView = indlaterView.findViewById(R.id.city_list_choose);
        LettersSidebar simpleSideBar = indlaterView.findViewById(R.id.simpleRipple);

        animationView = indlaterView.findViewById(R.id.viewNotFound);

        searchRecycler = indlaterView.findViewById(R.id.location_recycler);

        historyView = indlaterView.findViewById(R.id.history_view);

        View deleteView = indlaterView.findViewById(R.id.remove);

        searchAdpter = new SearchedAdpter();

        searchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        //true的情况加载搜索历史，false情况加载城市选择页
        if (getArguments() != null && getArguments().getBoolean(HomepageFragment.FLAG)) {
            simpleSideBar.setVisibility(View.GONE);

            DB = HistoryDatabase.getInstance(getContext());

            historyRecyclerView = indlaterView.findViewById(R.id.recyclerView_history);

            historyAdapter = new HistoryAdapter();

            mPresenter.subscribe(DB.historyDao()
                    .getAllData()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorResumeNext(throwable -> Observable.just(new ArrayList<>()))
                    .subscribe(this::initHistory,
                            error -> {
                                throw new RuntimeException("从数据库加载历史搜索数据异常");
                            }));

            @io.reactivex.rxjava3.annotations.NonNull Observable<CharSequence> editObservable = RxTextView.textChanges(editText)
                    .filter(sequence -> {
                        //控制选择视图和搜索视图的可见性
                        if (editText.getText().length() == 0) {
                            searchRecycler.setVisibility(View.GONE);
                            animationView.setVisibility(View.GONE);
                            historyView.setVisibility(View.VISIBLE);
                            return false;
                        } else {
                            historyView.setVisibility(View.GONE);
                            searchRecycler.setVisibility(View.VISIBLE);
                            return !sequence.toString().isEmpty();
                        }
                    })
                    .debounce(1_000, TimeUnit.MICROSECONDS)
                    .retry(2);

            //调用搜索功能
            mPresenter.locationSearch(editObservable);

            deleteView.setOnClickListener(v -> {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(() -> {
                    DB.historyDao().deleteDataAll();

                    //在主线程中更改UI
                    runOnUiThread(() -> {
                        mPresenter.subscribe(DB.historyDao()
                                .getAllData()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .onErrorResumeNext(throwable -> Observable.just(new ArrayList<>()))
                                .subscribe(this::initHistory,
                                        error -> {
                                            throw new RuntimeException("从数据库加载历史搜索数据异常");
                                        }));
                    });
                });
                executor.shutdown();
            });

            returnHistoryClickData();
        } else {

            cityRecycler = indlaterView.findViewById(R.id.recycler_view);
            cityAdapter = new CityAdapter(getContext(), CityOfCollect.getCityList());
            cityRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            cityRecycler.setAdapter(cityAdapter);

            @io.reactivex.rxjava3.annotations.NonNull Observable<CharSequence> editObservable = RxTextView.textChanges(editText)
                    .filter(sequence -> {
                        //控制选择视图和搜索视图的可见性
                        if (editText.getText().length() == 0) {
                            cityLayoutView.setVisibility(View.VISIBLE);
                            searchRecycler.setVisibility(View.GONE);
                            animationView.setVisibility(View.GONE);
                            return false;
                        } else {
                            searchRecycler.setVisibility(View.VISIBLE);
                            cityLayoutView.setVisibility(View.GONE);
                            return !sequence.toString().isEmpty();
                        }
                    })
                    .debounce(1_000, TimeUnit.MICROSECONDS)
                    .retry(2);

            //调用搜索功能
            mPresenter.locationSearch(editObservable);
            returnClickData();

            simpleSideBar.setOnLetterTouchedChangeListener(letterTouched -> {

                //实现滚动到屏幕顶端功能
                // 获取 RecyclerView 的 LayoutManager
                LinearLayoutManager layoutManager = (LinearLayoutManager) cityRecycler.getLayoutManager();
                if (layoutManager != null) {
                    // 获取字母对应的位置
                    int pos = cityAdapter.getLetterPosition(letterTouched);
                    if (pos != -1) {
                        // 滚动 RecyclerView 到指定位置并且偏移 0 个像素
                        layoutManager.scrollToPositionWithOffset(pos, 0);
                    }
                }

            });

        }


        //返回上一层
        backButton.setOnClickListener(v -> {
            getActivity().finish();
        });

        return indlaterView;
    }


    /**
     * 用于加载搜索的历史记录
     *
     * @param data
     */
    private void initHistory(List<HistoryData> data) {
        historyAdapter.notifyDataSetChanged();
        historyAdapter.setmList(data);
        historyRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        historyRecyclerView.setAdapter(historyAdapter);
    }


    @Override
    public void returnClickData() {

        if (mListener != null) {
            mPresenter.subscribe(cityAdapter.getClickObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(textView -> {
                                mListener.onClickCity(textView.getText().toString());
                            },
                            error -> {
                                throw new RuntimeException("城市点击事件出错");
                            }));

            mPresenter.subscribe(searchAdpter.getClickObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(textView -> {
                                mListener.onClickCity(textView.getText().toString());
                            },
                            error -> {
                                throw new RuntimeException("搜索点击事件出错");
                            }));
        }

    }


    @Override
    public void returnHistoryClickData() {
        if (mListener != null) {
            mPresenter.subscribe(searchAdpter.getClickObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(textView -> {
                                mListener.onClickCity(textView.getText().toString());

                                ExecutorService executor = Executors.newSingleThreadExecutor();
                                executor.submit(() -> {

                                    HistoryData data = new HistoryData();
                                    data.setLocationName(textView.getText().toString());

                                    DB.historyDao().insertData(data);

                                    //在主线程中更改UI
                                    runOnUiThread(() -> {
                                        historyAdapter.notifyDataSetChanged();
                                    });
                                });
                                executor.shutdown();

                            },
                            error -> {
                                throw new RuntimeException("搜索点击事件出错");
                            }));


            mPresenter.subscribe(historyAdapter.getClickObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(textView -> {
                                mListener.onClickCity(textView.getText().toString());
                            },
                            error -> {
                                throw new RuntimeException("历史记录点击事件出错");
                            }));
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//        HomepageFragment homeFragment = (HomepageFragment) fragmentManager.findFragmentById((int) R.layout.home_fragment);
//        if (homeFragment != null) {
//            FragmentTransaction ft = fragmentManager.beginTransaction();
//            ft.show(homeFragment);
//            ft.commit();
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(@NonNull LocationInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void searchRecycler(SearchedLocationData locationDataList) {

        //处理搜索失败的情况
        if (locationDataList.getResult() == null) {
            animationView.setVisibility(View.VISIBLE);
        }

        searchAdpter.setLocationData(locationDataList.getResult());
        searchRecycler.setAdapter(searchAdpter);
    }


}
