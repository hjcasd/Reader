package com.hjc.learn.main.ui.collect.child

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter.AnimationType
import com.hjc.learn.main.R
import com.hjc.learn.main.adapter.ArticleAdapter
import com.hjc.learn.main.databinding.MainFragmentCollectArticleBinding
import com.hjc.learn.main.viewmodel.collect.CollectArticleViewModel
import com.hjc.library_base.fragment.BaseLazyFragment
import com.hjc.library_common.router.RouteManager
import com.hjc.library_common.router.path.RouteMainPath
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * @Author: HJC
 * @Date: 2019/3/11 11:00
 * @Description: 我的收藏下的文章页面
 */
@Route(path = RouteMainPath.URL_FRAGMENT_COLLECT_ARTICLE)
class CollectArticleFragment : BaseLazyFragment<MainFragmentCollectArticleBinding, CollectArticleViewModel>() {

    private lateinit var mAdapter: ArticleAdapter

    private var mPage = 0

    override fun getLayoutId(): Int {
        return R.layout.main_fragment_collect_article
    }

    override fun createViewModel(): CollectArticleViewModel {
        return ViewModelProvider(this)[CollectArticleViewModel::class.java]
    }

    override fun initView() {
        super.initView()

        initLoadSir(mBindingView.smartRefreshLayout)

        val manager = LinearLayoutManager(mContext)
        mBindingView.rvArticle.layoutManager = manager

        mAdapter = ArticleAdapter()
        mBindingView.rvArticle.adapter = mAdapter

        mAdapter.setAnimationWithDefault(AnimationType.SlideInLeft)
    }

    override fun initData() {
        mViewModel?.loadCollectArticleList(mPage, true)
    }

    override fun observeLiveData() {
        mViewModel?.run {
            collectArticleLiveData.observe(this@CollectArticleFragment) { data ->
                if (mPage == 0) {
                    mAdapter.setNewInstance(data)
                } else {
                    mAdapter.addData(data)
                }
            }

            refreshData.observe(this@CollectArticleFragment) { result ->
                if (result) {
                    mBindingView.smartRefreshLayout.finishRefresh()
                    mBindingView.smartRefreshLayout.finishLoadMore()
                }
            }

            positionLiveData.observe(this@CollectArticleFragment) { position -> mAdapter.removeAt(position) }
        }
    }

    override fun addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPage = 0
                mViewModel?.loadCollectArticleList(mPage, false)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPage++
                mViewModel?.loadCollectArticleList(mPage, false)
            }

        })

        mAdapter.setOnItemClickListener { _, _, position ->
            val dataList = mAdapter.data
            val bean = dataList[position]
            RouteManager.jumpToWeb(bean.title, bean.link)
        }

        mAdapter.addChildClickViewIds(R.id.cb_collect)
        mAdapter.setOnItemChildClickListener { _, view, position ->
            when(view.id){
                R.id.cb_collect -> {
                    val dataList = mAdapter.data
                    val bean = dataList[position]
                    mViewModel?.unCollectArticle(bean.id, bean.originId, position)
                }
            }
        }
    }

    override fun onSingleClick(v: View?) {

    }

    override fun onRetryBtnClick(v: View?) {
        mPage = 0
        mViewModel?.loadCollectArticleList(mPage, true)
    }
}