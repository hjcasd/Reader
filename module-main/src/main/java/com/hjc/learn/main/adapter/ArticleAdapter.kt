package com.hjc.learn.main.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainItemArticleImageBinding
import com.hjc.learn.main.databinding.MainItemArticleTextBinding
import com.hjc.library_net.model.WanCollectArticleBean

/**
 * @Author: HJC
 * @Date: 2021/3/3 10:28
 * @Description: 收藏文章adapter
 */
class ArticleAdapter : BaseDelegateMultiAdapter<WanCollectArticleBean, BaseViewHolder>() {

    companion object {
        private const val TYPE_TEXT = 1
        private const val TYPE_IMAGE = 2
    }

    init {
        setMultiTypeDelegate(CollectMultiTypeDelegate())
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: WanCollectArticleBean) {
        when (holder.itemViewType) {
            TYPE_TEXT -> initType1(holder, item)
            TYPE_IMAGE -> initType2(holder, item)
        }
    }

    /**
     * 文字布局
     */
    private fun initType1(holder: BaseViewHolder, item: WanCollectArticleBean) {
        val binding = DataBindingUtil.getBinding<MainItemArticleTextBinding>(holder.itemView)
        binding?.collectBean = item
    }

    /**
     * 图片布局
     */
    private fun initType2(holder: BaseViewHolder, item: WanCollectArticleBean) {
        val binding = DataBindingUtil.getBinding<MainItemArticleImageBinding>(holder.itemView)
        binding?.collectBean = item
    }

    // 代理类
    class CollectMultiTypeDelegate : BaseMultiTypeDelegate<WanCollectArticleBean>() {

        init {
            // 绑定 item 类型
            addItemType(TYPE_TEXT, R.layout.main_item_article_text)
            addItemType(TYPE_IMAGE, R.layout.main_item_article_image)
        }

        override fun getItemType(data: List<WanCollectArticleBean>, position: Int): Int {
            val bean = data[position]
            val envelopePic: String? = bean.envelopePic

            return if (StringUtils.isEmpty(envelopePic)) {
                TYPE_TEXT
            } else {
                TYPE_IMAGE
            }
        }

    }

}