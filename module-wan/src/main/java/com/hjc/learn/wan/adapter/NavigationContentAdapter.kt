package com.hjc.learn.wan.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjc.learn.wan.R
import com.hjc.learn.wan.databinding.WanItemNavigationContentBinding
import com.hjc.library_common.router.RouteManager.jumpToWeb
import com.hjc.library_net.model.WanArticleBean
import com.hjc.library_net.model.WanNavigationBean
import com.hjc.library_widget.FlowLayout

/**
 * @Author: HJC
 * @Date: 2021/2/22 17:08
 * @Description: 导航数据内容adapter
 */
class NavigationContentAdapter(data: MutableList<WanNavigationBean>?) :
    BaseQuickAdapter<WanNavigationBean, BaseViewHolder>(R.layout.wan_item_navigation_content, data) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: WanNavigationBean) {
        val binding = DataBindingUtil.getBinding<WanItemNavigationContentBinding>(holder.itemView)
        binding?.let {
            it.navigationBean = item
            val articleList = item.articles
            initTags(articleList, it.flowLayout)
        }
    }

    /**
     * 初始化tag
     *
     * @param tagList  标签集合
     * @param flLabels 流式布局控件
     */
    private fun initTags(tagList: List<WanArticleBean>, flLabels: FlowLayout) {
        flLabels.removeAllViews()
        for (bean in tagList) {
            val view = View.inflate(context, R.layout.wan_view_navigation_tag, null)
            val tvTag = view.findViewById<TextView>(R.id.tv_tag)
            tvTag.text = bean.title
            flLabels.addView(view)
            tvTag.setOnClickListener { jumpToWeb(bean.title, bean.link) }
        }
    }
}