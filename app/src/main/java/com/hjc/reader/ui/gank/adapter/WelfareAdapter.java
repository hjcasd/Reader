package com.hjc.reader.ui.gank.adapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjc.reader.R;
import com.hjc.reader.bean.response.GankDayBean;
import com.hjc.reader.databinding.ItemWelfareBinding;


public class WelfareAdapter extends BaseQuickAdapter<GankDayBean, BaseViewHolder> {

    public WelfareAdapter() {
        super(R.layout.item_welfare);
    }

    @Override
    protected void onItemViewHolderCreated(@NonNull BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, GankDayBean item) {
        if (item == null) {
            return;
        }

        ItemWelfareBinding binding = helper.getBinding();
        if (binding != null) {
            binding.setGankDayBean(item);
        }

//        瀑布流布局
//        int position = helper.getAdapterPosition();
//        if (position % 2 == 0) {
//            ivPic.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(250f)));
//        } else {
//            ivPic.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  DensityUtil.dp2px(300f)));
//        }
    }
}
