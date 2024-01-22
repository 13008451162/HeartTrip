package com.xupt3g.hearttrip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import com.alibaba.android.arouter.launcher.ARouter;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.hello).setOnClickListener(view -> {
            Log.d("TAG", "onCreate: 点击了");
            ARouter.getInstance().build("/LoginRegistration/LoginRegistrationActivity").navigation();

        });
    }
}