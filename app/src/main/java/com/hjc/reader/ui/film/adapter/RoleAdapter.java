package com.hjc.reader.ui.film.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.MovieDetailBean;
import com.hjc.reader.utils.image.ImageManager;

import java.util.List;

public class RoleAdapter extends BaseQuickAdapter<MovieDetailBean.DataBean.BasicBean.ActorsBean, BaseViewHolder> {
    public RoleAdapter(@Nullable List<MovieDetailBean.DataBean.BasicBean.ActorsBean> data) {
        super(R.layout.item_rv_role, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MovieDetailBean.DataBean.BasicBean.ActorsBean item) {
        ImageView ivRolePic = helper.getView(R.id.iv_role_pic);
        TextView tvRoleName = helper.getView(R.id.tv_role_name);
        TextView tvRoleType = helper.getView(R.id.tv_role_type);

        ImageManager.loadImage(ivRolePic, item.getImg(), 3);
        tvRoleName.setText(item.getName());
        tvRoleType.setText(item.getRoleName());
    }
}
