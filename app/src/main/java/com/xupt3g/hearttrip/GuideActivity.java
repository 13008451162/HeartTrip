package com.xupt3g.hearttrip;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroCustomLayoutFragment;
import com.github.appintro.AppIntroPageTransformerType;
/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.hearttrip.GuideActivity
 *
 * @author: shallew
 * @data:2024/1/29 0:07
 * @about: TODO 引导页活动
 */
public class GuideActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkFirstLaunch();

        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.custom_layout_1));
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.custom_layout_2));
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.custom_layout_3));

        //淡入淡出
        setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);

//        //颜色过渡 需设置backgroundColor
//        setColorTransitionsEnabled(true);

        //向导模式
        setWizardMode(true);

        //沉浸模式
        setImmersiveMode();

        //禁用系统回退
        setSystemBackButtonLocked(true);

        setProgressIndicator();

        setSeparatorColor(Color.TRANSPARENT);

        setDoneText("OK");
        setDoneTextTypeface(R.font.app_font_2);
    }

    private void checkFirstLaunch() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isFirstLaunch = sharedPreferences.getBoolean("first_launch", true);

        if (isFirstLaunch) {
            // 第一次启动，显示引导页
//            Intent intent = new Intent(this, GuideActivity.class);
//            startActivity(intent);
            // 将标志位设置为false
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("first_launch", false);
            editor.apply();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        //点击跳过直接进入MainActivity。但是禁用跳过
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        //点击完成（OK）直接进入MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}