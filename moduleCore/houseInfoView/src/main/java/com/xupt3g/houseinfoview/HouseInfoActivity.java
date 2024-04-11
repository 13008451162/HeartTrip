package com.xupt3g.houseinfoview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;


import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.xupt3g.UiTools.UiTool;
import com.xupt3g.houseinfoview.view.HouseInfoFragment;

@Route(path = "/houseInfoView/HouseInfoActivity")
public class HouseInfoActivity extends AppCompatActivity {
    @Autowired(name = "HouseId")
    public int houseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_info);
        if (!BuildConfig.isModule) {
            ARouter.getInstance().inject(this);
        }
        UiTool.setImmersionBar(this, true);
        FrameLayout frameLayout = findViewById(R.id.houseinfo_fragment_container);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.houseinfo_fragment_container, HouseInfoFragment.newInstance(houseId)).commit();

    }

    public int getHouseId() {
        return houseId;
    }
}