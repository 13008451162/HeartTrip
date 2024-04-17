package com.xupt3g.commentsview.model;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xuexiang.xui.utils.XToastUtils;
import com.xupt3g.commentsview.PictureInfo;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xupt3g.mylibrary1.PublicRetrofit;
import com.xupt3g.mylibrary1.implservice.TopCommentData;
import com.xupt3g.mylibrary1.implservice.TopCommentGetService;
import com.xupt3g.mylibrary1.response.FileUploadResponse;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.MultipartBody;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.model.CommentsRequest
 *
 * @author: shallew
 * @data: 2024/3/23 12:44
 * @about: TODO Model进行网络申请的类
 */
@Route(path = "/commentsView/CommentsRequest")
public class CommentsRequest implements CommentsModelImpl, TopCommentGetService {
    private CommentsService commentsService;
    private MutableLiveData<CommentsListResponse> commentsListLiveData;
    private MutableLiveData<FileUploadResponse> imageUrlLiveData;
    private MutableLiveData<IsSuccessfulResponse> postSuccessLiveData;

    public CommentsRequest() {
        this.commentsService = (CommentsService) PublicRetrofit.create(CommentsService.class);
    }

//    private int totalPages = 5;

    /**
     * @param houseId  民宿Id
     * @param page     当前请求页码
     * @param pageSize 请求页码大小
     * @return 评论集合Response
     * 无需登录 返回可能为null
     */
    @Override
    public MutableLiveData<CommentsListResponse> getCommentsList(int houseId, int page, int pageSize) {
        //无需登录
        commentsListLiveData = new MutableLiveData<>();
        commentsService.getCommentsList(new CommentPageRequestBody(houseId, page, pageSize))
                .enqueue(new Callback<CommentsListResponse>() {
                    @Override
                    public void onResponse(Call<CommentsListResponse> call, Response<CommentsListResponse> response) {
                        CommentsListResponse body = response.body();
                        Log.d("TTAYVCCA", "onResponse: " + body);
                        if ((body != null && body.getCode() == 200 && "OK".equals(body.getMsg()))) {
                            commentsListLiveData.setValue(body);
                        } else {
                            commentsListLiveData.setValue(new CommentsListResponse(PublicRetrofit.getErrorMsg()));
                        }
                    }

                    @Override
                    public void onFailure(Call<CommentsListResponse> call, Throwable t) {
                        commentsListLiveData.setValue(new CommentsListResponse(PublicRetrofit.getErrorMsg()));
                        XToastUtils.error("网络请求失败！");
                    }
                });
        return commentsListLiveData;
    }

    @Override
    public MutableLiveData<FileUploadResponse> uploadPicture(MultipartBody.Part image, int index) {
        imageUrlLiveData = new MutableLiveData<>();
        //需要登陆
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            //如果未登录
            XToastUtils.error("尚未登陆！");
            imageUrlLiveData.setValue(new FileUploadResponse(PublicRetrofit.getErrorMsg()));
            return imageUrlLiveData;
        }

        commentsService.uploadCommentPicture(LoginStatusData.getUserToken().getValue(), image)
                .enqueue(new Callback<FileUploadResponse>() {
                    @Override
                    public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
                        FileUploadResponse body = response.body();
                        Log.d("commentPictureUplord", "onResponse: " + body);
                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                            imageUrlLiveData.setValue(body);
                        } else {
                            imageUrlLiveData.setValue(new FileUploadResponse(PublicRetrofit.getErrorMsg()));
                        }
                    }

                    @Override
                    public void onFailure(Call<FileUploadResponse> call, Throwable t) {
                        imageUrlLiveData.setValue(new FileUploadResponse(PublicRetrofit.getErrorMsg()));
                        XToastUtils.error("网络请求失败！");
                    }
                });
        return imageUrlLiveData;
    }

    @Override
    public MutableLiveData<IsSuccessfulResponse> postNewComment(PostCommentData newComment) {
        postSuccessLiveData = new MutableLiveData<>();
        //需要登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            //如果当前未登录
            XToastUtils.error("尚未登陆！");
            postSuccessLiveData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
            return postSuccessLiveData;
        }
        Log.d("postNewComment", "onResponse: " + newComment);
        commentsService.postNewComment(LoginStatusData.getUserToken().getValue(), newComment)
                .enqueue(new Callback<IsSuccessfulResponse>() {
                    @Override
                    public void onResponse(Call<IsSuccessfulResponse> call, Response<IsSuccessfulResponse> response) {
                        IsSuccessfulResponse body = response.body();
                        Log.d("postNewComment", "onResponse: " + body);
                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
                            postSuccessLiveData.setValue(body);
                        } else {
                            postSuccessLiveData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
                        }
                    }

                    @Override
                    public void onFailure(Call<IsSuccessfulResponse> call, Throwable t) {
                        postSuccessLiveData.setValue(new IsSuccessfulResponse(PublicRetrofit.getErrorMsg()));
                        XToastUtils.error("网络请求失败！");
                    }
                });
        return postSuccessLiveData;
    }

    /**
     * 对外暴露方法
     */
    @Override
    public MutableLiveData<TopCommentData> getTopCommentInfo(LifecycleOwner owner, int houseId) {
        MutableLiveData<CommentsListResponse> topCommentLiveData = getCommentsList(houseId, 0, 1);
        MutableLiveData<TopCommentData> resultLiveData = new MutableLiveData<>();
        topCommentLiveData.observe(owner, new Observer<CommentsListResponse>() {
            @Override
            public void onChanged(CommentsListResponse response) {
                if (response != null && !response.getMsg().equals(PublicRetrofit.getErrorMsg())) {
                    if (response.getCommentsList() != null) {
                        CommentData commentData = response.getCommentsList().get(0);
                        List<PictureInfo> pictureUrls = commentData.getPictureUrls();
                        String[] tem = new String[pictureUrls.size()];
                        int k = 0;
                        for (PictureInfo p :
                                pictureUrls) {
                            tem[k++] = p.getUrl();
                        }
                        TopCommentData topCommentData = new TopCommentData(commentData.getUserAvatar(), commentData.getCommentedTime(),
                                commentData.getUserNickname(), commentData.getCommentedScore(), commentData.getCommentContent(),
                                tem);
                        resultLiveData.setValue(topCommentData);
                    } else {
                        resultLiveData.setValue(new TopCommentData(PublicRetrofit.getErrorMsg()));
                    }
                }
            }
        });
        return resultLiveData;
    }

    @Override
    public void init(Context context) {
        if (commentsService == null) {
            this.commentsService = (CommentsService) PublicRetrofit.create(CommentsService.class);
        }
    }
}
