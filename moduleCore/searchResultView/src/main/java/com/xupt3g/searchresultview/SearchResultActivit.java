package com.xupt3g.searchresultview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.bottomsheet.BottomSheetDialog;


@Route(path = "/searchResultView/SearchResultActivit")
public class SearchResultActivit extends AppCompatActivity {


    public final int REQUEST_CODE = 1;

    @Autowired(name = "position")
    public String position;

    @Autowired(name = "date")
    public String date;


    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ARouter.getInstance().inject(this);


        ARouter.getInstance().build("/homepageView/SearchActivity").navigation(this,REQUEST_CODE);




        Log.d("TAG", "onCreate: " + position + "   " + date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra("data_return");
            Log.d("TAG", "onActivityResult: " + result);
        }
    }
}