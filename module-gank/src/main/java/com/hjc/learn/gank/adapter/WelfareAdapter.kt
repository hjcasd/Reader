package com.hjc.learn.gank.adapter

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.ConvertUtils
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
        binding?.let {
            it.gankDayBean = item

            //瀑布流布局
//            val position: Int = holder.adapterPosition
//            if (position % 2 == 0) {
//                it.ivPic.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(250f))
//            } else {
//                it.ivPic.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(300f))
//            }
        }
    }
}