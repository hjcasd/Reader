package com.hjc.reader.adapter;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.databinding.ItemImageBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImageAdapter(@Nullable List<String> data) {
        super(R.layout.item_image, data);
    }

    @Override
    protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String item) {
        if (item == null) {
            return;
        }

        ItemImageBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setImageUrl(item);
        }
    }
}
