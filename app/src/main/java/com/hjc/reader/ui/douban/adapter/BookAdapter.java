package com.hjc.reader.ui.douban.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.DBBookBean;
import com.hjc.reader.ui.douban.child.BookDetailActivity;
import com.hjc.reader.utils.image.ImageManager;

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

        ImageManager.loadImage(ivCover, item.getImages().getLarge(), 2);
        tvName.setText(item.getTitle());
        tvScore.setText(item.getRating().getAverage());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookDetailActivity.class);
                intent.putExtra("bean", item);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, ivCover, mContext.getResources().getString(R.string.transition_book_img));
                ActivityCompat.startActivity(mContext, intent, options.toBundle());
            }
        });
    }
}
