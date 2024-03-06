package com.xupt3g.hearttrip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType;
import com.xuexiang.xutil.data.DateUtils;
import com.xupt3g.UiTools.UiTool;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private TimePickerView mTimePicker;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UiTool.setImmersionBar(this);

        findViewById(R.id.hello).setOnClickListener(view -> {
            Log.d("TAG", "onCreate: 点击了");
            ARouter.getInstance().build("/LoginRegistration/LoginRegistrationActivity").navigation();
        });
        //测试XUI框架
        showTimePicker();
    }

    /**
     * 测试XUI框架，可删除
     */
    private void showTimePicker() {
        if (mTimePicker == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.getNowDate());
            mTimePicker = new TimePickerBuilder(this, (date, v) -> XToastUtils.toast(DateUtils.date2String(date, DateUtils.HHmmss.get())))
                    .setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                    .setType(TimePickerType.TIME)
                    .setTitleText("时间选择")
                    .setDate(calendar)
                    .build();
        }
        mTimePicker.show();
    }
}