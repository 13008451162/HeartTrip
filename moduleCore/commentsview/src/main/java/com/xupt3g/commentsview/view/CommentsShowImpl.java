package com.xupt3g.commentsview.view;

import com.xupt3g.commentsview.model.CommentData;
import com.xupt3g.commentsview.model.CommentsListResponse;

import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.view.CommentsShowImpl
 *
 * @author: shallew
 * @data: 2024/3/23 16:01
 * @about: TODO View层对外暴露接口
 */
public interface CommentsShowImpl {

    /**
     *
     * @param commentsListResponse 评论区的信息（评分和总条数等）
     * TODO 将评论区顶部信息显示出来
     */
    void showCommentsRatingInfo(CommentsListResponse commentsListResponse);

    /**
     *
     * TODO 添加一些评论
     */
    void addCommentsToShowList(List<CommentData> commentsList);



    void loadMoreFailed();
}
