package com.hjc.learn.wan.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjc.learn.wan.R
import com.hjc.learn.wan.databinding.WanItemBinding
import com.hjc.library_net.model.WanArticleBean

/**
 * @Author: HJC
 * @Date: 2021/2/22 16:18
 * @Description: 玩安卓列表adapter
 */
class PlayAdapter(data: MutableList<WanArticleBean>?) :
    BaseQuickAdapter<WanArticleBean, BaseViewHolder>(R.layout.wan_item, data) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: WanArticleBean) {
        val binding = DataBindingUtil.getBinding<WanItemBinding>(holder.itemView)
        if (binding != null) {
            binding.articleBean = item
        }
    }
}