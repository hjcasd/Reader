package com.hjc.reader.ui.collect.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.CollectLinkBean;

import java.util.List;

public class LinkAdapter extends BaseQuickAdapter<CollectLinkBean.DataBean, BaseViewHolder> {
    public LinkAdapter(@Nullable List<CollectLinkBean.DataBean> data) {
        super(R.layout.item_rv_link, data) ;
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectLinkBean.DataBean item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_link, item.getLink());
    }
}
