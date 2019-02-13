package com.hjc.reader.ui.wan.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.WanListBean;

import java.util.List;

public class ArticleListAdapter extends BaseQuickAdapter<WanListBean.DataBean.DatasBean, BaseViewHolder> {
    public ArticleListAdapter(@Nullable List<WanListBean.DataBean.DatasBean> data) {
        super(R.layout.item_rv_article, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WanListBean.DataBean.DatasBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_time, item.getNiceDate());
        helper.setText(R.id.tv_author, item.getAuthor());
    }
}
