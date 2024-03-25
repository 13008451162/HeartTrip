package com.xupt3g.homepageview.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.xupt3g.homepageview.R;
import com.xupt3g.homepageview.model.net.LocationInfoTask;
import com.xupt3g.homepageview.presenter.LocationInfoPresenter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * @author lukecc0
 * @date 2024/03/20
 * TODO 地址搜索界面
 */
public class SearchActivity extends AppCompatActivity {

    private PublishSubject publishSubject = PublishSubject.create();
    private static CityPickerFragment cityPickerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //实现MVP绑定
        CityPickerFragment cityPickerFragment = initCityPicker();
        LocationInfoTask locationInfoTask = LocationInfoTask.getInstance();
        LocationInfoPresenter presenter = new LocationInfoPresenter(locationInfoTask, cityPickerFragment);

        cityPickerFragment.setPresenter(presenter);

    }


    private CityPickerFragment initCityPicker() {
        FragmentManager FragmentManager = getSupportFragmentManager();
        cityPickerFragment = (CityPickerFragment) FragmentManager.findFragmentById((int) R.layout.city_picke_fragment);

        if (cityPickerFragment == null) {
            cityPickerFragment = CityPickerFragment.newInstance();
            FragmentTransaction ft = FragmentManager.beginTransaction();
            ft.add(R.id.home_fragment, cityPickerFragment);
            ft.commit();
        }
        return cityPickerFragment;
    }

}