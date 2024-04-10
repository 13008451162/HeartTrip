package com.xupt3g.commentsview.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
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
import com.xupt3g.mylibrary1.PublicRetrofit;
import com.xupt3g.mylibrary1.response.FileUploadResponse;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    private Context mContext;

    public CommentsPresenter(CommentsShowImpl showView, Context context) {
        this.showView = showView;
        this.model = new CommentsRequest();
        this.mContext = context;
    }

    public CommentsPresenter(CommentPostImpl postImpl, Context context) {
        this.postImpl = postImpl;
        this.model = new CommentsRequest();
        this.mContext = context;
    }

    /**
     * TODO 将Model层的评论区数据显示在View层的Ui上
     */
    public void commentsDataShowOnUi(int houseId, int page, int pageSize) {
        Log.d("commentsDataShowOnUi", "commentsDataShowOnUi: " + page + " " + pageSize);
        MutableLiveData<CommentsListResponse> commentsListLiveData = model.getCommentsList(houseId, page, pageSize);
        commentsListLiveData.observe((LifecycleOwner) showView, new Observer<CommentsListResponse>() {
            @Override
            public void onChanged(CommentsListResponse response) {
                Log.d("commentsDataShowOnUi", "onChanged: " + response);
                if (response != null && !response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    if (page == 1) {
                        showView.showCommentsRatingInfo(response);
                    }
                    if (response.getCommentsList() != null && response.getCommentsList().size() > 0) {
                        showView.addCommentsToShowList(response.getCommentsList());
                    } else {
                        //没有更多
                        XToastUtils.error("没有评论数据！");
                        showView.loadMoreFailed();
                    }
                } else if (response != null && response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    XToastUtils.error("网络请求！");
                    ProgressAnim.hideProgress();
                }
                commentsListLiveData.removeObservers((LifecycleOwner) showView);
            }
        });
    }

    private MutableLiveData<Boolean> uploadResultLiveData;
    private StringBuilder urls;
    private int currUrlsCount;

    /**
     * @param pictures
     * @param newComment
     * @return TODO 发表新的评论
     */
    public void postNewComment(List<LocalMedia> pictures, PostCommentData newComment) {
        uploadResultLiveData = new MutableLiveData<>();
        currUrlsCount = 0;
        //需要登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            //如果当前未登录
            XToastUtils.error("尚未登陆！");
            return;
        }
        urls = new StringBuilder();
        //先将几张图片提交
        if (pictures != null && pictures.size() > 0) {
            //递归调用 上传单张图片
//            for (int i = 0; i < pictures.size(); i++) {
                singlePictureUpload(pictures, 0);
//            }
            //监听上传结果
            uploadResultLiveData.observe((LifecycleOwner) postImpl, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if (aBoolean.equals(Boolean.FALSE)) {
                        //有一张上传失败返回失败
                        postImpl.postFailed();
                    } else {
                        if (currUrlsCount == pictures.size()) {
                            //最后一张图片也上传成功 调用发布评论的方法
                            newComment.setPictureUrls(urls.substring(0, urls.length() - 1));
                            postCommentInfo(newComment);
                        }
                    }
                }
            });
        } else {
            //没有要上传的图片
            newComment.setPictureUrls("");
            postCommentInfo(newComment);
        }
    }

    private static final int COMPRESSION_QUALITY = 60;

    private void singlePictureUpload(List<LocalMedia> pictures, int index) {
        if (index >= pictures.size()) {
            return;
        }

        RequestOptions requestOptions = new RequestOptions()
                // 跳过内存缓存
                .skipMemoryCache(true)
                .format(DecodeFormat.PREFER_RGB_565)
                // 不使用磁盘缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(mContext).asBitmap().load(pictures.get(index).getPath())
                .apply(requestOptions)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //将图片转化为Bitmap 再由Bitmap进行压缩
                        saveCompressedImage(resource, pictures, index);
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    /**
     * TODO 对图片进行压缩 以及网络上传请求
     * @param bitmap
     * @param pictures
     * @param index
     */
    private void saveCompressedImage(Bitmap bitmap, List<LocalMedia> pictures, int index ) {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(storageDir, "compressed_image.jpg");
        Log.d("commentPictureUplord", "saveCompressedImage: " + file.getAbsolutePath());
        try {
            if (file.exists()){
                //如果该目录存在，就会删除该目录下的东西。
                file.delete();
            }
            //如果该目录是空的，就会创建一个文件用于保存拍摄的图片
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_QUALITY, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        File file = new File("/storage/emulated/0/Pictures/compressed_image.jpg");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("myFile", file.getName(), requestBody);
        MutableLiveData<FileUploadResponse> fileUploadLiveData = model.uploadPicture(imagePart, index);
        fileUploadLiveData.observe((LifecycleOwner) postImpl, new Observer<FileUploadResponse>() {
            @Override
            public void onChanged(FileUploadResponse response) {
                if (response != null && !response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    if (response.getUrl() != null) {
                        urls.append(response.getUrl());
                        urls.append(",");
                        currUrlsCount++;
                        if (index == pictures.size() - 1) {
                            //如果是最后一张图片上传成功
                            uploadResultLiveData.setValue(true);
                        }
                        singlePictureUpload(pictures, index + 1);
                    } else {
                        //如果有一个为空就直接返回 上传失败
                        uploadResultLiveData.setValue(false);
                    }
                } else {
                    ProgressAnim.hideProgress();
                    XToastUtils.error("上传失败！");
                }
            }
        });
    }

    /**
     * TODO 调用网络请求完成评论发表
     */
    private void postCommentInfo(PostCommentData newComment) {
        //设置发表时间
        Date currDate = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("y年MM月留下点评");
        String time = sdf.format(currDate);
        ToastUtils.toast(time);
        newComment.setCommentedTime(time);
        newComment.setAvatar("");
        newComment.setNickname("");
        //调用网络请求
        MutableLiveData<IsSuccessfulResponse> postSuccessLiveData = model.postNewComment(newComment);
        postSuccessLiveData.observe((LifecycleOwner) postImpl, new Observer<IsSuccessfulResponse>() {
            @Override
            public void onChanged(IsSuccessfulResponse response) {
                if (response != null && !response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    if (response.isSuccess()) {
                        postImpl.postSuccessful();
                    } else {
                        postImpl.postFailed();
                    }
                } else {
                    postImpl.postFailed();
                }
            }
        });
    }
}
