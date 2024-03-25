package com.xupt3g.commentsview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.xuexiang.xui.widget.imageview.preview.PreviewBuilder;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.CommentsPostPictureAdapter
 *
 * @author: shallew
 * @data: 2024/3/22 18:57
 * @about: TODO 发表评论的图片适配器
 */
public class CommentsPostPictureAdapter extends RecyclerView.Adapter<CommentsPostPictureAdapter.ViewHolder> {
    private List<LocalMedia> currPicturePaths;
    private List<PictureInfo> currPicturesInfo;
    private Activity mActivity;

    public CommentsPostPictureAdapter(List<LocalMedia> currPicturePaths, Activity activity) {
        this.currPicturePaths = currPicturePaths;
        this.mActivity = activity;
        currPicturesInfo = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_comments_post_picture_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemCount() == 0 || position >= 6) {
            return;
        }
        currPicturesInfo.clear();
        for (int i = 0; i < currPicturePaths.size(); i++) {
            currPicturesInfo.add(new PictureInfo(currPicturePaths.get(i).getPath()));
        }
        //前6个
        holder.itemPicture.setVisibility(View.GONE);
        holder.itemOverlayView.setVisibility(View.GONE);
        holder.itemDeleteBtn.setVisibility(View.GONE);

        Glide.with(holder.itemView.getContext()).load(currPicturePaths.get(position).getPath())
                .into(holder.itemPicture);
        holder.itemPicture.setVisibility(View.VISIBLE);
        holder.itemDeleteBtn.setVisibility(View.VISIBLE);
        holder.itemPicture.setOnClickListener(view -> {
            PreviewBuilder.from(mActivity)
                    .setImgs(currPicturesInfo)
                    .setCurrentIndex(position)
                    .setSingleFling(true)
                    .setType(PreviewBuilder.IndicatorType.Number)
                    .start();
        });
        holder.itemDeleteBtn.setOnClickListener(view -> {
            currPicturePaths.remove(position);
            if (currPicturePaths.isEmpty()) {
                //集合为空
                isEmptyListener.picturesIsEmpty();
            }
            notifyDataSetChanged();
        });
        if (position == 5 && getItemCount() > 6) {
            //还有多的图片
            holder.itemOverlayView.setVisibility(View.VISIBLE);
            holder.itemMorePictureCount.setText("+" + (getItemCount() - 6));
            holder.itemOverlayView.setOnClickListener(view -> {
                ToastUtils.toast(currPicturesInfo.size() + "");
                PreviewBuilder.from(mActivity)
                        .setImgs(currPicturesInfo)
                        .setCurrentIndex(5)
                        .setSingleFling(true)
                        .setType(PreviewBuilder.IndicatorType.Number)
                        .start();
            });
            holder.itemDeleteBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return currPicturePaths.size();
    }
    public interface OnPicturesIsEmptyListener {
        /**
         * 图片被清空时的回调方法
         */
        void picturesIsEmpty();
    }
    private OnPicturesIsEmptyListener isEmptyListener;
    public void setIsEmptyListener (OnPicturesIsEmptyListener listener) {
        this.isEmptyListener = listener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemPicture;
        private ImageView itemDeleteBtn;
        private FrameLayout itemOverlayView;
        private TextView itemMorePictureCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemPicture = itemView.findViewById(R.id.comment_post_item_picture);
            itemPicture.setVisibility(View.GONE);
            itemDeleteBtn = itemView.findViewById(R.id.comment_post_item_delete_button);
            itemDeleteBtn.setVisibility(View.GONE);
            itemOverlayView = itemView.findViewById(R.id.comments_post_item_picture_overlay);
            itemMorePictureCount = itemView.findViewById(R.id.comments_post_item_more_picture_count);
            itemOverlayView.setVisibility(View.GONE);

        }
    }
}
