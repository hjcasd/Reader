package com.hjc.reader.ui.douban.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.DBBookBean;
import com.hjc.reader.utils.image.ImageLoader;

import java.util.List;

public class BookAdapter extends BaseQuickAdapter<DBBookBean.BooksBean, BaseViewHolder> {
    public BookAdapter(@Nullable List<DBBookBean.BooksBean> data) {
        super(R.layout.item_rv_book, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DBBookBean.BooksBean item) {
        ImageView ivCover = helper.getView(R.id.iv_cover);
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvScore = helper.getView(R.id.tv_score);

        ImageLoader.loadImage(ivCover, item.getImages().getLarge(), 2);
        tvName.setText(item.getTitle());
        tvScore.setText(item.getRating().getAverage());
    }
}
