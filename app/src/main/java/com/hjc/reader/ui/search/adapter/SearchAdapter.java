package com.hjc.reader.ui.search.adapter;

import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseDelegateMultiAdapter;
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.WanListBean;
import com.hjc.reader.databinding.ItemSearchImageBinding;
import com.hjc.reader.databinding.ItemSearchTextBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchAdapter extends BaseDelegateMultiAdapter<WanListBean.DataBean.DatasBean, BaseViewHolder> {
    private static final int TYPE_TEXT = 1;
    private static final int TYPE_IMAGE = 2;

    public SearchAdapter() {
        super();
        setMultiTypeDelegate(new SearchMultiTypeDelegate());
    }

    @Override
    protected void onItemViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, WanListBean.DataBean.DatasBean item) {
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

    private void initType1(BaseViewHolder helper, WanListBean.DataBean.DatasBean item) {
        ItemSearchTextBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setSearchBean(item);
        }
    }

    private void initType2(BaseViewHolder helper, WanListBean.DataBean.DatasBean item) {
        ItemSearchImageBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setSearchBean(item);
        }
    }

    // 代理类
    final static class SearchMultiTypeDelegate extends BaseMultiTypeDelegate<WanListBean.DataBean.DatasBean> {

        public SearchMultiTypeDelegate() {
            // 绑定 item 类型
            addItemType(TYPE_TEXT, R.layout.item_search_text);
            addItemType(TYPE_IMAGE, R.layout.item_search_image);
        }

        @Override
        public int getItemType(@NotNull List<? extends WanListBean.DataBean.DatasBean> data, int position) {
            WanListBean.DataBean.DatasBean bean = data.get(position);
            String envelopePic = bean.getEnvelopePic();
            if (StringUtils.isEmpty(envelopePic)) {
                return TYPE_TEXT;
            } else {
                return TYPE_IMAGE;
            }
        }
    }

}
