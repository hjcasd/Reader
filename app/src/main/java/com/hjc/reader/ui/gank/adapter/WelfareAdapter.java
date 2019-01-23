package com.hjc.reader.ui.gank.adapter;

import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.GankIOBean;
import com.hjc.reader.utils.image.ImageLoader;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.List;

public class WelfareAdapter extends BaseQuickAdapter<GankIOBean.ResultsBean, BaseViewHolder> {
    public WelfareAdapter(@Nullable List<GankIOBean.ResultsBean> data) {
        super(R.layout.item_rv_welfare, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankIOBean.ResultsBean item) {
        ImageView ivPic = helper.getView(R.id.iv_pic);
        ImageLoader.loadImage(ivPic, item.getUrl(), 1);

        int position = helper.getAdapterPosition();
        if (position % 2 == 0) {
            ivPic.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(250f)));
        } else {
            ivPic.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  DensityUtil.dp2px(300f)));
        }
    }
}
