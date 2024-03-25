package com.xupt3g.commentsview.model;

import android.content.Context;

import com.xuexiang.xui.utils.XToastUtils;
import com.xupt3g.commentsview.PictureInfo;
import com.xupt3g.mylibrary1.LoginStatusData;
import com.xupt3g.mylibrary1.PublicRetrofit;
import com.xupt3g.mylibrary1.implservice.TopCommentData;
import com.xupt3g.mylibrary1.implservice.TopCommentGetService;
import com.xupt3g.mylibrary1.response.FileUploadResponse;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
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
public class CommentsRequest implements CommentsModelImpl, TopCommentGetService {
    private CommentsService commentsService;
    private String pictureUrl;
    private boolean isPostSuccessful;
    private CommentsListResponse commentsListResponse;

    public CommentsRequest() {
        this.commentsService = (CommentsService) PublicRetrofit.create(CommentsService.class);
    }

    private int totalPages = 5;

    /**
     * @param houseId  民宿Id
     * @param page     当前请求页码
     * @param pageSize 请求页码大小
     * @return 评论集合Response
     * 无需登录 返回可能为null
     */
    @Override
    public CommentsListResponse getCommentsList(int houseId, int page, int pageSize) {
        //无需登录
        if (page >= totalPages) {
            return new CommentsListResponse(0, 4.0f, 4.0f, 4.5f, 4.9f, 5.0f, 5.0f,
                    500, null);
        }
        List<CommentData> comments = new ArrayList<>();
        comments.add(new CommentData("1"));
        comments.add(new CommentData("2"));
        comments.add(new CommentData("3"));
        comments.add(new CommentData("4"));
        comments.add(new CommentData("5"));
        comments.add(new CommentData("6"));
        return new CommentsListResponse(0, 4.0f, 4.0f, 4.5f, 4.9f, 5.0f, 5.0f,
                500, comments);
//        commentsService.getCommentsList(houseId, page, pageSize).enqueue(new Callback<CommentsListResponse>() {
//            @Override
//            public void onResponse(Call<CommentsListResponse> call, Response<CommentsListResponse> response) {
//                CommentsListResponse body = response.body();
//                if ((body != null && body.getCode() == 200 && "OK".equals(body.getMsg()))) {
//                    commentsListResponse = body;
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CommentsListResponse> call, Throwable t) {
//                commentsListResponse = null;
//                XToastUtils.error("网络请求失败！");
//            }
//        });
//        return commentsListResponse;
    }

    @Override
    public String uploadPicture(MultipartBody.Part image) {
        //需要登陆
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            //如果未登录
            XToastUtils.error("尚未登陆！");
            return null;
        }

        return "1212";

//        commentsService.uploadCommentPicture(LoginStatusData.getUserToken().getValue(), image)
//                .enqueue(new Callback<FileUploadResponse>() {
//                    @Override
//                    public void onResponse(Call<FileUploadResponse> call, Response<FileUploadResponse> response) {
//                        FileUploadResponse body = response.body();
//                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
//                            pictureUrl = body.getUrl();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<FileUploadResponse> call, Throwable t) {
//                        pictureUrl = null;
//                        XToastUtils.error("网络请求失败！");
//                    }
//                });
//        return pictureUrl;
    }

    @Override
    public boolean postNewComment(PostCommentData newComment) {
        //需要登录
        if (Boolean.FALSE.equals(LoginStatusData.getLoginStatus().getValue())) {
            //如果当前未登录
            XToastUtils.error("尚未登陆！");
            return false;
        }

        return true;
//        commentsService.postNewComment(LoginStatusData.getUserToken().getValue(), newComment)
//                .enqueue(new Callback<IsSuccessfulResponse>() {
//                    @Override
//                    public void onResponse(Call<IsSuccessfulResponse> call, Response<IsSuccessfulResponse> response) {
//                        IsSuccessfulResponse body = response.body();
//                        if (body != null && body.getCode() == 200 && "OK".equals(body.getMsg())) {
//                            isPostSuccessful = body.isSuccess();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<IsSuccessfulResponse> call, Throwable t) {
//                        isPostSuccessful = false;
//                        XToastUtils.error("网络请求失败！");
//                    }
//                });
//        return isPostSuccessful;
    }

    /**
     * 对外暴露方法
     */
    @Override
    public TopCommentData getTopCommentInfo(int houseId) {
        CommentsListResponse listResponse = getCommentsList(houseId, 0, 1);
        TopCommentData topCommentData = null;
        if (listResponse != null) {
            CommentData commentData = listResponse.getCommentsList().get(0);
            List<PictureInfo> pictureUrls = commentData.getPictureUrls();
            String[] tem = new String[pictureUrls.size()];
            int k = 0;
            for (PictureInfo p :
                    pictureUrls) {
                tem[k++] = p.getUrl();
            }

            topCommentData = new TopCommentData(listResponse.getScore(), commentData.getUserAvatar(), commentData.getCommentedTime(),
                    commentData.getUserNickname(), commentData.getCommentedScore(), commentData.getCommentContent(),
                    tem, commentData.getCommentContent());
        }
        return topCommentData;
    }

    @Override
    public void init(Context context) {
        if (commentsService == null) {
            this.commentsService = (CommentsService) PublicRetrofit.create(CommentsService.class);
        }
    }
}
