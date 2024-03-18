package com.xupt3g.houseinfoview;

import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import moe.codeest.enviews.ENPlayView;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.houseinfoview.VideoPlayerView
 *
 * @author: shallew
 * @data: 2024/3/10 19:42
 * @about: TODO
 */
public class VideoPlayerView extends StandardGSYVideoPlayer {

    private ImageView mCoverImage;

    public VideoPlayerView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public VideoPlayerView(Context context) {
        super(context);
    }

    public VideoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init(Context context) {
        // 不调父类的 init
        // super.init(context);
        // GSYVideoView int
        if (getActivityContext() != null) {
            this.mContext = getActivityContext();
        } else {
            this.mContext = context;
        }

        initInflate(mContext);

        mTextureViewContainer = (ViewGroup) findViewById(R.id.surface_container);
        if (isInEditMode()) {
            return;
        }
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        mAudioManager = (AudioManager) mContext.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);


        // GSYVideoControlView
        mLoadingProgressBar = findViewById(R.id.loading); //载入中动画

        mStartButton = findViewById(R.id.start); //播放按钮
        mBackButton = (ImageView) findViewById(R.id.back); //返回按键
        mLockScreen = (ImageView) findViewById(R.id.lock_screen); //锁定图标
        mFullscreenButton = (ImageView) findViewById(R.id.fullscreen); //全屏按钮

        mTitleTextView = (TextView) findViewById(R.id.title); //title
        mTotalTimeTextView = (TextView) findViewById(R.id.total); //总的时长
        mCurrentTimeTextView = (TextView) findViewById(R.id.current); //时间显示

        mBottomContainer = (ViewGroup) findViewById(R.id.layout_bottom); //顶部和底部区域
        mTopContainer = (ViewGroup) findViewById(R.id.layout_top); //顶部和底部区域

        mProgressBar = (SeekBar) findViewById(R.id.progress); //进度条
        mBottomProgressBar = (ProgressBar) findViewById(R.id.bottom_progressbar); //底部进度条

        mThumbImageViewLayout = (RelativeLayout) findViewById(R.id.thumb); //用来装封面的容器

        if (isInEditMode()) {
            return;
        }

        if (mStartButton != null) {
            mStartButton.setOnClickListener(this);
        }

        if (mFullscreenButton != null) {
            mFullscreenButton.setOnClickListener(this);
            mFullscreenButton.setOnTouchListener(this);
        }

        if (mProgressBar != null) {
            mProgressBar.setOnSeekBarChangeListener(this);
        }

        if (mBottomContainer != null) {
            mBottomContainer.setOnClickListener(this);
        }

        if (mTextureViewContainer != null) {
            mTextureViewContainer.setOnClickListener(this);
            mTextureViewContainer.setOnTouchListener(this);
        }

        if (mProgressBar != null) {
            mProgressBar.setOnTouchListener(this);
        }

        if (mThumbImageViewLayout != null) {
            mThumbImageViewLayout.setVisibility(GONE);
            mThumbImageViewLayout.setOnClickListener(this);
        }
        if (mThumbImageView != null && !mIfCurrentIsFullscreen && mThumbImageViewLayout != null) {
            mThumbImageViewLayout.removeAllViews();
            resolveThumbImage(mThumbImageView);
        }

        if (mBackButton != null) {
            mBackButton.setOnClickListener(this);
        }

        if (mLockScreen != null) {
            mLockScreen.setVisibility(GONE);
            mLockScreen.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCurrentState == CURRENT_STATE_AUTO_COMPLETE ||
                            mCurrentState == CURRENT_STATE_ERROR) {
                        return;
                    }
                    lockTouchLogic();
                    if (mLockClickListener != null) {
                        mLockClickListener.onClick(v, mLockCurScreen);
                    }
                }
            });
        }

        if (getActivityContext() != null) {
            mSeekEndOffset = CommonUtil.dip2px(getActivityContext(), 50);
        }

        // GSYBaseVideoPlayer
        mSmallClose = findViewById(R.id.small_close);

        //StandardGSYVideoPlayer
        if (mBottomProgressDrawable != null) {
            mBottomProgressBar.setProgressDrawable(mBottomProgressDrawable);
        }

        if (mBottomShowProgressDrawable != null) {
            mProgressBar.setProgressDrawable(mBottomProgressDrawable);
        }

        if (mBottomShowProgressThumbDrawable != null) {
            mProgressBar.setThumb(mBottomShowProgressThumbDrawable);
        }

        mCoverImage = new ImageView(getContext());
        mCoverImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setThumbImageView(mCoverImage);
    }

    /**
     * 设置封面图片
     * <p>
     * GlideApp.with(context).load(imageUrl).into(player.getCoverView());
     *
     * @return
     */
    public ImageView getCoverView() {
        return mCoverImage;
    }

    @Override
    public int getLayoutId() {
        return R.layout.houseinfo_lib_video_layout;
    }

    /**
     * 开始播放
     */
    @Override
    public void startPrepare() {
        super.startPrepare();
    }

    /**
     * 自定义 开始/ 暂停/ 错误的图标
     */
    @Override
    protected void updateStartImage() {
        if (mStartButton instanceof ENPlayView) {
            ENPlayView enPlayView = (ENPlayView) mStartButton;
            enPlayView.setDuration(500);
            if (mCurrentState == CURRENT_STATE_PLAYING) {
                enPlayView.play();
            } else if (mCurrentState == CURRENT_STATE_ERROR) {
                enPlayView.pause();
            } else {
                enPlayView.pause();
            }
        } else if (mStartButton instanceof ImageView) {
            ImageView imageView = (ImageView) mStartButton;
            if (mCurrentState == CURRENT_STATE_PLAYING) {
                //暂停
                imageView.setImageResource(R.drawable.houseinfo_icon_video_pause);
            } else if (mCurrentState == CURRENT_STATE_ERROR) {
                //错误
                imageView.setImageResource(R.drawable.houseinfo_icon_video_error);
            } else {
                //播放
                imageView.setImageResource(R.drawable.houseinfo_icon_video_play);
            }
        }
    }

    /**
     * 是否处于暂停状态
     *
     * @return
     */
    public boolean isInInPause() {
        return (mCurrentState >= 0 && mCurrentState == CURRENT_STATE_PAUSE);
    }
}