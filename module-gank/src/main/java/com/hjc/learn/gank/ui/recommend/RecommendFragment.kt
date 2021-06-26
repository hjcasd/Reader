package com.hjc.learn.gank.ui.recommend

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter.AnimationType
import com.hitomi.tilibrary.transfer.TransferConfig
import com.hitomi.tilibrary.transfer.Transferee
import com.hjc.learn.gank.R
import com.hjc.learn.gank.adapter.RecommendAdapter
import com.hjc.learn.gank.databinding.GankFragmentRecommendBinding
import com.hjc.learn.gank.viewmodel.RecommendViewModel
import com.hjc.library_base.fragment.BaseLazyFragment
import com.hjc.library_common.router.RouteManager
import com.hjc.library_common.router.path.RouteGankPath
import com.hjc.library_net.model.GankDayBean
import com.vansz.glideimageloader.GlideImageLoader
import java.util.*

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 每日推荐页面
 */
@Route(path = RouteGankPath.URL_FRAGMENT_RECOMMEND)
class RecommendFragment : BaseLazyFragment<GankFragmentRecommendBinding, RecommendViewModel>() {

    private lateinit var mAdapter: RecommendAdapter

    private lateinit var transferee: Transferee

    override fun getLayoutId(): Int {
        return R.layout.gank_fragment_recommend
    }

    override fun createViewModel(): RecommendViewModel {
        return ViewModelProvider(this)[RecommendViewModel::class.java]
    }

    override fun initView() {
        super.initView()

        initLoadSir(mBindingView.smartRefreshLayout)

        val manager = LinearLayoutManager(mContext)
        mBindingView.rvRecommend.layoutManager = manager

        mAdapter = RecommendAdapter()
        mBindingView.rvRecommend.adapter = mAdapter

        mAdapter.setAnimationWithDefault(AnimationType.SlideInLeft)
    }

    override fun initData() {
        transferee = Transferee.getDefault(mContext)

        mViewModel?.getRecommendData(true)
    }

    override fun observeLiveData() {
        super.observeLiveData()

        mViewModel?.run {
            recommendLiveData.observe(this@RecommendFragment) { data ->
                mAdapter.setNewInstance(data)
            }

            refreshData.observe(this@RecommendFragment) { result ->
                if (result) {
                    mBindingView.smartRefreshLayout.finishRefresh()
                }
            }
        }
    }

    override fun addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshListener { mViewModel?.getRecommendData(false) }

        mAdapter.setOnItemClickListener { _, _, position ->
            val dataList = mAdapter.data
            val bean = dataList[position]

            val itemViewType = mAdapter.getItemViewType(position)
            if (itemViewType == GankDayBean.TYPE_IMAGE_ONLY) {
                val imageList: MutableList<String> = ArrayList()
                bean.url?.let {
                    imageList.add(it)
                }
                transferee.apply(
                    TransferConfig.build()
                        .setImageLoader(GlideImageLoader.with(context))
                        .setSourceUrlList(imageList)
                        .create()
                ).show()
            } else if (itemViewType == GankDayBean.TYPE_TEXT || itemViewType == GankDayBean.TYPE_IMAGE_ONE || itemViewType == GankDayBean.TYPE_IMAGE_THREE) {
                RouteManager.jumpToWeb(bean.desc, bean.url)
            }
        }
    }

    override fun onSingleClick(v: View?) {

    }

    override fun onRetryBtnClick(v: View?) {
        mViewModel?.getRecommendData(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        transferee.destroy()
    }

}