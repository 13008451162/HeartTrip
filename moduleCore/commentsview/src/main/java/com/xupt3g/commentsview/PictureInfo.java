package com.xupt3g.commentsview;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.xuexiang.xui.widget.imageview.preview.enitity.IPreviewInfo;

import java.util.Collections;
import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: com.xupt3g.commentsview.PictureInfo
 *
 * @author: shallew
 * @data: 2024/3/21 12:37
 * @about: TODO 预览图片实体
 */

public class PictureInfo implements IPreviewInfo {

    /**
     * 图片地址
     */
    private String mUrl;
    /**
     * 记录坐标
     */
    private Rect mBounds;
    private String mVideoUrl;

    private String mDescription = "描述信息";

    public static List<PictureInfo> newInstance(String url, Rect bounds) {
        return Collections.singletonList(new PictureInfo(url, bounds));
    }

    public PictureInfo(String url) {
        mUrl = url;
    }

    public PictureInfo(String url, Rect bounds) {
        mUrl = url;
        mBounds = bounds;
    }

    public PictureInfo(String videoUrl, String url) {
        mUrl = url;
        mVideoUrl = videoUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public String getUrl() {//将你的图片地址字段返回
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public Rect getBounds() {//将你的图片显示坐标字段返回
        return mBounds;
    }

    @Nullable
    @Override
    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setBounds(Rect bounds) {
        mBounds = bounds;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
        dest.writeParcelable(mBounds, flags);
        dest.writeString(mDescription);
        dest.writeString(mVideoUrl);
    }

    protected PictureInfo(Parcel in) {
        mUrl = in.readString();
        mBounds = in.readParcelable(Rect.class.getClassLoader());
        mDescription = in.readString();
        mVideoUrl = in.readString();
    }

    public static final Parcelable.Creator<PictureInfo> CREATOR = new Parcelable.Creator<PictureInfo>() {
        @Override
        public PictureInfo createFromParcel(Parcel source) {
            return new PictureInfo(source);
        }

        @Override
        public PictureInfo[] newArray(int size) {
            return new PictureInfo[size];
        }
    };
}
