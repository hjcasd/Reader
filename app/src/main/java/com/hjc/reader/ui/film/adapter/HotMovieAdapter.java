package com.hjc.reader.ui.film.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.MovieItemBean;
import com.hjc.reader.databinding.ItemHotMovieBinding;


import java.util.List;

public class HotMovieAdapter extends BaseQuickAdapter<MovieItemBean, BaseViewHolder> {

    public HotMovieAdapter(@Nullable List<MovieItemBean> data) {
        super(R.layout.item_hot_movie, data);
    }

    @Override
    protected void onItemViewHolderCreated(@NonNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MovieItemBean item) {
        if (item == null) {
            return;
        }

        ItemHotMovieBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setHotMovieBean(item);
        }
    }
}
