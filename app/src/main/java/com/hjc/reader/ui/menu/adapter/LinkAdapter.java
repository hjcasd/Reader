package com.hjc.reader.ui.menu.adapter;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.CollectLinkBean;
import com.hjc.reader.databinding.ItemLinkBinding;


public class LinkAdapter extends BaseQuickAdapter<CollectLinkBean.DataBean, BaseViewHolder> {

    public LinkAdapter() {
        super(R.layout.item_link) ;
    }

    @Override
    protected void onItemViewHolderCreated(@NonNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CollectLinkBean.DataBean item) {
        if (item == null) {
            return;
        }

        ItemLinkBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setLinkBean(item);
        }
    }
}
