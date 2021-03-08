package com.hjc.reader.ui.wan.adapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.WanNavigationBean;
import com.hjc.reader.databinding.ItemNavigationBinding;


public class NavigationAdapter extends BaseQuickAdapter<WanNavigationBean.DataBean, BaseViewHolder> {
    private OnSelectListener listener;

    public NavigationAdapter() {
        super(R.layout.item_navigation);
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

        ItemNavigationBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setNavigationBean(item);
            binding.tvChapter.setSelected(item.isSelected());

            if (listener != null) {
                binding.tvChapter.setOnClickListener(v -> {
                    listener.onSelected(helper.getAdapterPosition());
                });
            }
        }
    }

    public interface OnSelectListener {
        void onSelected(int position);
    }

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }
}
