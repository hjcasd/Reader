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
import com.hjc.reader.model.response.DBMovieBean;
import com.hjc.reader.ui.douban.child.MovieDetailActivity;
import com.hjc.reader.utils.FormatUtils;
import com.hjc.reader.utils.image.ImageManager;

import java.util.List;

public class MovieAdapter extends BaseQuickAdapter<DBMovieBean.SubjectsBean, BaseViewHolder> {
    public MovieAdapter(@Nullable List<DBMovieBean.SubjectsBean> data) {
        super(R.layout.item_rv_movie, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DBMovieBean.SubjectsBean item) {
        ImageView ivCover = helper.getView(R.id.iv_cover);
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvDirecter = helper.getView(R.id.tv_director);
        TextView tvMain = helper.getView(R.id.tv_main);
        TextView tvType = helper.getView(R.id.tv_type);
        TextView tvScore = helper.getView(R.id.tv_score);

        ImageManager.loadImage(ivCover, item.getImages().getLarge(), 3);
        tvName.setText(item.getTitle());
        tvDirecter.setText(FormatUtils.formatName(item.getDirectors()));
        tvMain.setText(FormatUtils.formatName(item.getCasts()));
        tvType.setText(FormatUtils.formatGenres(item.getGenres()));
        tvScore.setText(item.getRating().getAverage() + "");


        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("bean", item);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, ivCover, mContext.getResources().getString(R.string.transition_movie_img));
                ActivityCompat.startActivity(mContext, intent, options.toBundle());
            }
        });

    }
}
