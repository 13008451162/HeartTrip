package com.xupt3g.commentsview.presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.xui.utils.XToastUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.commentsview.model.CommentsListResponse;
import com.xupt3g.commentsview.model.CommentsModelImpl;
import com.xupt3g.commentsview.model.CommentsRequest;
import com.xupt3g.commentsview.model.PostCommentData;
import com.xupt3g.commentsview.view.CommentPostImpl;
import com.xupt3g.commentsview.view.CommentsShowImpl;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xupt3g.mylibrary1.ProgressAnim;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.presenter.CommentsPresenter
 *
 * @author: shallew
 * @data: 2024/3/23 16:20
 * @about: TODO Presenter层
 */
public class CommentsPresenter {
    private CommentsModelImpl model;
    private CommentsShowImpl showView;
    private CommentPostImpl postImpl;

    public CommentsPresenter(CommentsShowImpl showView) {
        this.showView = showView;
        this.model = new CommentsRequest();
    }

    public CommentsPresenter(CommentPostImpl postImpl) {
        this.postImpl = postImpl;
        this.model = new CommentsRequest();
    }

    /**
     * TODO 将Model层的评论区数据显示在View层的Ui上
     */
    public void commentsDataShowOnUi(int houseId, int page, int pageSize) {
        CommentsListResponse listResponse = model.getCommentsList(houseId, page, pageSize);
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listResponse != null) {
                    showView.showCommentsRatingInfo(listResponse);
                    if (listResponse.getCommentsList() != null && listResponse.getCommentsList().size() > 0) {
                        showView.addCommentsToShowList(listResponse.getCommentsList());
                    } else {
                        //没有更多
                        showView.loadMoreFailed();
                    }
                }
            }
        }, 1000);
    }

    /**
     *
     * @param pictures
     * @param newComment
     * @return
     */
    public boolean postNewComment(List<LocalMedia> pictures, PostCommentData newComment) {
        //需要登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            //如果当前未登录
            XToastUtils.error("尚未登陆！");
            return false;
        }
        StringBuilder urls = new StringBuilder();
        //先将几张图片提交
        if (pictures != null) {
            int k = 0;
            for (int i = 0;i < pictures.size();i++) {
                File file = new File(pictures.get(i).getPath());
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                String url = model.uploadPicture(imagePart);
                if (url != null) {
                    urls.append(url);
                    urls.append(",");
                } else {
                    //如果有一个为空就直接返回 上传失败
                    postImpl.postFailed();
                    return false;
                }
            }
        }
        if (urls.length() > 0) {
            //有图片
            newComment.setPictureUrls(urls.toString().substring(0, urls.length() - 1));
        } else {
            newComment.setPictureUrls(null);
        }
        Date currDate = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("y年MM月留下点评");
        String time = sdf.format(currDate);
        ToastUtils.toast(time);
        newComment.setCommentedTime(time);
        boolean b = model.postNewComment(newComment);
        if (b) {
            postImpl.postSuccessful();
        } else {
            postImpl.postFailed();
        }
        return b;
    }
}
