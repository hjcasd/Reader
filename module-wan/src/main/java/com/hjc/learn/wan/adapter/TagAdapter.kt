package com.hjc.learn.wan.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjc.learn.wan.R
import com.hjc.learn.wan.databinding.WanItemTagBinding
import com.hjc.library_net.model.WanArticleBean

/**
 * @Author: HJC
 * @Date: 2021/2/22 17:14
 * @Description: 标签列表adapter
 */
class TagAdapter(data: MutableList<WanArticleBean>?) :
    BaseQuickAdapter<WanArticleBean, BaseViewHolder>(R.layout.wan_item_tag, data) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: WanArticleBean) {
        val binding = DataBindingUtil.getBinding<WanItemTagBinding>(holder.itemView)
        binding?.articleBean = item
    }
}