package com.hjc.learn.gank.ui.custom

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter.AnimationType
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hjc.learn.gank.R
import com.hjc.learn.gank.adapter.FilterAdapter
import com.hjc.learn.gank.adapter.GankAdapter
import com.hjc.learn.gank.databinding.GankFragmentCustomBinding
import com.hjc.learn.gank.viewmodel.GankViewModel
import com.hjc.library_base.fragment.BaseLazyFragment
import com.hjc.library_common.router.RouteManager
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import java.util.*

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 干货定制页面
 */
class CustomFragment : BaseLazyFragment<GankFragmentCustomBinding, GankViewModel>() {

    private lateinit var tvTypeName: TextView
    private lateinit var llFilter: LinearLayout

    private lateinit var mAdapter: GankAdapter

    private var page = 1
    private var type = "All"

    companion object {
        fun newInstance(): CustomFragment {
            return CustomFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.gank_fragment_custom
    }

    override fun createViewModel(): GankViewModel {
        return ViewModelProvider(this)[GankViewModel::class.java]
    }

    override fun initView() {
        super.initView()

        initLoadSir(mBindingView.smartRefreshLayout)

        val headerView = View.inflate(mContext, R.layout.gank_layout_header_filter, null)
        tvTypeName = headerView.findViewById(R.id.tv_type_name)
        llFilter = headerView.findViewById(R.id.ll_filter)

        val manager = LinearLayoutManager(mContext)
        mBindingView.rvGank.layoutManager = manager

        mAdapter = GankAdapter()
        mBindingView.rvGank.adapter = mAdapter

        mAdapter.addHeaderView(headerView)

        mAdapter.setAnimationWithDefault(AnimationType.SlideInRight)
    }

    override fun initData() {
        mViewModel?.loadGankList(type, page, true)
    }

    override fun observeLiveData() {
        super.observeLiveData()

        mViewModel?.run {
            gankLiveData.observe(this@CustomFragment) { data ->
                if (page == 1) {
                    mAdapter.setNewInstance(data)
                } else {
                    mAdapter.addData(data)
                }
            }

            refreshData.observe(this@CustomFragment) { result ->
                if (result) {
                    mBindingView.smartRefreshLayout.finishRefresh()
                    mBindingView.smartRefreshLayout.finishLoadMore()
                }
            }
        }
    }

    override fun addListeners() {
        llFilter.setOnClickListener(this)

        mBindingView.smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {

            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 1
                mViewModel?.loadGankList(type, page, false)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                page++
                mViewModel?.loadGankList(type, page, false)
            }

        })

        mAdapter.setOnItemClickListener { _, _, position ->
            val dataList = mAdapter.data
            val bean = dataList[position]
            RouteManager.jumpToWeb(bean.desc, bean.url)
        }
    }

    override fun onSingleClick(v: View?) {
        when (v?.id) {
            R.id.ll_filter -> showFilterDialog()
        }
    }

    /**
     * 显示选择分类dialog
     */
    private fun showFilterDialog() {
        val bottomSheetDialog = BottomSheetDialog(mContext)
        val view = View.inflate(mContext, R.layout.gank_dialog_filter, null)
        bottomSheetDialog.setContentView(view)

        val rvFilter: RecyclerView = view.findViewById(R.id.rv_filter)
        val manager = LinearLayoutManager(mContext)
        rvFilter.layoutManager = manager

        val titles = arrayOf("全部", "Android", "IOS", "App", "前端", "后端", "推荐")
        val list = ArrayList(listOf(*titles))
        val adapter = FilterAdapter(list)
        rvFilter.adapter = adapter

        adapter.setOnItemClickListener { _, _, position ->
            bottomSheetDialog.dismiss()
            when (position) {
                0 -> type = "All"
                1 -> type = "Android"
                2 -> type = "iOS"
                3 -> type = "app"
                4 -> type = "frontend"
                5 -> type = "backend"
                6 -> type = "promote"
            }
            tvTypeName.text = titles[position]
            mBindingView.smartRefreshLayout.autoRefresh()
        }
        bottomSheetDialog.show()
    }

    override fun onRetryBtnClick(v: View?) {
        page = 1
        mViewModel?.loadGankList(type, page, true)
    }
}