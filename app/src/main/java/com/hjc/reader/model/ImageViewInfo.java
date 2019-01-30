package com.hjc.reader.model;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageViewInfo implements Parcelable {
    private String url;
    private Rect rect;

    public ImageViewInfo(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeParcelable(this.rect, flags);
    }

    protected ImageViewInfo(Parcel in) {
        this.url = in.readString();
        this.rect = in.readParcelable(Rect.class.getClassLoader());
    }

    public static final Creator<ImageViewInfo> CREATOR = new Creator<ImageViewInfo>() {
        @Override
        public ImageViewInfo createFromParcel(Parcel source) {
            return new ImageViewInfo(source);
        }

        @Override
        public ImageViewInfo[] newArray(int size) {
            return new ImageViewInfo[size];
        }
    };
}
