package com.xupt3g.commentsview;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xuexiang.xui.widget.imageview.preview.PreviewBuilder;
import com.xuexiang.xui.widget.popupwindow.good.GoodView;
import com.xupt3g.commentsview.model.CommentData;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.CommentsItemAdapter
 *
 * @author: shallew
 * @data: 2024/3/20 22:45
 * @about: TODO
 */
public class CommentsItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CommentData> commentDataList;
    private Fragment mFragment;
    private GoodView goodView;

    public CommentsItemAdapter(List<CommentData> commentDataList, Fragment fragment) {
        this.commentDataList = commentDataList;
        this.mFragment = fragment;
        goodView = new GoodView(mFragment.getContext());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_comment_item, parent, false);
            return new ViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_footview_loarding_more, parent, false);
            return new FootViewHolder(view);
        }
    }

    private final int CONTENT_MAX_LINES = 4;

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            CommentData commentData = commentDataList.get(position);
            //评分
            viewHolder.score.setText(commentData.getCommentedScore());
            //时间
            viewHolder.time.setText(commentData.getCommentedTime());
            //昵称
            viewHolder.userNickname.setText(commentData.getUserNickname());
            //头像
            Glide.with(viewHolder.itemView.getContext()).load(R.drawable.comments_default_avatar_2).into(viewHolder.userAvatar);

            //图片加载
            List<PictureInfo> pictureUrls = commentData.getPictureUrls();
            if (pictureUrls.size() != 0) {
                //有图片
                for (int i = 0; i < 3; i++) {
                    if (pictureUrls.size() > i) {
                        //集合中有第i张图片
                        Glide.with(viewHolder.itemView).load(pictureUrls.get(i).getUrl()).
                                into(viewHolder.pictureContainerArr[i]);
                        viewHolder.pictureCardArr[i].setVisibility(View.VISIBLE);
                    } else {
                        break;
                    }
                }
                if (pictureUrls.size() > 3) {
                    viewHolder.pictureGreyView.setVisibility(View.VISIBLE);
                    viewHolder.morePictureCount.setText("+" + (pictureUrls.size() - 3));
                }
            }
            for (int i = 0; i < 2; i++) {
                int finalI = i;
                viewHolder.pictureContainerArr[i].setOnClickListener(view -> {
                    PreviewBuilder.from(mFragment)
                            .setImgs(pictureUrls)
                            .setCurrentIndex(finalI)
                            .setType(PreviewBuilder.IndicatorType.Number)
                            .start();
                });
            }
            viewHolder.pictureGreyView.setOnClickListener(view -> {
                PreviewBuilder.from(mFragment)
                        .setImgs(pictureUrls)
                        .setCurrentIndex(2)
                        .setSingleFling(true)
                        .setType(PreviewBuilder.IndicatorType.Number)
                        .start();
            });
            //内容加载
            viewHolder.content.setText(commentData.getCommentContent());
            viewHolder.content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Layout layout = viewHolder.content.getLayout();
                    if (layout != null && layout.getEllipsisCount(layout.getLineCount() - 1) > 0) {
                        //省略了
                        viewHolder.expand.setVisibility(View.VISIBLE);
                        viewHolder.isNeedEllipsis = true;
                    } else {
                        //未省略
                        if (!viewHolder.isNeedEllipsis) {
                            viewHolder.expand.setVisibility(View.GONE);
                        }
                    }
                }
            });
            viewHolder.expand.setOnClickListener(view -> {
                if (!viewHolder.isExpanded) {
                    //当前未展开 即将展开
                    viewHolder.content.setMaxLines(Integer.MAX_VALUE);
                    viewHolder.content.requestLayout();
                    // 展开
                    viewHolder.content.setEllipsize(null);
                    viewHolder.expand.setText("收起");
                    viewHolder.isExpanded = true;
                } else {
                    //当前已展开 即将收缩
                    viewHolder.content.setMaxLines(CONTENT_MAX_LINES);
                    viewHolder.content.requestLayout();
                    // 收缩
                    viewHolder.content.setEllipsize(TextUtils.TruncateAt.END);
                    viewHolder.expand.setText("展开");
                    viewHolder.isExpanded = false;
                }
            });
            Random random = new Random();
            viewHolder.likedCount.setText(random.nextInt(601) + 100 + "");
            viewHolder.likedLayout.setOnClickListener(view -> {
                if (!viewHolder.isLiked) {
                    //当前未点赞 点赞
                    viewHolder.likedIcon.setImageResource(R.drawable.comments_icon_collect_after);
                    viewHolder.likedCount.setText(Integer.parseInt((String) viewHolder.likedCount.getText()) + 1 + "");
                    viewHolder.likedCount.setTextColor(Color.parseColor("#ff587b"));
                    viewHolder.isLiked = true;
                    goodView.setText("点赞成功")
                            .setTextColor(Color.parseColor("#ff587b"))
                            .setTextSize(12)
                            .setDuration(1000)
                            .setDistance(120)
                            .show(viewHolder.likedCount);
                } else {
                    //当前已点赞 取消
                    viewHolder.likedIcon.setImageResource(R.drawable.comments_icon_collect_before);
                    viewHolder.likedCount.setText(Integer.parseInt((String) viewHolder.likedCount.getText()) - 1 + "");
                    viewHolder.likedCount.setTextColor(Color.parseColor("#3E4055"));
                    viewHolder.isLiked = false;
                }
            });
        } else {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            if (footViewState == FOOTER_VIEW_STATE_LOAD) {
                footViewHolder.itemView.setOnClickListener(view -> {
                    //去Fragment中进行加载更多
                    onLoadMoreListener.lardMore();
                });
            } else if (footViewState == FOOTER_VIEW_STATE_NO_MORE) {
                footViewHolder.footText.setText("只有这么多了");
            }
        }
    }

    public interface OnLoadMoreListener {
        boolean lardMore();
    }

    private OnLoadMoreListener onLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.onLoadMoreListener = loadMoreListener;
    }

    @Override
    public int getItemCount() {
        if (commentDataList.size() > 0) {
            return commentDataList.size() + 1;
        }
        return 0;
    }

    private final int TYPE_FOOT = 10086;
    private final int TYPE_NORMAL = TYPE_FOOT + 1;

    @Override
    public int getItemViewType(int position) {
        if (position >= commentDataList.size()) {
            return TYPE_FOOT;
        }
        return TYPE_NORMAL;
    }

    //底部View状态 加载前
    public static int FOOTER_VIEW_STATE_LOAD = 2121;
    //底部View状态 没有更多
    public static int FOOTER_VIEW_STATE_NO_MORE = FOOTER_VIEW_STATE_LOAD + 1;
    private int footViewState = 0;

    @SuppressLint("NotifyDataSetChanged")
    public void changeState(int state) {
        this.footViewState = state;
        notifyItemChanged(getItemCount() - 1);
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout footLayout;
        private ImageView footImage1;
        private ImageView footImage2;
        private TextView footText;

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            footLayout = itemView.findViewById(R.id.comments_foot_layout);
            footImage1 = itemView.findViewById(R.id.comments_loading_more_1);
            footImage2 = itemView.findViewById(R.id.comments_loading_more_2);
            footText = itemView.findViewById(R.id.comments_foot_text);
            Glide.with(itemView).asGif().load(R.drawable.comments_loading).into(footImage1);
            Glide.with(itemView).asGif().load(R.drawable.comments_loading).into(footImage2);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView userAvatar;
        private TextView userNickname;
        private TextView time;
        private TextView score;
        private TextView content;
        private TextView expand;
        private ImageView picture1;
        private ImageView picture2;
        private ImageView picture3;
        private TextView likedCount;
        private FrameLayout pictureGreyView;
        /**
         * 标记是否需要被省略
         */
        private boolean isNeedEllipsis;
        private boolean isExpanded;
        private ImageView[] pictureContainerArr;
        private CardView[] pictureCardArr;
        private TextView morePictureCount;
        private CardView pictureCard1;
        private CardView pictureCard2;
        private CardView pictureCard3;
        private ImageView likedIcon;
        private ConstraintLayout likedLayout;
        private boolean isLiked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.comment_item_avatar);
            userNickname = itemView.findViewById(R.id.comment_item_username);
            time = itemView.findViewById(R.id.comment_item_time);
            score = itemView.findViewById(R.id.comment_item_user_score);
            content = itemView.findViewById(R.id.comment_item_content);
            expand = itemView.findViewById(R.id.comment_item_to_expand);
            picture1 = itemView.findViewById(R.id.comment_item_picture_1);
            pictureCard1 = itemView.findViewById(R.id.comments_picture_card1);
            pictureCard1.setVisibility(View.GONE);
            picture2 = itemView.findViewById(R.id.comment_item_picture_2);
            pictureCard2 = itemView.findViewById(R.id.comments_picture_card2);
            pictureCard2.setVisibility(View.GONE);
            picture3 = itemView.findViewById(R.id.comment_item_picture_3);
            pictureCard3 = itemView.findViewById(R.id.comments_picture_card3);
            pictureCard3.setVisibility(View.GONE);
            pictureContainerArr = new ImageView[]{picture1, picture2, picture3};
            pictureCardArr = new CardView[]{pictureCard1, pictureCard2, pictureCard3};
            likedCount = itemView.findViewById(R.id.comment_item_liked_count);
            pictureGreyView = itemView.findViewById(R.id.comment_item_more_picture_greyview);
            morePictureCount = itemView.findViewById(R.id.comment_item_more_picture_count);
            likedIcon = itemView.findViewById(R.id.comments_liked_icon);
            likedLayout = itemView.findViewById(R.id.comments_liked_layout);
            isExpanded = false;
            isNeedEllipsis = false;
            isLiked = false;
        }
    }
}
