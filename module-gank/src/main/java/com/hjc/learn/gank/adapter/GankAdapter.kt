package com.hjc.learn.gank.adapter

import android.annotation.SuppressLint
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseDelegateMultiAdapter
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjc.learn.gank.R
import com.hjc.learn.gank.databinding.GankItemImageOneBinding
import com.hjc.learn.gank.databinding.GankItemImageThreeBinding
import com.hjc.learn.gank.databinding.GankItemTextBinding
import com.hjc.library_net.entity.GankDayBean
import java.util.*

/**
 * @Author: HJC
 * @Date: 2021/2/23 17:00
 * @Description: 干货定制adapter
 */
class GankAdapter : BaseDelegateMultiAdapter<GankDayBean, BaseViewHolder>() {

    init {
        setMultiTypeDelegate(GankMultiTypeDelegate())
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<ViewDataBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: GankDayBean) {
        when (holder.itemViewType) {
            GankDayBean.TYPE_TEXT -> initType1(holder, item)
            GankDayBean.TYPE_IMAGE_ONE -> initType2(holder, item)
            GankDayBean.TYPE_IMAGE_THREE -> initType3(holder, item)
        }
    }

    private fun initType1(holder: BaseViewHolder, item: GankDayBean) {
        val binding = DataBindingUtil.getBinding<GankItemTextBinding>(holder.itemView)
        binding?.gankDayBean = item
    }

    private fun initType2(holder: BaseViewHolder, item: GankDayBean) {
        val binding = DataBindingUtil.getBinding<GankItemImageOneBinding>(holder.itemView)
        binding?.gankDayBean = item
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initType3(holder: BaseViewHolder, item: GankDayBean) {
        val binding = DataBindingUtil.getBinding<GankItemImageThreeBinding>(holder.itemView)
        binding?.let {
            it.gankDayBean = item

            val manager = GridLayoutManager(context, 3)
            it.rvPic.layoutManager = manager

            val imgList = ArrayList<String?>()
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

    // 代理类
    class GankMultiTypeDelegate : BaseMultiTypeDelegate<GankDayBean>() {

        override fun getItemType(data: List<GankDayBean>, position: Int): Int {
            val bean = data[position]
            val images = bean.images

            return if (images == null || images.isEmpty()) {
                GankDayBean.TYPE_TEXT
            } else {
                if (images.size < 3) {
                    GankDayBean.TYPE_IMAGE_ONE
                } else {
                    GankDayBean.TYPE_IMAGE_THREE
                }
            }
        }

        init {
            // 绑定 item 类型
            addItemType(GankDayBean.TYPE_TEXT, R.layout.gank_item_text)
            addItemType(GankDayBean.TYPE_IMAGE_ONE, R.layout.gank_item_image_one)
            addItemType(GankDayBean.TYPE_IMAGE_THREE, R.layout.gank_item_image_three)
        }
    }


}