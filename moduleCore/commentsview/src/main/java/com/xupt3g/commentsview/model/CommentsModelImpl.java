package com.xupt3g.commentsview.model;

import okhttp3.MultipartBody;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.model.CommentsModelImpl
 *
 * @author: shallew
 * @data: 2024/3/23 12:51
 * @about: TODO Model层对外暴露接口
 */
public interface CommentsModelImpl {
    /**
     * 无需登录
     * @param houseId 民宿Id
     * @return 评论列表
     */
    CommentsListResponse getCommentsList(int houseId, int page, int pageSize);

    /**
     * 需要登录
     * @return 上传的图片的url
     */
    String uploadPicture(MultipartBody.Part image);

    boolean postNewComment(PostCommentData postCommentData);


}
