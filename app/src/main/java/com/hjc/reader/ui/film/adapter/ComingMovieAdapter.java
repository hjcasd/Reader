package com.hjc.reader.ui.film.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.MovieComingBean;
import com.hjc.reader.model.response.MovieItemBean;
import com.hjc.reader.ui.film.child.MovieDetailActivity;
import com.hjc.reader.utils.image.ImageManager;

import java.util.List;

public class ComingMovieAdapter extends BaseQuickAdapter<MovieComingBean.ComingItemBean, BaseViewHolder> {
    public ComingMovieAdapter(@Nullable List<MovieComingBean.ComingItemBean> data) {
        super(R.layout.item_coming_movie, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MovieComingBean.ComingItemBean item) {
        ImageView ivCover = helper.getView(R.id.iv_cover);
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvTime = helper.getView(R.id.tv_time);

        ImageManager.loadImage(ivCover, item.getImage(), 3);
        tvName.setText(item.getTitle());
        tvTime.setText(item.getReleaseDate());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieItemBean movieItemBean = new MovieItemBean();
                movieItemBean.setId(item.getId());
                movieItemBean.setDN(item.getDirector());
                movieItemBean.setTCn(item.getTitle());
                movieItemBean.setMovieType(item.getType());
                movieItemBean.setImg(item.getImage());
                String actor1 = item.getActor1();
                String actor2 = item.getActor2();
                if (!TextUtils.isEmpty(actor2)) {
                    actor1 = actor1 + " / " + actor2;
                }
                movieItemBean.setActors(actor1);

                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("bean", movieItemBean);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, ivCover, mContext.getResources().getString(R.string.transition_movie_img));
                ActivityCompat.startActivity(mContext, intent, options.toBundle());
            }
        });
    }
}
