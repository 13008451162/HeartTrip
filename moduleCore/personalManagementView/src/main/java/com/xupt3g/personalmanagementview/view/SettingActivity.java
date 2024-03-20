package com.xupt3g.personalmanagementview.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xupt3g.UiTools.UiTool;
import com.xupt3g.personalmanagementview.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity_setting);
        UiTool.setImmersionBar(this, false);
    }
}