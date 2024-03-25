package com.xupt3g.commentsview.model;

import com.xupt3g.mylibrary1.response.FileUploadResponse;
import com.xupt3g.mylibrary1.response.IsSuccessfulResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.model.CommentsService
 *
 * @author: shallew
 * @data: 2024/3/22 22:57
 * @about: TODO 评论的申请、发表的相关接口服务
 */
public interface CommentsService {

    /**
     * 无需登录
     * @param houseId 民宿ID
     * @return 评论列表
     */
    @FormUrlEncoded
    @POST("/travel/v1/homestayComment/commentList")
    Call<CommentsListResponse> getCommentsList(@Field("HouseId") int houseId, @Field("Page") int page, @Field("PageSize") int pageSize);

    @Multipart
    @POST("/usercenter/v1/upload")
    Call<FileUploadResponse> uploadCommentPicture(@Header("UserToken") String userToken, @Part MultipartBody.Part image);

    @POST("/travel/v1/homestayComment/addComment")
    Call<IsSuccessfulResponse> postNewComment(@Header("UserToken") String userToken, @Body PostCommentData newCommentData);

}
