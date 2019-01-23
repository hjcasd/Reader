package com.hjc.reader.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.utils.image.ImageLoader;

import java.util.List;

public class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImageAdapter(@Nullable List<String> data) {
        super(R.layout.item_rv_image, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivPic = helper.getView(R.id.iv_pic);
        ImageLoader.loadImage(ivPic, item, 0);
    }
}
