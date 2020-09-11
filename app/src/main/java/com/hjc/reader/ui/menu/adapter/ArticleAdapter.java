package com.hjc.reader.ui.menu.adapter;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseDelegateMultiAdapter;
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.CollectArticleBean;
import com.hjc.reader.databinding.ItemArticleImageBinding;
import com.hjc.reader.databinding.ItemArticleTextBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ArticleAdapter extends BaseDelegateMultiAdapter<CollectArticleBean.DataBean.DatasBean, BaseViewHolder> {
    private static final int TYPE_TEXT = 1;
    private static final int TYPE_IMAGE = 2;

    public ArticleAdapter() {
        super();
        setMultiTypeDelegate(new CollectMultiTypeDelegate());
    }

    @Override
    protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, CollectArticleBean.DataBean.DatasBean item) {
        if (item == null) {
            return;
        }
        switch (helper.getItemViewType()) {
            case TYPE_TEXT:
                initType1(helper, item);
                break;

            case TYPE_IMAGE:
                initType2(helper, item);
                break;
        }
    }

    private void initType1(BaseViewHolder helper, CollectArticleBean.DataBean.DatasBean item) {
        ItemArticleTextBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setCollectBean(item);
        }
    }

    private void initType2(BaseViewHolder helper, CollectArticleBean.DataBean.DatasBean item) {
        ItemArticleImageBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setCollectBean(item);
        }
    }

    // 代理类
    final static class CollectMultiTypeDelegate extends BaseMultiTypeDelegate<CollectArticleBean.DataBean.DatasBean> {

        public CollectMultiTypeDelegate() {
            // 绑定 item 类型
            addItemType(TYPE_TEXT, R.layout.item_article_text);
            addItemType(TYPE_IMAGE, R.layout.item_article_image);
        }

        @Override
        public int getItemType(@NotNull List<? extends CollectArticleBean.DataBean.DatasBean> data, int position) {
            CollectArticleBean.DataBean.DatasBean bean = data.get(position);
            String envelopePic = bean.getEnvelopePic();
            if (StringUtils.isEmpty(envelopePic)) {
                return TYPE_TEXT;
            } else {
                return TYPE_IMAGE;
            }
        }
    }
}
