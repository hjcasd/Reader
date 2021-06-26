package com.hjc.learn.wan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hjc.learn.wan.R
import com.hjc.learn.wan.databinding.WanItemBannerBinding
import com.hjc.library_net.model.WanBannerBean
import com.youth.banner.adapter.BannerAdapter

/**
 * @Author: HJC
 * @Date: 2021/2/22 16:40
 * @Description: banner adapter
 */
class MyBannerAdapter(data: MutableList<WanBannerBean>?) :
    BannerAdapter<WanBannerBean, MyBannerAdapter.BannerViewHolder>(data) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val viewDataBinding: WanItemBannerBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.wan_item_banner, parent, false)
        return BannerViewHolder(viewDataBinding)
    }

    override fun onBindView(holder: BannerViewHolder, data: WanBannerBean?, position: Int, size: Int) {
        if (data == null) {
            return
        }

        holder.mBinding?.let {
            it.bannerBean = data
            val num = (position + 1).toString() + "/" + size
            it.tvNum.text = num
        }
    }

    class BannerViewHolder(binding: WanItemBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        var mBinding: WanItemBannerBinding? = binding
    }

}