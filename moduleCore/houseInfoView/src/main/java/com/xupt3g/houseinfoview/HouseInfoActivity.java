package com.xupt3g.houseinfoview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.xupt3g.UiTools.UiTool;

public class HouseInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_info);

        UiTool.setImmersionBar(this, true);
    }
}