package com.hjc.reader.ui.wan.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.WanTreeBean;
import com.nex3z.flowlayout.FlowLayout;

import java.util.List;

public class TreeListAdapter extends BaseQuickAdapter<WanTreeBean.DataBean, BaseViewHolder> {
    public TreeListAdapter(@Nullable List<WanTreeBean.DataBean> data) {
        super(R.layout.item_rv_tree, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WanTreeBean.DataBean item) {
        String name = item.getName();
        helper.setText(R.id.tv_title, name);

        FlowLayout flowLayout = helper.getView(R.id.flow_layout);
        List<WanTreeBean.DataBean.ChildrenBean> childrenList = item.getChildren();
        initTags(childrenList, flowLayout);
    }

    /**
     * 初始化tag
     *
     * @param tagList  标签集合
     * @param flLabels 流式布局控件
     */
    private void initTags(List<WanTreeBean.DataBean.ChildrenBean> tagList, FlowLayout flLabels) {
        flLabels.removeAllViews();
        for (WanTreeBean.DataBean.ChildrenBean bean : tagList) {
            View view = View.inflate(mContext, R.layout.view_tree_tag, null);
            TextView tvTag = view.findViewById(R.id.tv_tag);
            tvTag.setText(bean.getName());
            flLabels.addView(tvTag);

            tvTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort("tag---" + bean.getName());
                }
            });
        }
    }
}
