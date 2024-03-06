package com.xupt3g.browsinghistoryview.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xupt3g.UiTools.UiTool;
import com.xupt3g.browsinghistoryview.R;

@Route(path = "/browsingHistoryView/BrowsingHistoryActivity")
public class BrowsingHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity_browsing_history);

        UiTool.setImmersionBar(this, true);
    }


}