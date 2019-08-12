package com.hjc.reader.ui.film.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.MovieItemBean;
import com.hjc.reader.ui.film.child.MovieDetailActivity;
import com.hjc.reader.utils.image.ImageManager;

import java.util.List;

public class HotMovieAdapter extends BaseQuickAdapter<MovieItemBean, BaseViewHolder> {
    public HotMovieAdapter(@Nullable List<MovieItemBean> data) {
        super(R.layout.item_rv_movie, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MovieItemBean item) {
        ImageView ivCover = helper.getView(R.id.iv_cover);
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvDirecter = helper.getView(R.id.tv_director);
        TextView tvMain = helper.getView(R.id.tv_main);
        TextView tvType = helper.getView(R.id.tv_type);
        TextView tvScore = helper.getView(R.id.tv_score);

        ImageManager.loadImage(ivCover, item.getImg(), 3);

        tvName.setText(item.getTCn());
        tvDirecter.setText(String.format("导演：%s", item.getDN()));
        tvMain.setText(String.format("主演：%s", item.getActors()));
        tvType.setText(String.format("类型：%s", item.getMovieType()));
        tvScore.setText(String.format("评分：%s", item.getR()));

        helper.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MovieDetailActivity.class);
            intent.putExtra("bean", item);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, ivCover, mContext.getResources().getString(R.string.transition_movie_img));
            ActivityCompat.startActivity(mContext, intent, options.toBundle());
        });

    }
}
