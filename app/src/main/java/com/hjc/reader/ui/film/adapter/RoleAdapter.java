package com.hjc.reader.ui.film.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.MovieDetailBean;
import com.hjc.reader.databinding.ItemRoleBinding;


import java.util.List;

public class RoleAdapter extends BaseQuickAdapter<MovieDetailBean.DataBean.BasicBean.ActorsBean, BaseViewHolder> {

    public RoleAdapter(@Nullable List<MovieDetailBean.DataBean.BasicBean.ActorsBean> data) {
        super(R.layout.item_role, data);
    }

    @Override
    protected void onItemViewHolderCreated(@NonNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MovieDetailBean.DataBean.BasicBean.ActorsBean item) {
        if (item == null) {
            return;
        }

        ItemRoleBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setRoleBean(item);
        }
    }
}
