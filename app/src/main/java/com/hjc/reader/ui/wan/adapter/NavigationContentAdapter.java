package com.hjc.reader.ui.wan.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.WanNavigationBean;
import com.hjc.reader.databinding.ItemNavigationContentBinding;
import com.hjc.reader.utils.helper.RouteManager;
import com.nex3z.flowlayout.FlowLayout;


import java.util.List;

public class NavigationContentAdapter extends BaseQuickAdapter<WanNavigationBean.DataBean, BaseViewHolder> {
    public NavigationContentAdapter() {
        super(R.layout.item_navigation_content);
    }

    @Override
    protected void onItemViewHolderCreated(@NonNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, WanNavigationBean.DataBean item) {
        if (item == null) {
            return;
        }

        ItemNavigationContentBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setNavigationBean(item);

            List<WanNavigationBean.DataBean.ArticlesBean> articleList = item.getArticles();
            initTags(articleList, binding.flowLayout);
        }
    }

    /**
     * 初始化tag
     *
     * @param tagList  标签集合
     * @param flLabels 流式布局控件
     */
    private void initTags(List<WanNavigationBean.DataBean.ArticlesBean> tagList, FlowLayout flLabels) {
        flLabels.removeAllViews();
        for (WanNavigationBean.DataBean.ArticlesBean bean : tagList) {
            View view = View.inflate(getContext(), R.layout.view_navigation_tag, null);
            TextView tvTag = view.findViewById(R.id.tv_tag);
            tvTag.setText(bean.getTitle());
            flLabels.addView(tvTag);

            tvTag.setOnClickListener(v -> RouteManager.jumpToWeb(bean.getTitle(), bean.getLink()));
        }
    }
}
