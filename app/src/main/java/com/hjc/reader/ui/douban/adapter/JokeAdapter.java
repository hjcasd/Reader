package com.hjc.reader.ui.douban.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.JokeBean;
import com.hjc.reader.utils.image.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class JokeAdapter extends BaseQuickAdapter<JokeBean.ItemsBean, BaseViewHolder> {
    public JokeAdapter(@Nullable List<JokeBean.ItemsBean> data) {
        super(R.layout.item_rv_joke, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeBean.ItemsBean item) {
        helper.setText(R.id.tv_name, item.getUser().getLogin());
        helper.setText(R.id.tv_time, TimeUtils.millis2String(Long.valueOf(item.getPublished_at() + "000")));
        helper.setText(R.id.tv_content, item.getContent());

        CircleImageView civAvatar = helper.getView(R.id.civ_avatar);
        Glide.with(mContext).load(item.getUser().getThumb()).into(civAvatar);
    }
}
