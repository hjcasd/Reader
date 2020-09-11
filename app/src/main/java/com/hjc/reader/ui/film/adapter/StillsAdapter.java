package com.hjc.reader.ui.film.adapter;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.MovieDetailBean;
import com.hjc.reader.databinding.ItemStillsBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class StillsAdapter extends BaseQuickAdapter<MovieDetailBean.DataBean.BasicBean.StageImgBean.ListBean, BaseViewHolder> {

    public StillsAdapter(@Nullable List<MovieDetailBean.DataBean.BasicBean.StageImgBean.ListBean> data) {
        super(R.layout.item_stills, data);
    }

    @Override
    protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, MovieDetailBean.DataBean.BasicBean.StageImgBean.ListBean item) {
        if (item == null) {
            return;
        }

        ItemStillsBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setStillsBean(item);
        }
    }
}
