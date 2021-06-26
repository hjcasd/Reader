package com.hjc.learn.gank.ui.welfare

import android.graphics.Color
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter.AnimationType
import com.hitomi.tilibrary.style.index.NumberIndexIndicator
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator
import com.hitomi.tilibrary.transfer.TransferConfig
import com.hitomi.tilibrary.transfer.Transferee
import com.hjc.learn.gank.R
import com.hjc.learn.gank.adapter.WelfareAdapter
import com.hjc.learn.gank.databinding.GankFragmentWelfareBinding
import com.hjc.learn.gank.viewmodel.WelfareViewModel
import com.hjc.library_base.fragment.BaseLazyFragment
import com.hjc.library_common.router.path.RouteGankPath
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.vansz.glideimageloader.GlideImageLoader
import java.util.*

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 福利社区页面
 */
@Route(path = RouteGankPath.URL_FRAGMENT_WELFARE)
class WelfareFragment : BaseLazyFragment<GankFragmentWelfareBinding, WelfareViewModel>() {

    private lateinit var mAdapter: WelfareAdapter
    private lateinit var transferee: Transferee

    private var mPage = 1

    override fun getLayoutId(): Int {
        return R.layout.gank_fragment_welfare
    }

    override fun createViewModel(): WelfareViewModel {
        return ViewModelProvider(this)[WelfareViewModel::class.java]
    }

    override fun initView() {
        super.initView()

        initLoadSir(mBindingView.smartRefreshLayout)

        val manager = GridLayoutManager(mContext, 2)
        mBindingView.rvWelfare.layoutManager = manager

        mAdapter = WelfareAdapter(null)
        mBindingView.rvWelfare.adapter = mAdapter

        mAdapter.setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun initData() {
        transferee = Transferee.getDefault(mContext)

        mViewModel?.loadWelfareList(mPage, true)
    }

    override fun observeLiveData() {
        super.observeLiveData()

        mViewModel?.run {
            welfareLiveData.observe(this@WelfareFragment) { data ->
                if (mPage == 1) {
                    mAdapter.setNewInstance(data)
                } else {
                    mAdapter.addData(data)
                }
            }

            refreshData.observe(this@WelfareFragment) { result ->
                if (result) {
                    mBindingView.smartRefreshLayout.finishRefresh()
                    mBindingView.smartRefreshLayout.finishLoadMore()
                }
            }
        }
    }

    override fun addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPage = 1
                mViewModel?.loadWelfareList(mPage, false)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPage++
                mViewModel?.loadWelfareList(mPage, false)
            }

        })

        mAdapter.setOnItemClickListener { _, _, position ->
            val dataList = mAdapter.data
            val imagesList = ArrayList<String>()
            for (bean in dataList) {
                bean.url?.let {
                    imagesList.add(it)
                }
            }
            val config = TransferConfig.build()
                .setSourceUrlList(imagesList) // 资源 url 集合, String 格式
                .setMissPlaceHolder(R.drawable.gank_img_default_girl) // 资源加载前的占位图
                .setErrorPlaceHolder(R.drawable.gank_img_default_girl) // 资源加载错误后的占位图
                .setProgressIndicator(ProgressPieIndicator()) // 资源加载进度指示器, 可以实现 IProgressIndicator 扩展
                .setIndexIndicator(NumberIndexIndicator()) // 资源数量索引指示器，可以实现 IIndexIndicator 扩展
                .setImageLoader(GlideImageLoader.with(mContext)) // 图片加载器，可以实现 ImageLoader 扩展
                .setBackgroundColor(Color.parseColor("#000000")) // 背景色
                .setDuration(300) // 开启、关闭、手势拖拽关闭、显示、扩散消失等动画时长
                .setOffscreenPageLimit(2) // 第一次初始化或者切换页面时预加载资源的数量，与 justLoadHitImage 属性冲突，默认为 1
                .setNowThumbnailIndex(position) // 缩略图在图组中的索引
                .enableJustLoadHitPage(true) // 是否只加载当前显示在屏幕中的的资源，默认关闭
                .enableDragClose(true) // 是否开启下拉手势关闭，默认开启
                .enableDragHide(false) // 下拉拖拽关闭时，是否先隐藏页面上除主视图以外的其他视图，默认开启
                .enableDragPause(false) // 下拉拖拽关闭时，如果当前是视频，是否暂停播放，默认关闭
                .enableHideThumb(false) // 是否开启当 transferee 打开时，隐藏缩略图, 默认关闭
                .enableScrollingWithPageChange(false) // 是否启动列表随着页面的切换而滚动你的列表，默认关闭
                .bindRecyclerView(mBindingView.rvWelfare, R.id.iv_pic) // 绑定一个 RecyclerView， 所有绑定方法只能调用一个
            transferee.apply(config).show()
        }
    }

    override fun onSingleClick(v: View?) {

    }

    override fun onRetryBtnClick(v: View?) {
        mPage = 1
        mViewModel?.loadWelfareList(mPage, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        transferee.destroy()
    }
}