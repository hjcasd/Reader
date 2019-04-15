package com.hjc.reader.ui.image.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.utils.image.ImageManager;

import java.util.List;

public class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImageAdapter(@Nullable List<String> data) {
        super(R.layout.item_rv_image, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivPic = helper.getView(R.id.iv_pic);
        ImageManager.loadImage(ivPic, item, 0);
    }
}
