package com.hjc.learn.main.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainItemLinkBinding
import com.hjc.library_net.model.WanCollectLinkBean

/**
 * @Author: HJC
 * @Date: 2021/3/3 10:35
 * @Description: 收藏网址adapter
 */
class LinkAdapter(data: MutableList<WanCollectLinkBean>?) :
    BaseQuickAdapter<WanCollectLinkBean, BaseViewHolder>(R.layout.main_item_link, data) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: WanCollectLinkBean) {
        val binding = DataBindingUtil.getBinding<MainItemLinkBinding>(holder.itemView)
        binding?.linkBean = item
    }
}