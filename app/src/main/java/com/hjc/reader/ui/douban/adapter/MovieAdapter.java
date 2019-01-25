package com.hjc.reader.ui.douban.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.DBMovieBean;
import com.hjc.reader.utils.image.ImageLoader;

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

        ImageLoader.loadImage(ivCover, item.getImages().getLarge(), 3);
        tvName.setText(item.getTitle());
        tvDirecter.setText(formatName(item.getDirectors()));
        tvMain.setText(formatName(item.getCasts()));
        tvType.setText(formatGenres(item.getGenres()));
        tvScore.setText(item.getRating().getAverage() + "");
    }

    private String formatName(List<DBMovieBean.SubjectsBean.PersonBean> casts) {
        if (casts != null && casts.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < casts.size(); i++) {
                if (i < casts.size() - 1) {
                    stringBuilder.append(casts.get(i).getName()).append(" / ");
                } else {
                    stringBuilder.append(casts.get(i).getName());
                }
            }
            return stringBuilder.toString();
        } else {
            return "佚名";
        }
    }

    /**
     * 格式化电影类型
     */
    private String formatGenres(List<String> genres) {
        if (genres != null && genres.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < genres.size(); i++) {
                if (i < genres.size() - 1) {
                    stringBuilder.append(genres.get(i)).append(" / ");
                } else {
                    stringBuilder.append(genres.get(i));
                }
            }
            return stringBuilder.toString();
        } else {
            return "不知名类型";
        }
    }
}
