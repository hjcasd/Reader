package com.hjc.learn.gank.adapter

import android.annotation.SuppressLint
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjc.learn.gank.R
import com.hjc.learn.gank.databinding.*
import com.hjc.learn.gank.utils.TranslateUtils
import com.hjc.library_net.model.GankDayBean
import java.util.*

/**
 * @Author: HJC * @Date: 2021/2/23 17:01
 * @Description: 每日推荐adapter
 */
class RecommendAdapter : BaseDelegateMultiAdapter<GankDayBean, BaseViewHolder>() {

    init {
        setMultiTypeDelegate(RecommendMultiTypeDelegate())
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: GankDayBean) {
        when (holder.itemViewType) {
            GankDayBean.TYPE_TEXT -> initType1(holder, item)
            GankDayBean.TYPE_IMAGE_ONE -> initType2(holder, item)
            GankDayBean.TYPE_IMAGE_THREE -> initType3(holder, item)
            GankDayBean.TYPE_TITLE -> initType4(holder, item)
            GankDayBean.TYPE_IMAGE_ONLY -> initType5(holder, item)
        }
    }

    /**
     * 无图片布局
     */
    private fun initType1(holder: BaseViewHolder, item: GankDayBean) {
        val binding = DataBindingUtil.getBinding<GankItemTextBinding>(holder.itemView)
        binding?.let {
            val translateTime = TranslateUtils.getTranslateTime(item.publishedAt!!)
            item.publishedAt = translateTime
            it.gankDayBean = item
        }
    }

    /**
     * 一张图片布局
     */
    private fun initType2(holder: BaseViewHolder, item: GankDayBean) {
        val binding = DataBindingUtil.getBinding<GankItemImageOneBinding>(holder.itemView)
        binding?.let {
            val translateTime = TranslateUtils.getTranslateTime(item.publishedAt!!)
            item.publishedAt = translateTime
            it.gankDayBean = item
        }
    }

    /**
     * 三张图片布局
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun initType3(holder: BaseViewHolder, item: GankDayBean) {
        val binding = DataBindingUtil.getBinding<GankItemImageThreeBinding>(holder.itemView)
        binding?.let {
            val translateTime = TranslateUtils.getTranslateTime(item.publishedAt!!)
            item.publishedAt = translateTime
            it.gankDayBean = item

            val manager = GridLayoutManager(context, 3)
            it.rvPic.layoutManager = manager

            val imgList: MutableList<String?> = ArrayList()
            //多个取前3个
            for (i in 0..2) {
                imgList.add(item.images!![i])
            }
            val adapter = ImageAdapter(imgList)
            it.rvPic.adapter = adapter

            //解决嵌套RecyclerView时,当点击item内部的RecyclerView后,外部RecyclerView的点击事件不生效的问题
            it.rvPic.setOnTouchListener { _, event -> holder.itemView.onTouchEvent(event) }
        }
    }

    /**
     * 标题布局
     */
    private fun initType4(holder: BaseViewHolder, item: GankDayBean) {
        val binding = DataBindingUtil.getBinding<GankItemTitleBinding>(holder.itemView)
        binding?.gankDayBean = item
    }

    /**
     * 只有图片布局
     */
    private fun initType5(holder: BaseViewHolder, item: GankDayBean) {
        val binding = DataBindingUtil.getBinding<GankItemImageOnlyBinding>(holder.itemView)
        binding?.gankDayBean = item
    }

    // 代理类
    class RecommendMultiTypeDelegate : BaseMultiTypeDelegate<GankDayBean>() {

        override fun getItemType(data: List<GankDayBean>, position: Int): Int {
            val bean = data[position]
            val title = bean.title

            return if (!StringUtils.isEmpty(title)) {
                GankDayBean.TYPE_TITLE
            } else {
                val images = bean.images
                if (images == null || images.isEmpty()) {
                    if (bean.type.equals("福利")) {
                        GankDayBean.TYPE_IMAGE_ONLY
                    } else {
                        GankDayBean.TYPE_TEXT
                    }
                } else {
                    if (images.size < 3) {
                        GankDayBean.TYPE_IMAGE_ONE
                    } else {
                        GankDayBean.TYPE_IMAGE_THREE
                    }
                }
            }
        }

        init {
            // 绑定 item 类型
            addItemType(GankDayBean.TYPE_TEXT, R.layout.gank_item_text)
            addItemType(GankDayBean.TYPE_IMAGE_ONE, R.layout.gank_item_image_one)
            addItemType(GankDayBean.TYPE_IMAGE_THREE, R.layout.gank_item_image_three)
            addItemType(GankDayBean.TYPE_TITLE, R.layout.gank_item_title)
            addItemType(GankDayBean.TYPE_IMAGE_ONLY, R.layout.gank_item_image_only)
        }
    }


}