package com.hjc.reader.ui.douban.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.RoleBean;
import com.hjc.reader.utils.SchemeUtils;
import com.hjc.reader.utils.image.ImageLoader;

import java.util.List;

public class RoleAdapter extends BaseQuickAdapter<RoleBean, BaseViewHolder> {
    public RoleAdapter(@Nullable List<RoleBean> data) {
        super(R.layout.item_rv_role, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoleBean item) {
        ImageView ivRolePic = helper.getView(R.id.iv_role_pic);
        TextView tvRoleName = helper.getView(R.id.tv_role_name);
        TextView tvRoleType = helper.getView(R.id.tv_role_type);

        ImageLoader.loadImage(ivRolePic, item.getAvatar(), 3);
        tvRoleName.setText(item.getName());
        tvRoleType.setText(item.getType());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SchemeUtils.jumpToWeb(mContext, item.getAlt(), item.getName());
            }
        });
    }
}
