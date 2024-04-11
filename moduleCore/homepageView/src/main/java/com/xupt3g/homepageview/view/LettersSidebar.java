package com.xupt3g.homepageview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.xupt3g.homepageview.R;

/**
 * 项目名: HeartTrip
 * 文件名: SimpleSideBar
 *
 * @author: lukecc0
 * @data:2024/3/13 下午9:00
 * @about: TODO  实现一个简单的字母侧边栏
 */

public class LettersSidebar extends View {

    private static final String TouchTag = "NO";

    //初始触摸状态
    private String lastTouchedCharacter = TouchTag;

    // 索引字母数组
    private String[] alphabet = {
            "热门城市",
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z"
    };

    // 当前选择的索引字母的下标
    private int currentChoosenAlphabetIndex = -1;


    OnLetterTouchedChangeListener onLetterTouchedChangeListener;

    // 画笔
    private Paint mPaint = new Paint();

    // 索引字母绘制大小
    private int indexTextSize = 36;
    private TextView locationTextView;
    private ViewGroup locationPargentGroup;

    //位置提示字母大小
    private int locationTextSize = 24;
    private Handler handler;


    /**
     * 索引列表的触摸事假监听
     *
     * @author lukecc0
     * @date 2024/03/14
     */
    public interface OnLetterTouchedChangeListener {
        void onTouchedLetterChange(String letterTouched);

    }

    public LettersSidebar(Context context) {
        super(context);
    }

    public LettersSidebar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        handler = new Handler();
    }

    public LettersSidebar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LettersSidebar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewHeight = getHeight();
        int viewWidth = getWidth();

        //每个控件的高度
        int singleHeight = viewHeight / alphabet.length;

        for (int i = 0; i < alphabet.length; i++) {

            //设置颜色、大小、位置、抗锯齿、字体粗细
            mPaint.setTextSize(indexTextSize);
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setAntiAlias(true);
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);

//            // 选择的索引和循环索引一致
//            if (i == currentChoosenAlphabetIndex) {
//                // 设置画笔颜色，绘制文字大小和加粗
//                mPaint.setColor(Color.GREEN);
//                mPaint.setTextSize(indexTextSize);
//                mPaint.setFakeBoldText(true);
//            }

            // 索引字母的相对于控件的x坐标，此处算法结果为居中
            float xPos = (float) viewWidth / 2;
            // 索引字母的相对于控件的y坐标，索引字母的高度乘以索引字母下标+1即为y坐标
            float yPos = singleHeight * i + singleHeight;

            // 绘制索引字母
            if (i == 0){
                canvas.drawText("热门", xPos, yPos, mPaint);
            }else {
                canvas.drawText(alphabet[i], xPos, yPos, mPaint);
            }
            // 重置画笔，为绘制下一个索引字母做准备
            mPaint.reset();

        }
    }

    /**
     * 索引栏点击事件的回调
     * @param onLetterTouchedChangeListener
     */
    public void setOnLetterTouchedChangeListener(OnLetterTouchedChangeListener onLetterTouchedChangeListener) {
        this.onLetterTouchedChangeListener = onLetterTouchedChangeListener;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        int action = event.getAction();

        float touchYPos = event.getY();

        // 确保触摸坐标在合法范围内
        if (touchYPos < 0 || touchYPos > getHeight()) {
            return false;
        }

        // 计算触摸的字母的下标，点击位置/控件高度*索引数组长度
        int index = (int) (touchYPos / getHeight() * alphabet.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                //显示当前位置弹窗
                if (lastTouchedCharacter != alphabet[index]) {
                    showCurrentLocation(alphabet[index]);
                    lastTouchedCharacter = alphabet[index];
                }

                currentChoosenAlphabetIndex = index;

                if (onLetterTouchedChangeListener != null) {
                    onLetterTouchedChangeListener.onTouchedLetterChange(alphabet[index]);
                }

                invalidate();
                break;

            case MotionEvent.ACTION_UP:

                lastTouchedCharacter = TouchTag;
                setBackgroundResource(R.color.white);
                currentChoosenAlphabetIndex = -1;
                invalidate();
                break;

            default:
                break;
        }

        return true;
    }


    /**
     * 在屏幕中间显示当前位置的Text
     *
     * @param location 需要显示信息
     */
    private void showCurrentLocation(String location) {

        if (locationPargentGroup != null && locationTextView != null) {
            locationPargentGroup.removeView(locationTextView);
        }

        //创建一个容器
        locationPargentGroup = (ViewGroup) getParent();

        locationTextView = new TextView(getContext());
        locationTextView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.circle_background));
        locationTextView.setTextSize(locationTextSize);
        locationTextView.setTextColor(Color.WHITE);
        locationTextView.setGravity(Gravity.CENTER);
        locationTextView.setText(location);

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout
                .LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT
                , ConstraintLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;

        locationPargentGroup.addView(locationTextView, layoutParams);

        // 创建一个 Handler，并在 2 秒后移除 TextView
        handler.postDelayed(() -> {
            locationPargentGroup.removeView(locationTextView);
            //保证只有一个任务执行
            handler.removeCallbacksAndMessages(null);
        }, 2_000);

    }

    /**
     * 更新侧边栏字母位置
     *
     * @param letter
     */
    public void setTouchedLetter(String letter) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i].equals(letter)) {
                currentChoosenAlphabetIndex = i;
                invalidate();
                break;
            }
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // 移除所有挂起的消息和任务
        handler.removeCallbacksAndMessages(null);
    }


}
