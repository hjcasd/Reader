package com.hjc.learn.gank.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjc.learn.gank.R
import com.hjc.learn.gank.databinding.GankItemWelfareBinding
import com.hjc.library_net.model.GankDayBean

/**
 * @Author: HJC
 * @Date: 2021/2/23 17:13
 * @Description: 福利社区adapter
 */
class WelfareAdapter(data: MutableList<GankDayBean>?) :
    BaseQuickAdapter<GankDayBean, BaseViewHolder>(R.layout.gank_item_welfare, data) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: GankDayBean) {
        val binding = DataBindingUtil.getBinding<GankItemWelfareBinding>(holder.itemView)
        binding?.gankDayBean = item

//        瀑布流布局
//        int position = helper.getAdapterPosition();
//        if (position % 2 == 0) {
//            ivPic.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(250f)));
//        } else {
//            ivPic.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  DensityUtil.dp2px(300f)));
//        }
    }
}