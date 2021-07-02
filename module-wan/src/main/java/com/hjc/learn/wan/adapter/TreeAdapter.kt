package com.hjc.learn.wan.adapter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjc.learn.wan.R
import com.hjc.learn.wan.databinding.WanItemTreeBinding
import com.hjc.library_common.router.RouteManager
import com.hjc.library_common.router.path.RouteWanPath
import com.hjc.library_net.entity.WanClassifyBean
import com.hjc.library_net.entity.WanSystemBean
import com.hjc.library_widget.FlowLayout

/**
 * @Author: HJC
 * @Date: 2021/2/22 17:18
 * @Description: 知识体系adapter
 */
class TreeAdapter(data: MutableList<WanSystemBean>?) :
    BaseQuickAdapter<WanSystemBean, BaseViewHolder>(R.layout.wan_item_tree, data) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: WanSystemBean) {
        val binding = DataBindingUtil.getBinding<WanItemTreeBinding>(holder.itemView)
        binding?.let {
            it.wanTreeBean = item
            val childrenList = item.children
            initTags(childrenList, binding.flowLayout)
        }
    }

    /**
     * 初始化tag
     *
     * @param tagList  标签集合
     * @param flLabels 流式布局控件
     */
    private fun initTags(tagList: List<WanClassifyBean>, flLabels: FlowLayout) {
        flLabels.removeAllViews()
        for (bean in tagList) {
            val view = View.inflate(context, R.layout.wan_view_tree_tag, null)
            val tvTag = view.findViewById<TextView>(R.id.tv_tag)
            tvTag.text = bean.name
            flLabels.addView(view)

            tvTag.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("name", bean.name)
                bundle.putInt("id", bean.id)
                RouteManager.jumpWithBundle(RouteWanPath.Activity.URL_TAG, bundle)
            }
        }
    }
}