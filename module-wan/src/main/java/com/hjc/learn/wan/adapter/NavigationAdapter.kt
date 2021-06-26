package com.hjc.learn.wan.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjc.learn.wan.R
import com.hjc.learn.wan.databinding.WanItemNavigationBinding
import com.hjc.library_net.model.WanNavigationBean

/**
 * @Author: HJC
 * @Date: 2021/2/22 17:08
 * @Description: 导航数据侧边adapter
 */
class NavigationAdapter(data: MutableList<WanNavigationBean>?) :
    BaseQuickAdapter<WanNavigationBean, BaseViewHolder>(R.layout.wan_item_navigation, data) {

    private var listener: OnSelectListener? = null

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: WanNavigationBean) {
        val binding = DataBindingUtil.getBinding<WanItemNavigationBinding>(holder.itemView)
        binding?.let {
            it.navigationBean = item
            it.tvChapter.isSelected = item.isSelected
            it.tvChapter.setOnClickListener { listener?.onSelected(holder.adapterPosition) }
        }
    }

    interface OnSelectListener {
        fun onSelected(position: Int)
    }

    fun setOnSelectListener(listener: OnSelectListener?) {
        this.listener = listener
    }
}