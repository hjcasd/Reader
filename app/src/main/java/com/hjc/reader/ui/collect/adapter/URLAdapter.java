package com.hjc.reader.ui.collect.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;

import java.util.List;

public class URLAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public URLAdapter(@Nullable List<String> data) {
        super(R.layout.item_rv_article, data) ;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
