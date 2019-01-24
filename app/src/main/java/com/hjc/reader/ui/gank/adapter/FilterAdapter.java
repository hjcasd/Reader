package com.hjc.reader.ui.gank.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;

import java.util.List;

public class FilterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public FilterAdapter(@Nullable List<String> data) {
        super(R.layout.item_rv_filter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int imageResId;
        switch (item) {
            case "全部":
                imageResId = R.mipmap.icon_filter_all;
                break;

            case "Android":
                imageResId = R.mipmap.icon_filter_android;
                break;

            case "iOS":
                imageResId = R.mipmap.icon_filter_ios;
                break;

            case "App":
                imageResId = R.mipmap.icon_filter_app;
                break;

            case "前端":
                imageResId = R.mipmap.icon_filter_web;
                break;

            case "休息视频":
                imageResId = R.mipmap.icon_filter_video;
                break;

            case "拓展资源":
                imageResId = R.mipmap.icon_filter_extra;
                break;

            default:
                imageResId = R.mipmap.icon_filter_all;
                break;
        }
        helper.setText(R.id.tv_type_name, item);
        helper.setImageResource(R.id.iv_pic, imageResId);
    }
}
