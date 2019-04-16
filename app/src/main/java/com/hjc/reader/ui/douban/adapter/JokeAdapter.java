package com.hjc.reader.ui.douban.adapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.JokeBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class JokeAdapter extends BaseQuickAdapter<JokeBean.ItemsBean, BaseViewHolder> {
    public JokeAdapter(@Nullable List<JokeBean.ItemsBean> data) {
        super(R.layout.item_rv_joke, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeBean.ItemsBean item) {
        JokeBean.ItemsBean.UserBeanX userBean = item.getUser();
        helper.setText(R.id.tv_name, userBean != null ? userBean.getLogin() : "未知");

        helper.setText(R.id.tv_time, TimeUtils.millis2String(Long.valueOf(item.getPublished_at() + "000")));
        helper.setText(R.id.tv_content, item.getContent());

        CircleImageView civAvatar = helper.getView(R.id.civ_avatar);
        Glide.with(mContext).load(userBean != null ? userBean.getThumb() : R.mipmap.ic_launcher).into(civAvatar);
    }
}
