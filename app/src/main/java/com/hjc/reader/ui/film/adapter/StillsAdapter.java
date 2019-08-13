package com.hjc.reader.ui.film.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.MovieDetailBean;
import com.hjc.reader.utils.image.ImageManager;

import java.util.List;

public class StillsAdapter extends BaseQuickAdapter<MovieDetailBean.DataBean.BasicBean.StageImgBean.ListBean, BaseViewHolder> {
    public StillsAdapter(@Nullable List<MovieDetailBean.DataBean.BasicBean.StageImgBean.ListBean> data) {
        super(R.layout.item_rv_stills, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,MovieDetailBean.DataBean.BasicBean.StageImgBean.ListBean item) {
        ImageView ivPic = helper.getView(R.id.iv_pic);
        ImageManager.loadImage(ivPic, item.getImgUrl(), 3);
    }
}
