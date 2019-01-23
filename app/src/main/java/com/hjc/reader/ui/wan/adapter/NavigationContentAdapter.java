package com.hjc.reader.ui.wan.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.model.response.WanNavigationBean;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class NavigationContentAdapter extends BaseQuickAdapter<WanNavigationBean.DataBean, BaseViewHolder> {
    public NavigationContentAdapter(@Nullable List<WanNavigationBean.DataBean> data) {
        super(R.layout.item_rv_navigation_content, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WanNavigationBean.DataBean item) {
        String name = item.getName();
        helper.setText(R.id.tv_chapter_name, name);

        FlowLayout flowLayout = helper.getView(R.id.flow_layout);

        List<WanNavigationBean.DataBean.ArticlesBean> articleList = item.getArticles();
        List<String> tagList = new ArrayList<>();
        for (WanNavigationBean.DataBean.ArticlesBean bean: articleList) {
            tagList.add(bean.getTitle());
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
            View view = View.inflate(mContext, R.layout.view_navigation_tag, null);
            TextView tvTag = view.findViewById(R.id.tv_tag);
            tvTag.setText(tag);
            flLabels.addView(tvTag);
        }
    }
}
