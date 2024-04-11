package com.xupt3g.commentsview.model;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.model.CommentPageRequestBody
 *
 * @author: shallew
 * @data: 2024/3/27 22:53
 * @about: TODO 评论区页码相关请求体
 */
public class CommentPageRequestBody {
    private int homestayId;
    private int page;
    private int pageSize;

    public CommentPageRequestBody(int homestayId, int page, int pageSize) {
        this.homestayId = homestayId;
        this.page = page;
        this.pageSize = pageSize;
    }
}
