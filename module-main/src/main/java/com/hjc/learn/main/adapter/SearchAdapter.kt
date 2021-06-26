package com.hjc.learn.main.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainItemSearchImageBinding
import com.hjc.learn.main.databinding.MainItemSearchTextBinding
import com.hjc.library_net.model.WanArticleBean

/**
 * @Author: HJC
 * @Date: 2021/3/3 15:19
 * @Description: 搜索Adapter
 */
class SearchAdapter : BaseDelegateMultiAdapter<WanArticleBean, BaseViewHolder>() {

    companion object {
        private const val TYPE_TEXT = 1
        private const val TYPE_IMAGE = 2
    }

    init {
        setMultiTypeDelegate(SearchMultiTypeDelegate())
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: WanArticleBean) {
        when (holder.itemViewType) {
            TYPE_TEXT -> initType1(holder, item)
            TYPE_IMAGE -> initType2(holder, item)
        }
    }

    private fun initType1(holder: BaseViewHolder, item: WanArticleBean) {
        val binding = DataBindingUtil.getBinding<MainItemSearchTextBinding>(holder.itemView)
        binding?.searchBean = item
    }

    private fun initType2(holder: BaseViewHolder, item: WanArticleBean) {
        val binding = DataBindingUtil.getBinding<MainItemSearchImageBinding>(holder.itemView)
        binding?.searchBean = item
    }

    // 代理类
    class SearchMultiTypeDelegate : BaseMultiTypeDelegate<WanArticleBean>() {

        init {
            // 绑定 item 类型
            addItemType(TYPE_TEXT, R.layout.main_item_search_text)
            addItemType(TYPE_IMAGE, R.layout.main_item_search_image)
        }

        override fun getItemType(data: List<WanArticleBean>, position: Int): Int {
            val bean = data[position]
            val envelopePic = bean.envelopePic

            return if (StringUtils.isEmpty(envelopePic)) {
                TYPE_TEXT
            } else {
                TYPE_IMAGE
            }
        }
    }

}