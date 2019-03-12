package com.hjc.reader.ui.collect.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.CollectArticleBean;

import java.util.List;

public class ArticleAdapter extends BaseQuickAdapter<CollectArticleBean.DataBean.DatasBean, BaseViewHolder> {
    public ArticleAdapter(@Nullable List<CollectArticleBean.DataBean.DatasBean> data) {
        super(R.layout.item_rv_article, data) ;
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectArticleBean.DataBean.DatasBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_time, item.getNiceDate());
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_chapter, item.getChapterName());
    }
}
