package com.hjc.learn.gank.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjc.learn.gank.R
import com.hjc.learn.gank.databinding.GankItemImageBinding

/**
 * @Author: HJC
 * @Date: 2021/2/23 16:59
 * @Description: 图片adapter
 */
class ImageAdapter(data: MutableList<String?>?) :
    BaseQuickAdapter<String?, BaseViewHolder>(R.layout.gank_item_image, data) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: String?) {
        if (item == null){
            return
        }
        val binding = DataBindingUtil.getBinding<GankItemImageBinding>(holder.itemView)
        binding?.imageUrl = item
    }
}