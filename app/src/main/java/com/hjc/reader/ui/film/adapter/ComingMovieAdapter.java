package com.hjc.reader.ui.film.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.MovieComingBean;
import com.hjc.reader.databinding.ItemComingMovieBinding;

import java.util.List;

public class ComingMovieAdapter extends BaseQuickAdapter<MovieComingBean.ComingItemBean, BaseViewHolder> {

    public ComingMovieAdapter(@Nullable List<MovieComingBean.ComingItemBean> data) {
        super(R.layout.item_coming_movie, data);
    }

    @Override
    protected void onItemViewHolderCreated(@NonNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MovieComingBean.ComingItemBean item) {
        if (item == null) {
            return;
        }

        ItemComingMovieBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setComingMovieBean(item);
        }
    }
}
