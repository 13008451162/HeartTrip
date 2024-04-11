package com.xupt3g.houseinfoview.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xupt3g.UiTools.UiTool;
import com.xupt3g.houseinfoview.R;

/**
 * TODO 附近景点详情网页
 */
public class AttractionDetailWebActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail_web);

        findViewById(R.id.attraction_detail_back).setOnClickListener(view -> finish());
        WebView webView = (WebView) findViewById(R.id.attraction_detail_webView);
        String detailUrl = getIntent().getStringExtra("detailUrl");
        if (detailUrl == null || "".equals(detailUrl)) {
            finish();
        }

        // 设置 WebView 的属性
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);

        UiTool.setImmersionBar(this, false);

        // 加载网页
        webView.loadUrl(detailUrl);
    }
}