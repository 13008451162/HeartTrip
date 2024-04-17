package com.xupt3g.commentsview.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.UiTools.UiTool;
import com.xupt3g.commentsview.R;

@Route(path = "/commentsView/CommentsActivity")
public class CommentsActivity extends AppCompatActivity {
//    @Autowired(name = "HouseId")
    private int houseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        UiTool.setImmersionBar(this, true);
        //初始化-1
        houseId = -1;
        if (!BuildConfig.isModule) {
            //集成模式下
            Bundle extraBundle = getIntent().getBundleExtra("ExtraBundle");
            if (extraBundle != null) {
                houseId = extraBundle.getInt("HouseId");
            }
        }
//        if (houseId == -1) {
//            ToastUtils.toast("houseId注入失败");
//            finish();
//        }
        Log.d("TTAYVCCA", "show: " + houseId);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.comments_fragment_container, CommentsShowFragment.newInstance(houseId)).commit();
    }
}