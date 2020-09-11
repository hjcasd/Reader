package com.hjc.reader.ui.wan.adapter;

import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.WanListBean;
import com.hjc.reader.databinding.ItemWanBinding;

import org.jetbrains.annotations.NotNull;


public class WanListAdapter extends BaseQuickAdapter<WanListBean.DataBean.DatasBean, BaseViewHolder> {

    public WanListAdapter() {
        super(R.layout.item_wan);
    }

    @Override
    protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, WanListBean.DataBean.DatasBean item) {
        if (item == null) {
            return;
        }

        ItemWanBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setArticleBean(item);
        }
    }
}
