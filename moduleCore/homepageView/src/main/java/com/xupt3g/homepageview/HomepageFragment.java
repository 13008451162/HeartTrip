package com.xupt3g.homepageview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xupt3g.homepageview.model.net.LocationInfoTask;
import com.xupt3g.homepageview.presenter.LocationInfoPresenter;
import com.xupt3g.homepageview.view.Carousel;
import com.xupt3g.homepageview.view.CityPickerFragment;

import java.util.ArrayList;

/**
 * 项目名: HeartTrip
 * 文件名: HomeFragment
 *
 * @author: lukecc0
 * @data:2024/3/14 下午7:29
 * @about: TODO 主展示页面
 */

@Route(path = "/homepageView/HomepageFragment")
public class HomepageFragment extends Fragment {

    private View inflaterView;

    public static HomepageFragment newInstance() {
        return new HomepageFragment();
    }

    private HomepageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflaterView = inflater.inflate(R.layout.home_fragment, container, false);


        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("https://raw.githubusercontent.com/13008451162/PicImg/main/202403201729241.jpg");
        arrayList.add("https://raw.githubusercontent.com/13008451162/PicImg/main/202403201729258.jpg");
        arrayList.add("https://raw.githubusercontent.com/13008451162/PicImg/main/202403201729937.jpg");
        arrayList.add("https://raw.githubusercontent.com/13008451162/PicImg/main/202403201729595.jpg");


        Carousel carousel = new Carousel(getContext(),inflaterView.findViewById(R.id.index_dot),inflaterView.findViewById(R.id.viewPager2));
        carousel.initViews(arrayList);


        //实现MVP绑定

        CityPickerFragment cityPickerFragment = initCityPicker();
        LocationInfoTask locationInfoTask = LocationInfoTask.getInstance();
        LocationInfoPresenter presenter = new LocationInfoPresenter(locationInfoTask, cityPickerFragment);

        cityPickerFragment.setPresenter(presenter);


        return inflaterView;
    }

    private CityPickerFragment initCityPicker() {
        FragmentManager FragmentManager = getParentFragmentManager();
        CityPickerFragment cityPickerFragment = (CityPickerFragment) FragmentManager.findFragmentById((int) R.layout.city_picke_fragment);

        if (cityPickerFragment == null) {
            cityPickerFragment = CityPickerFragment.newInstance();
            FragmentTransaction ft = FragmentManager.beginTransaction();
            ft.add(R.id.home_fragment, cityPickerFragment);

            ft.addToBackStack(null);
            ft.commit();
        }
        return cityPickerFragment;
    }
}
