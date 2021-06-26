package com.hjc.learn.wan.ui.tag

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter.AnimationType
import com.hjc.learn.wan.R
import com.hjc.learn.wan.adapter.TagAdapter
import com.hjc.learn.wan.databinding.WanActivityTagBinding
import com.hjc.learn.wan.viewmodel.TagViewModel
import com.hjc.library_base.activity.BaseActivity
import com.hjc.library_common.global.GlobalKey
import com.hjc.library_common.router.RouteManager
import com.hjc.library_common.router.path.RouteWanPath
import com.hjc.library_widget.bar.OnViewLeftClickListener
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener

/**
 * @Author: HJC
 * @Date: 2020/8/27 17:13
 * @Description: 知识体系tag下文章列表页面
 */
@Route(path = RouteWanPath.Activity.URL_TAG)
class TagActivity : BaseActivity<WanActivityTagBinding, TagViewModel>() {

    private lateinit var mAdapter: TagAdapter

    private var mPage = 0

    private var cid = 0

    @JvmField
    @Autowired(name = GlobalKey.ROUTER_PARAMS)
    var bundle: Bundle? = null

    override fun getLayoutId(): Int {
        return R.layout.wan_activity_tag
    }

    override fun createViewModel(): TagViewModel {
        return ViewModelProvider(this)[TagViewModel::class.java]
    }

    override fun initView() {
        super.initView()

        val manager = LinearLayoutManager(this)
        mBindingView.rvArticle.layoutManager = manager

        mAdapter = TagAdapter(null)
        mBindingView.rvArticle.adapter = mAdapter

        mAdapter.setAnimationWithDefault(AnimationType.AlphaIn)
    }

    override fun initData(savedInstanceState: Bundle?) {
        bundle?.let {
            val name = it.getString("name")
            cid = it.getInt("id", 0)
            mBindingView.titleBar.setTitle(name)
            mViewModel?.loadArticleList(mPage, cid, true)
        }
    }

    override fun observeLiveData() {
        mViewModel?.run {
            articleData.observe(this@TagActivity) { data ->
                if (mPage == 0) {
                    mAdapter.setNewInstance(data)
                } else {
                    mAdapter.addData(data)
                }
            }

            refreshData.observe(this@TagActivity) { result ->
                if (result) {
                    mBindingView.smartRefreshLayout.finishRefresh()
                    mBindingView.smartRefreshLayout.finishLoadMore()
                }
            }
        }
    }

    override fun addListeners() {
        mBindingView.titleBar.setOnViewLeftClickListener(object : OnViewLeftClickListener {
            override fun leftClick(view: View?) {
                finish()
            }

        })

        mBindingView.smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                mPage = 0
                mViewModel?.loadArticleList(mPage, cid, false)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                mPage++
                mViewModel?.loadArticleList(mPage, cid, false)
            }

        })

        mAdapter.setOnItemClickListener { _, _, position ->
            val dataList = mAdapter.data
            val bean = dataList[position]
            RouteManager.jumpToWeb(bean.title, bean.link)
        }
    }

    override fun onSingleClick(v: View?) {

    }

    override fun onRetryBtnClick(v: View?) {
        mPage = 0
        mViewModel?.loadArticleList(mPage, cid, false)
    }

}