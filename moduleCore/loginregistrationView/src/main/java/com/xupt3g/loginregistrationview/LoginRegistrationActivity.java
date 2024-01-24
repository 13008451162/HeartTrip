package com.xupt3g.loginregistrationview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType;
import com.xuexiang.xutil.data.DateUtils;
import com.xupt3g.UiTools.UiTool;

import java.util.Calendar;

@Route(path = "/LoginRegistration/LoginRegistrationActivity")
public class LoginRegistrationActivity extends AppCompatActivity {

    private TimePickerView mTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);

        UiTool.setImmersionBar(this);
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