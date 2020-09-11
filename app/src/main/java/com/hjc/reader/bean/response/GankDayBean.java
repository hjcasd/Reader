package com.hjc.reader.bean.response;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class GankDayBean implements MultiItemEntity {
    public static final int TYPE_TEXT = 1;  //只有文字
    public static final int TYPE_IMAGE_ONE = 2;  //一张图片加文字
    public static final int TYPE_IMAGE_THREE = 3;  //三张图片加文字
    public static final int TYPE_TITLE = 4;  //标题栏
    public static final int TYPE_IMAGE_ONLY = 5;  //标题栏

    private String title;
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private String source;
    private List<String> images;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public int getItemType() {
        String title = getTitle();
        if (!StringUtils.isEmpty(title)) {
            return TYPE_TITLE;
        } else {
            List<String> images = getImages();
            if (images == null) {
                if (getType().equals("福利")) {
                    return TYPE_IMAGE_ONLY;
                } else {
                    return TYPE_TEXT;
                }
            } else {
                if (images.size() >= 1 && images.size() < 3) {
                    return TYPE_IMAGE_ONE;
                } else {
                    return TYPE_IMAGE_THREE;
                }
            }
        }
    }
}
