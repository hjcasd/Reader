package com.hjc.learn.main.ui.search.child

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter.AnimationType
import com.hjc.learn.main.R
import com.hjc.learn.main.adapter.SearchAdapter
import com.hjc.learn.main.databinding.MainFragmentSearchResultBinding
import com.hjc.learn.main.viewmodel.search.SearchResultViewModel
import com.hjc.library_base.fragment.BaseFragment
import com.hjc.library_common.event.EventManager
import com.hjc.library_common.event.MessageEvent
import com.hjc.library_common.global.EventCode
import com.hjc.library_common.router.RouteManager
import com.hjc.library_common.router.path.RouteLoginPath
import com.hjc.library_common.router.path.RouteMainPath
import com.hjc.library_net.utils.AccountHelper
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author: HJC
 * @Date: 2020/9/11 10:55
 * @Description: 搜索结果页面
 */
@Route(path = RouteMainPath.URL_FRAGMENT_SEARCH_RESULT)
class SearchResultFragment : BaseFragment<MainFragmentSearchResultBinding, SearchResultViewModel>() {

    private lateinit var mAdapter: SearchAdapter

    private var mPage = 0
    private var mKeyword: String? = null

    override fun getLayoutId(): Int {
        return R.layout.main_fragment_search_result
    }

    override fun createViewModel(): SearchResultViewModel {
        return ViewModelProvider(this)[SearchResultViewModel::class.java]
    }

    override fun initView() {
        super.initView()

        initLoadSir(mBindingView.smartRefreshLayout)

        val manager = LinearLayoutManager(mContext)
        mBindingView.rvList.layoutManager = manager

        mAdapter = SearchAdapter()
        mBindingView.rvList.adapter = mAdapter

        mAdapter.setAnimationWithDefault(AnimationType.ScaleIn)
    }

    override fun initData(savedInstanceState: Bundle?) {
        EventManager.register(this)
    }

    /**
     * 点击搜索后的逻辑
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun receiveEvent(messageEvent: MessageEvent<String?>) {
        if (messageEvent.getCode() === EventCode.SEARCH_RESULT_CODE) {
            mKeyword = messageEvent.getData()
            mPage = 0
            mBindingView.rvList.scrollToPosition(0)
            mViewModel?.loadSearchData(mPage, mKeyword, true)
        }
    }

    override fun observeLiveData() {
        super.observeLiveData()

        mViewModel?.run {
            searchLiveData.observe(this@SearchResultFragment) { result ->
                if (mPage == 0) {
                    mAdapter.setNewInstance(result)
                } else {
                    mAdapter.addData(result)
                }
            }

            refreshData.observe(this@SearchResultFragment) { result ->
                if (result) {
                    mBindingView.smartRefreshLayout.finishRefresh()
                    mBindingView.smartRefreshLayout.finishLoadMore()
                }
            }

            articleLiveData.observe(this@SearchResultFragment) { mAdapter.notifyItemChanged(it.position) }
        }
    }

    override fun addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPage = 0
                mViewModel?.loadSearchData(mPage, mKeyword, false)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPage++
                mViewModel?.loadSearchData(mPage, mKeyword, false)
            }

        })

        mAdapter.setOnItemClickListener { _, _, position ->
            val dataList = mAdapter.data
            val bean = dataList[position]
            RouteManager.jumpToWeb(bean.title, bean.link)
        }

        mAdapter.addChildClickViewIds(R.id.cb_collect)
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
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
                        adapter.notifyItemChanged(position)
                        RouteManager.jump(RouteLoginPath.URL_LOGIN)
                    }
                }
            }
        }
    }

    override fun onSingleClick(v: View?) {

    }

    override fun onRetryBtnClick(v: View?) {
        super.onRetryBtnClick(v)
        mPage = 0
        mViewModel?.loadSearchData(mPage, mKeyword, true)
    }


    override fun onDestroy() {
        super.onDestroy()
        EventManager.unregister(this)
    }
}