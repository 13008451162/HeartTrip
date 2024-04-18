package com.xupt3g.houseinfoview.view;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.xuexiang.xui.utils.XToastUtils;
import com.xupt3g.UiTools.UiTool;
import com.xupt3g.houseinfoview.R;

import java.util.List;

@Route(path = "/houseInfoView/ChooseTimeCalendarActivity")
public class ChooseTimeCalendarActivity extends AppCompatActivity implements
        CalendarView.OnCalendarInterceptListener,
        CalendarView.OnCalendarRangeSelectListener,
        CalendarView.OnMonthChangeListener,
        View.OnClickListener {
    TextView mTextLeftDate;
    TextView mTextLeftWeek;

    TextView mTextRightDate;
    TextView mTextRightWeek;

    TextView mTextTotalNight;

    CalendarView mCalendarView;

    private int mCalendarHeight;
    private TextView mTextYearMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time_calendar);
        UiTool.setImmersionBar(this, true);

        initView();

    }

    private void initData() {
//        int year = mCalendarView.getCurYear();
//        int month = mCalendarView.getCurMonth();
//        Map<String, Calendar> map = new HashMap<>();
//        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
//                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
//        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
//                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
//        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "议").toString(),
//                getSchemeCalendar(year, month, 9, 0xFFdf1356, "议"));
//        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "记").toString(),
//                getSchemeCalendar(year, month, 13, 0xFFedc56d, "记"));
//        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记").toString(),
//                getSchemeCalendar(year, month, 14, 0xFFedc56d, "记"));
//        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假").toString(),
//                getSchemeCalendar(year, month, 15, 0xFFaacc44, "假"));
//        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记").toString(),
//                getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记"));
//        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假").toString(),
//                getSchemeCalendar(year, month, 25, 0xFF13acf0, "假"));
//        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多").toString(),
//                getSchemeCalendar(year, month, 27, 0xFF13acf0, "多"));
//        //此方法在巨大的数据量上不影响遍历性能，推荐使用
//        mCalendarView.setSchemeDate(map);

    }

    /**
     * @return 添加事件获取日历
     */
    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        //如果单独标记颜色、则会使用这个颜色
        calendar.setSchemeColor(color);
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    @SuppressLint("DefaultLocale")
    private void initView() {
        mTextLeftDate = findViewById(R.id.tv_left_date);
        mTextLeftWeek = findViewById(R.id.tv_left_week);
        mTextRightDate = findViewById(R.id.tv_right_date);
        mTextRightWeek = findViewById(R.id.tv_right_week);

        mTextTotalNight = findViewById(R.id.tv_total_night);
        mTextTotalNight.setVisibility(View.GONE);

        mCalendarView = findViewById(R.id.calendarView);
        mCalendarView.setOnCalendarRangeSelectListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        //设置日期拦截事件，当前有效
        mCalendarView.setOnCalendarInterceptListener(this);
        mTextYearMonth = findViewById(R.id.tv_calendar_title_year_month);

        findViewById(R.id.iv_clear).setOnClickListener(this);
        findViewById(R.id.iv_reduce).setOnClickListener(this);
        findViewById(R.id.iv_increase).setOnClickListener(this);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        findViewById(R.id.tv_title).setOnClickListener(this);

        mCalendarHeight = dipToPx(46);
        mTextYearMonth.setText(String.format("%d年 %d月", mCalendarView.getCurYear(), mCalendarView.getCurMonth()));
        mCalendarView.setRange(mCalendarView.getCurYear(), mCalendarView.getCurMonth(), mCalendarView.getCurDay(),
                mCalendarView.getCurYear() + 1, mCalendarView.getCurMonth(), mCalendarView.getCurDay());
        mCalendarView.post(new Runnable() {
            @Override
            public void run() {
                mCalendarView.scrollToCurrent();
            }
        });
    }

    private int dipToPx(int dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_title) {

        } else if (id == R.id.iv_clear) {
            mCalendarView.clearSelectRange();
            mTextLeftWeek.setText("开始日期");
            mTextRightWeek.setText("结束日期");
            mTextLeftDate.setText("");
            mTextRightDate.setText("");
            mTextTotalNight.setVisibility(View.GONE);

            //mCalendarView.setSelectCalendarRange(2018,10,13,2018,10,13);
        } else if (id == R.id.iv_reduce) {
            mCalendarHeight -= dipToPx(8);
            if (mCalendarHeight <= dipToPx(46)) {
                mCalendarHeight = dipToPx(46);
            }
            mCalendarView.setCalendarItemHeight(mCalendarHeight);
        } else if (id == R.id.iv_increase) {
            mCalendarHeight += dipToPx(8);
            if (mCalendarHeight >= dipToPx(90)) {
                mCalendarHeight = dipToPx(90);
            }
            mCalendarView.setCalendarItemHeight(mCalendarHeight);
        } else if (id == R.id.tv_commit) {
            List<Calendar> calendars = mCalendarView.getSelectCalendarRange();
            if (calendars == null || calendars.size() == 0) {
                return;
            }

            Intent intent = getIntent();
            int size = calendars.size();
            Calendar startCalender = calendars.get(0);
            Calendar endCalender = calendars.get(size - 1);
            if (intent != null) {
                if (startCalender.getYear() == endCalender.getYear()) {
                    //如果起始和结束都是同一年的
                    intent.putExtra("data_return", new String[]{
                            String.format("%d月%d日", startCalender.getMonth(), startCalender.getDay()),
                            String.format("%d月%d日", endCalender.getMonth(), endCalender.getDay()),
                            String.format("共%d晚", size)});
                } else {
                    intent.putExtra("data_return", new String[]{
                            String.format("%d年%d月%d日", startCalender.getYear(), startCalender.getMonth(), startCalender.getDay()),
                            String.format("%d年%d月%d日", endCalender.getYear(), endCalender.getMonth(), endCalender.getDay()),
                            String.format("共%d晚", size - 1)});
                }
                setResult(RESULT_OK, intent);
                Toast.makeText(this, String.format("选择了%s个日期: %s —— %s", calendars.size(),
                                calendars.get(0).toString(), calendars.get(calendars.size() - 1).toString()),
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                XToastUtils.error("日期返回失败 getIntent()为空");
            }
        }
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    private long getCurrentDayMill() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.HOUR, 0);
        calendar.set(java.util.Calendar.MINUTE, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        return false;
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
        Toast.makeText(this,
                calendar.toString() + (isClick ? "拦截不可点击" : "拦截设定为无效日期"),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarSelectOutOfRange(Calendar calendar) {
        // TODO: 2018/9/13 超出范围提示
    }

    @Override
    public void onSelectOutOfRange(Calendar calendar, boolean isOutOfMinRange) {
        Toast.makeText(this,
                calendar.toString() + (isOutOfMinRange ? "小于最小选择范围" : "超过最大选择范围"),
                Toast.LENGTH_SHORT).show();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onCalendarRangeSelect(Calendar calendar, boolean isEnd) {
        if (!isEnd) {
            mTextLeftDate.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
            mTextLeftWeek.setText(WEEK[calendar.getWeek()]);
            mTextRightWeek.setText("结束日期");
            mTextRightDate.setText("");
            mTextTotalNight.setVisibility(View.GONE);
        } else {
            mTextRightDate.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
            mTextRightWeek.setText(WEEK[calendar.getWeek()]);
            mTextTotalNight.setText(String.format("已选择%d晚", mCalendarView.getSelectCalendarRange().size() - 1));
            mTextTotalNight.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onMonthChange(int year, int month) {
        mTextYearMonth.setText(String.format("%d年 %d月", year, month));
    }

    private static final String[] WEEK = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
}