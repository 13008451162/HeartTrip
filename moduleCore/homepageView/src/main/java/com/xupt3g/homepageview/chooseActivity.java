package com.xupt3g.homepageview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.launcher.ARouter;

public class chooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ARouter.getInstance().build("/houseInfoView/ChooseTimeCalendarActivity").navigation(this, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String[] timeResult = data.getStringArrayExtra("data_return");

                Intent intent = getIntent().putExtra("return_data1", timeResult);
                setResult(RESULT_OK, intent);
                finish();
            }
        }else {
            finish();
        }
    }

    // 重写 onDestroy() 方法，在 Activity 被销毁时更新状态
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}