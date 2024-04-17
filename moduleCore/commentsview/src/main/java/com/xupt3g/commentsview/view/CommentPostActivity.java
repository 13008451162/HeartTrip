package com.xupt3g.commentsview.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.libbase.BuildConfig;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xui.widget.button.ButtonView;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.progress.ratingbar.ScaleRatingBar;
import com.xupt3g.UiTools.UiTool;
import com.xupt3g.commentsview.CommentsPostPictureAdapter;
import com.xupt3g.commentsview.GlideEngine;
import com.xupt3g.commentsview.PictureInfo;
import com.xupt3g.commentsview.R;
import com.xupt3g.commentsview.model.PostCommentData;
import com.xupt3g.commentsview.presenter.CommentsPresenter;
import com.xupt3g.mylibrary1.ProgressAnim;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.view.CommentsShowFragment
 *
 * @author: shallew
 * @data: 2024/3/20 21:04
 * @about: TODO 评论发表页面
 */
@Route(path = "/commentsView/CommentPostActivity")
public class CommentPostActivity extends AppCompatActivity implements CommentPostImpl {
    /**
     * 民宿ID
     */
//    @Autowired(name = "houseId")
    private int houseId;
    /**
     * 民宿评分
     */
    private ScaleRatingBar scoreRating;
    /**
     * 整洁程度
     */
    private ScaleRatingBar tidyRating;
    /**
     * 交通位置
     */
    private ScaleRatingBar trafficRating;
    /**
     * 安全程度
     */
    private ScaleRatingBar securityRating;
    /**
     * 餐饮体验
     */
    private ScaleRatingBar foodRating;
    /**
     * 综合性价比
     */
    private ScaleRatingBar costRating;
    private ImageView backButton;
    private EditText contentEdit;
    private ButtonView clearPictures;
    private ImageView addMorePicture;
    private ImageView postComment;
    private List<LocalMedia> currPictureList;
    private TextView editCurrTextNum;
    private TextView editMaxTextNum;
    private List<PictureInfo> currPicturesInfo;
    private RecyclerView pictureRecycler;
    private CommentsPostPictureAdapter pictureAdapter;
    private ImageView clearEditText;

