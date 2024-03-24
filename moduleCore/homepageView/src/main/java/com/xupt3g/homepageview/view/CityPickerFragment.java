package com.xupt3g.homepageview.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.xupt3g.homepageview.HomepageFragment;
import com.xupt3g.homepageview.R;
import com.xupt3g.homepageview.model.CityOfCollect;
import com.xupt3g.homepageview.model.SearchedLocationData;
import com.xupt3g.homepageview.presenter.LocationInfoContract;
import com.xupt3g.homepageview.view.Adapter.CityAdapter;
import com.xupt3g.homepageview.view.Adapter.SearchedAdpter;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

/**
 * 项目名: HeartTrip
 * 文件名: CityPickerFragment
 *
 * @author: lukecc0
 * @data:2024/3/7 下午6:59
 * @about: TODO  选择城市
 */

public class CityPickerFragment extends Fragment implements LocationInfoContract.LocationView {

    private static String TAG = CityPickerFragment.class.getSimpleName();

    private CompositeDisposable clickViewDisposable = new CompositeDisposable();

    private LocationInfoContract.Presenter mPresenter;
    private View indlaterView;
    private RecyclerView cityRecycler;
    private RecyclerView searchRecycler;
    private CityAdapter cityAdapter;
    private SearchedAdpter searchAdpter;
    private LottieAnimationView animationView;

    public static CityPickerFragment newInstance() {
        return new CityPickerFragment();
    }

    public CityPickerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        indlaterView = inflater.inflate(R.layout.city_picke_fragment, container, false);


        EditText editText = indlaterView.findViewById(R.id.search_editText);
        ImageButton backButton = indlaterView.findViewById(R.id.imageButton);


        LettersSidebar simpleSideBar = indlaterView.findViewById(R.id.simpleRipple);
        animationView = indlaterView.findViewById(R.id.viewNotFound);

        cityRecycler = indlaterView.findViewById(R.id.recycler_view);
        searchRecycler = indlaterView.findViewById(R.id.location_recycler);

        cityAdapter = new CityAdapter(getContext(), CityOfCollect.getCityList());
        searchAdpter = new SearchedAdpter();

        cityRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        searchRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        cityRecycler.setAdapter(cityAdapter);


        @io.reactivex.rxjava3.annotations.NonNull Observable<CharSequence> editObservable = RxTextView.textChanges(editText)
                .filter(sequence -> {
                    //控制选择视图和搜索视图的可见性
                    if (editText.getText().length() == 0) {
                        simpleSideBar.setVisibility(View.VISIBLE);
                        cityRecycler.setVisibility(View.VISIBLE);
                        searchRecycler.setVisibility(View.GONE);
                        animationView.setVisibility(View.GONE);
                        return false;
                    } else {
                        searchRecycler.setVisibility(View.VISIBLE);
                        simpleSideBar.setVisibility(View.GONE);
                        cityRecycler.setVisibility(View.GONE);
                        return sequence.toString().length() >= 1;
                    }
                })
                .debounce(1_000, TimeUnit.MICROSECONDS)
                .retry(2);

        //调用搜索功能
        mPresenter.locationSearch(editObservable);


        //返回上一层
        backButton.setOnClickListener(v -> {
            getActivity().finish();
        });



        cityAdapter.getClickObservable().subscribe(v ->
                Toast.makeText(getContext(), v.getText(), Toast.LENGTH_SHORT).show()
        );
        searchAdpter.getClickObservable().subscribe(v ->
                Toast.makeText(getContext(), v.getText(), Toast.LENGTH_SHORT).show()
        );

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

        return indlaterView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        HomepageFragment homeFragment = (HomepageFragment) fragmentManager.findFragmentById((int) R.layout.home_fragment);
        if (homeFragment != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.show(homeFragment);
            ft.commit();
        }
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
