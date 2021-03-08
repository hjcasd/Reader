package com.hjc.reader.ui.wan.adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.WanTreeBean;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.ItemTreeBinding;
import com.hjc.reader.utils.helper.RouteManager;
import com.nex3z.flowlayout.FlowLayout;


import java.util.List;

public class TreeListAdapter extends BaseQuickAdapter<WanTreeBean.DataBean, BaseViewHolder> {
    public TreeListAdapter() {
        super(R.layout.item_tree);
    }

    @Override
    protected void onItemViewHolderCreated(@NonNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, WanTreeBean.DataBean item) {
        if (item == null) {
            return;
        }

        ItemTreeBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setWanTreeBean(item);

            List<WanTreeBean.DataBean.ChildrenBean> childrenList = item.getChildren();
            initTags(childrenList, binding.flowLayout);
        }
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
            View view = View.inflate(getContext(), R.layout.view_tree_tag, null);
            TextView tvTag = view.findViewById(R.id.tv_tag);
            tvTag.setText(bean.getName());
            flLabels.addView(tvTag);

            tvTag.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("name", bean.getName());
                bundle.putInt("id", bean.getId());
                RouteManager.jumpWithBundle(RoutePath.URL_TAG, bundle);
            });
        }
    }
}