    private final int EDIT_MAX_TEXT_NUM = 200;
    private CommentsPresenter presenter;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_post);
        UiTool.setImmersionBar(this, false);
        if (!BuildConfig.isModule) {
            //集成模式下
            Bundle bundle = getIntent().getBundleExtra("bundle");
            if (bundle != null) {
                houseId = bundle.getInt("HouseId");
            }
        }

        if (getIntent() != null) {
            int id = getIntent().getIntExtra("HouseId", -1);
            if (id != -1) {
                houseId = id;
            }
        }
        Log.d("TTAYVCCA", "post: " + houseId);
        instantiationView();
        //添加照片的按钮监听
        addMorePicture.setOnClickListener(view -> {
            addPicture();
        });
        //清空照片的按钮监听
        clearPictures.setOnClickListener(view -> {
            currPicturesInfo.clear();
            currPictureList.clear();
            pictureAdapter.notifyDataSetChanged();
            clearPictures.setVisibility(View.GONE);
        });
        //输入监听
        contentEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = charSequence.length();
                if (length == 0) {
                    clearEditText.setVisibility(View.GONE);
                } else {
                    clearEditText.setVisibility(View.VISIBLE);
                }
                if (length > EDIT_MAX_TEXT_NUM) {
                    editCurrTextNum.setTextColor(Color.RED);
                    editMaxTextNum.setTextColor(Color.RED);
                    editCurrTextNum.setText(length + "");
                } else {
                    editCurrTextNum.setTextColor(Color.parseColor("#6f7173"));
                    editMaxTextNum.setTextColor(Color.parseColor("#6f7173"));
                    editCurrTextNum.setText(length + "");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //发送按钮监听
        postComment.setOnClickListener(view -> {
            if (contentEdit.getText().length() > EDIT_MAX_TEXT_NUM) {
                XToastUtils.error("您最大可添加200字！");
            } else if (contentEdit.getText().length() == 0) {
                XToastUtils.error("发表的内容不能为空！");
            } else {
                //发送到服务端
                new MaterialDialog.Builder(this)
                        .title("提示")
                        .content("是否发布评论")
                        .positiveText("确认")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ProgressAnim.showProgress(CommentPostActivity.this);
                                presenter.postNewComment(currPictureList, new PostCommentData(houseId,
                                        contentEdit.getText().toString(), scoreRating.getRating(),
                                        tidyRating.getRating(), trafficRating.getRating(), securityRating.getRating(),
                                        foodRating.getRating(), costRating.getRating()));
                            }
                        }).onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            }
                        }).show();
            }
        });
        //清空输入按钮监听
        clearEditText.setOnClickListener(view -> {
            contentEdit.setText("");
        });
    }

    private final int PICTURE_MAX_NUM = 10;


    private void addPicture() {
        if (currPictureList.size() >= PICTURE_MAX_NUM) {
            XToastUtils.error("最多10个");
            return;
        }
        PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .setMaxSelectNum(PICTURE_MAX_NUM - currPictureList.size())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        currPictureList.addAll(result);
                        pictureAdapter = new CommentsPostPictureAdapter(currPictureList, CommentPostActivity.this);
                        pictureAdapter.setIsEmptyListener(new CommentsPostPictureAdapter.OnPicturesIsEmptyListener() {
                            @Override
                            public void picturesIsEmpty() {
                                //图片被一个一个清空了
                                clearPictures.setVisibility(View.GONE);
                            }
                        });
                        pictureRecycler.setAdapter(pictureAdapter);
                        pictureRecycler.setLayoutManager(new GridLayoutManager(CommentPostActivity.this, 3));
                        if (currPictureList.size() > 0 && clearPictures.getVisibility() == View.GONE) {
                            clearPictures.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    /**
     * TODO 将控件实例化
     */
    private void instantiationView() {
        currPictureList = new ArrayList<>();
        currPicturesInfo = new ArrayList<>();
        scoreRating = (ScaleRatingBar) findViewById(R.id.comments_post_score_rating);
        tidyRating = (ScaleRatingBar) findViewById(R.id.comments_post_tidy_rating);
        trafficRating = (ScaleRatingBar) findViewById(R.id.comments_post_traffic_rating);
        securityRating = (ScaleRatingBar) findViewById(R.id.comments_post_security_rating);
        foodRating = (ScaleRatingBar) findViewById(R.id.comments_post_food_rating);
        costRating = (ScaleRatingBar) findViewById(R.id.comments_post_cost_rating);
        backButton = (ImageView) findViewById(R.id.comments_post_back);
        backButton.setOnClickListener(view -> finish());
        contentEdit = (EditText) findViewById(R.id.comments_post_comment_input);
        clearPictures = (ButtonView) findViewById(R.id.comments_post_clear_button);
        clearPictures.setVisibility(View.GONE);
        addMorePicture = (ImageView) findViewById(R.id.comments_post_add_picture);
        postComment = (ImageView) findViewById(R.id.comments_post_send_comment);
        editCurrTextNum = (TextView) findViewById(R.id.comments_post_current_text_num);
        editMaxTextNum = (TextView) findViewById(R.id.comments_post_max_text_num);
        pictureRecycler = (RecyclerView) findViewById(R.id.comments_post_picture_recycler);
        clearEditText = (ImageView) findViewById(R.id.comments_post_clear_edittext);
        clearEditText.setVisibility(View.GONE);
        presenter = new CommentsPresenter(this, this);
    }


    @Override
    public void postSuccessful() {
        ProgressAnim.hideProgress();
        XToastUtils.success("发布成功！");
    }

    @Override
    public void postFailed() {
        ProgressAnim.hideProgress();
        XToastUtils.error("发布失败！");
    }
}