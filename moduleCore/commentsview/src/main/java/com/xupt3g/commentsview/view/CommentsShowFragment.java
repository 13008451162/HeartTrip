package com.xupt3g.commentsview.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xuexiang.xui.widget.progress.ratingbar.ScaleRatingBar;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xupt3g.commentsview.CommentsItemAdapter;
import com.xupt3g.commentsview.R;
import com.xupt3g.commentsview.model.CommentData;
import com.xupt3g.commentsview.model.CommentsListResponse;
import com.xupt3g.commentsview.presenter.CommentsPresenter;
import com.xupt3g.mylibrary1.ProgressAnim;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.view.CommentsShowFragment
 *
 * @author: shallew
 * @data: 2024/3/20 21:04
 * @about: TODO 评论区展示页面
 */
public class CommentsShowFragment extends Fragment implements CommentsShowImpl {
    private int houseId;

    public static CommentsShowFragment newInstance(int houseId) {
        Bundle args = new Bundle();
        args.putInt("HouseId", houseId);
        CommentsShowFragment fragment = new CommentsShowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.houseId = getArguments().getInt("HouseId");
        }
    }

    private View mView;
    private ImageView backButton;
    private TextView ratingScore;
    private ScaleRatingBar ratingStarts;
    /**
     * 总评论条数 （共32条点评）
     */
    private TextView totalCommentsCount;
    /**
     * 整洁程度 5.0 改后面数字
     */
    private TextView tidyLevel;
    /**
     * 交通位置 5.0
     */
    private TextView transportationLocation;
    /**
     * 安全程度 5.0
     */
    private TextView securityLevel;
    /**
     * 餐饮体验 5.0
     */
    private TextView foodExperience;
    /**
     * 综合性价比 5.0
     */
    private TextView costPerformance;
    private RecyclerView commentsRecycler;
    /**
     * 发布评论的按钮
     */
    private FloatingActionButton postCommentButton;
    private CommentsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.comments_show_fragment, container, false);
        backButton = mView.findViewById(R.id.comments_back);
        backButton.setOnClickListener(view -> {
            requireActivity().finish();
        });
        ratingScore = mView.findViewById(R.id.comments_rating_score);
        ratingStarts = mView.findViewById(R.id.comments_rating_star);
        totalCommentsCount = mView.findViewById(R.id.comments_comment_count);
        tidyLevel = mView.findViewById(R.id.comments_tidy_level);
        transportationLocation = mView.findViewById(R.id.comments_transportation_location);
        securityLevel = mView.findViewById(R.id.comments_security_level);
        foodExperience = mView.findViewById(R.id.comments_food_experience);
        costPerformance = mView.findViewById(R.id.comments_cost_performance);
        commentsRecycler = mView.findViewById(R.id.comments_recycler);
        postCommentButton = mView.findViewById(R.id.comments_post_comment);
        postCommentButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), CommentPostActivity.class);
            intent.putExtra("HouseId", houseId);
            startActivityForResult(intent, 1);
        });

        presenter = new CommentsPresenter(this, getContext());
        ProgressAnim.showProgress(getContext());
        Log.d("TTAYVCCA", "CommentsPresenter: " + houseId);

        presenter.commentsDataShowOnUi(houseId, currPage, pageSize);
        return mView;
    }

    int currCommentsSize;
    int currPage = 1;
    int pageSize = 6;
    private List<CommentData> currComments;
    private CommentsItemAdapter commentsItemAdapter;

    /**
     * @param response 评论区的信息（评分和总条数等）
     * TODO 将评论区顶部信息显示出来
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void showCommentsRatingInfo(CommentsListResponse response) {
        Log.d("TAGGGGYTU", "showCommentsRatingInfo: " + houseId);
        ratingScore.setText(response.getScore() + "");
        ratingStarts.setRating(response.getScore());
        totalCommentsCount.setText(response.getCommentCount());
        tidyLevel.setText(response.getTidyRating());
        transportationLocation.setText(response.getTrafficRating());
        securityLevel.setText(response.getSecurityRating());
        foodExperience.setText(response.getFoodRating());
        costPerformance.setText(response.getCostRating());

    }

    @Override
    public void addCommentsToShowList(List<CommentData> commentsList) {
        if (currComments == null) {
            currComments = new ArrayList<>();
            commentsItemAdapter = new CommentsItemAdapter(currComments, CommentsShowFragment.this);
            currCommentsSize = 0;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            commentsRecycler.setLayoutManager(linearLayoutManager);
            commentsRecycler.setNestedScrollingEnabled(false);
            commentsRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
            commentsRecycler.setAdapter(commentsItemAdapter);
            commentsItemAdapter.changeState(CommentsItemAdapter.FOOTER_VIEW_STATE_LOAD);
            commentsItemAdapter.setOnLoadMoreListener(new CommentsItemAdapter.OnLoadMoreListener() {
                @Override
                public boolean lardMore() {
                    ProgressAnim.showProgress(getContext());
                    //发送请求
                    presenter.commentsDataShowOnUi(houseId, ++currPage, pageSize);

                    return true;
                }
            });
        }
        currComments.addAll(commentsList);
        commentsItemAdapter.notifyItemInserted(currCommentsSize);
        currCommentsSize = currComments.size();
        ProgressAnim.hideProgress();
    }

    @Override
    public void loadMoreFailed() {
        ProgressAnim.hideProgress();
        ToastUtils.toast("没有更多评论了。");
        Log.d("HOUSEIDDD", "loadMoreFailed: " + houseId);
        if (commentsItemAdapter != null) {
            commentsItemAdapter.changeState(CommentsItemAdapter.FOOTER_VIEW_STATE_NO_MORE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        currComments = null;
        currPage = 1;
        ProgressAnim.showProgress(getContext());
        presenter.commentsDataShowOnUi(houseId, currPage, pageSize);
    }
}
