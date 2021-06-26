package com.hjc.learn.gank.adapter

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjc.learn.gank.R
import com.hjc.learn.gank.databinding.GankItemFilterBinding

/**
 * @Author: HJC
 * @Date: 2021/2/23 16:39
 * @Description: 分类adapter
 */
class FilterAdapter(data: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.gank_item_filter, data) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: String) {
        val imageResId = when (item) {
            "全部" -> R.mipmap.gank_icon_filter_all
            "Android" -> R.mipmap.gank_icon_filter_android
            "IOS" -> R.mipmap.gank_icon_filter_ios
            "App" -> R.mipmap.gank_icon_filter_app
            "前端" -> R.mipmap.gank_icon_filter_web
            "后端" -> R.mipmap.gank_icon_filter_video
            "推荐" -> R.mipmap.gank_icon_filter_extra
            else -> R.mipmap.gank_icon_filter_all
        }

        val binding = DataBindingUtil.getBinding<GankItemFilterBinding>(holder.itemView)
        binding?.let {
            it.title = item
            it.ivPic.setImageResource(imageResId)
        }

    }
}