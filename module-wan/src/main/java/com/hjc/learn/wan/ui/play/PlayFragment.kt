package com.hjc.learn.wan.ui.play

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter.AnimationType
import com.hjc.learn.wan.R
import com.hjc.learn.wan.adapter.MyBannerAdapter
import com.hjc.learn.wan.adapter.PlayAdapter
import com.hjc.learn.wan.databinding.WanFragmentPlayBinding
import com.hjc.learn.wan.viewmodel.PlayViewModel
import com.hjc.library_base.fragment.BaseLazyFragment
import com.hjc.library_common.router.RouteManager
import com.hjc.library_common.router.path.RouteLoginPath
import com.hjc.library_common.router.path.RouteWanPath
import com.hjc.library_net.entity.WanArticleBean
import com.hjc.library_net.entity.WanBannerBean
import com.hjc.library_common.utils.AccountHelper
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.Banner
import com.youth.banner.transformer.ZoomOutPageTransformer

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 玩安卓页面
 */
@Route(path = RouteWanPath.Fragment.URL_PLAY)
class PlayFragment : BaseLazyFragment<WanFragmentPlayBinding, PlayViewModel>() {

    private lateinit var banner: Banner<*, *>

    private lateinit var mAdapter: PlayAdapter

    //页码
    private var mPage = 0

    override fun getLayoutId(): Int {
        return R.layout.wan_fragment_play
    }

    override fun createViewModel(): PlayViewModel {
        return ViewModelProvider(this)[PlayViewModel::class.java]
    }

    override fun initView() {
        super.initView()

        initLoadSir(mBindingView.smartRefreshLayout)

        val headerView = View.inflate(mContext, R.layout.wan_layout_header_banner, null)
        banner = headerView.findViewById(R.id.banner)
        banner.addBannerLifecycleObserver(this)

        val manager = LinearLayoutManager(mContext)
        mBindingView.rvList.layoutManager = manager

        mAdapter = PlayAdapter(null)
        mBindingView.rvList.adapter = mAdapter

        mAdapter.addHeaderView(headerView)
        mAdapter.setAnimationWithDefault(AnimationType.SlideInLeft)
    }

    override fun initData() {
        mViewModel?.getBannerData()
        mViewModel?.loadArticleList(mPage, true)
    }

    override fun observeLiveData() {
        mViewModel?.run {
            bannerData.observe(this@PlayFragment) { data ->
                banner.adapter = MyBannerAdapter(data)
                banner.setPageTransformer(ZoomOutPageTransformer())
                    .setOnBannerListener { item: Any, _: Int ->
                        val bean = item as WanBannerBean
                        RouteManager.jumpToWeb(bean.title, bean.url)
                    }
            }

            listData.observe(this@PlayFragment) { data ->
                if (mPage == 0) {
                    mAdapter.setNewInstance(data)
                } else {
                    mAdapter.addData(data)
                }
            }

            refreshData.observe(this@PlayFragment) { result ->
                if (result) {
                    mBindingView.smartRefreshLayout.finishRefresh()
                    mBindingView.smartRefreshLayout.finishLoadMore()
                }
            }

            articleLiveData.observe(this@PlayFragment) {
                mAdapter.notifyItemChanged(it.position + 1)
            }
        }
    }

    override fun addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPage = 0
                mViewModel?.loadArticleList(mPage, false)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPage++
                mViewModel?.loadArticleList(mPage, false)
            }

        })

        mAdapter.setOnItemClickListener { _, _, position ->
            val dataList: List<WanArticleBean> = mAdapter.data
            val bean = dataList[position]
            RouteManager.jumpToWeb(bean.title, bean.link)
        }

        mAdapter.addChildClickViewIds(R.id.cb_collect)
        mAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.cb_collect -> {
                    val dataList = mAdapter.data
                    val bean = dataList[position]

                    //收藏或取消收藏前判断是否已登录
                    if (AccountHelper.isLogin) {
                        if (!bean.collect) {
                            mViewModel?.collectArticle(bean, position)
                        } else {
                            mViewModel?.unCollectArticle(bean, position)
                        }
                    } else {
                        bean.collect = false
                        mAdapter.notifyItemChanged(position + 1)
                        RouteManager.jump(RouteLoginPath.URL_LOGIN)
                    }
                }
            }
        }
    }

    override fun onSingleClick(v: View?) {

    }

    override fun onRetryBtnClick(v: View?) {
        mPage = 0
        mViewModel?.loadArticleList(mPage, true)
        mViewModel?.getBannerData()
    }

}