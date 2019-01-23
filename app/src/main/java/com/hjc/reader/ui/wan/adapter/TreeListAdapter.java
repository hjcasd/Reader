package com.hjc.reader.ui.wan.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.WanTreeBean;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
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
        
        List<String> tagList = new ArrayList<>();
        for (WanTreeBean.DataBean.ChildrenBean bean: childrenList) {
            tagList.add(bean.getName());
        }
        initTags(tagList, flowLayout);
    }

    /**
     * 初始化tag
     *
     * @param tagList
     * @param flLabels
     */
    private void initTags(List<String> tagList, FlowLayout flLabels) {
        flLabels.removeAllViews();
        for (String tag : tagList) {
            View view = View.inflate(mContext, R.layout.view_tree_tag, null);
            TextView tvTag = view.findViewById(R.id.tv_tag);
            tvTag.setText(tag);
            flLabels.addView(tvTag);
        }
    }
}
