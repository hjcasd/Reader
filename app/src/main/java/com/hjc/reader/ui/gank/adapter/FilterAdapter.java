package com.hjc.reader.ui.gank.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.databinding.ItemFilterBinding;


import java.util.List;

public class FilterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FilterAdapter(@Nullable List<String> data) {
        super(R.layout.item_filter, data);
    }

    @Override
    protected void onItemViewHolderCreated(@NonNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        if (item == null) {
            return;
        }

        int imageResId;
        switch (item) {
            case "全部":
                imageResId = R.mipmap.icon_filter_all;
                break;

            case "Android":
                imageResId = R.mipmap.icon_filter_android;
                break;

            case "IOS":
                imageResId = R.mipmap.icon_filter_ios;
                break;

            case "App":
                imageResId = R.mipmap.icon_filter_app;
                break;

            case "前端":
                imageResId = R.mipmap.icon_filter_web;
                break;

            case "后端":
                imageResId = R.mipmap.icon_filter_video;
                break;

            case "推荐":
                imageResId = R.mipmap.icon_filter_extra;
                break;

            default:
                imageResId = R.mipmap.icon_filter_all;
                break;
        }

        ItemFilterBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setTitle(item);
            binding.ivPic.setImageResource(imageResId);
        }
    }
}
