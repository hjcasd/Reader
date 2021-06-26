package com.hjc.learn.wan.ui.tree

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter.AnimationType
import com.hjc.learn.wan.R
import com.hjc.learn.wan.adapter.TreeAdapter
import com.hjc.learn.wan.databinding.WanFragmentTreeBinding
import com.hjc.learn.wan.viewmodel.TreeViewModel
import com.hjc.library_base.fragment.BaseLazyFragment

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:30
 * @Description: 知识体系页面
 */
class TreeFragment : BaseLazyFragment<WanFragmentTreeBinding, TreeViewModel>() {

    private lateinit var mAdapter: TreeAdapter

    companion object {
        fun newInstance(): TreeFragment {
            return TreeFragment()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.wan_fragment_tree
    }

    override fun createViewModel(): TreeViewModel {
        return ViewModelProvider(this)[TreeViewModel::class.java]
    }

    override fun initView() {
        super.initView()

        initLoadSir(mBindingView.smartRefreshLayout)

        val manager = LinearLayoutManager(mContext)
        mBindingView.rvTree.layoutManager = manager

        mAdapter = TreeAdapter(null)
        mBindingView.rvTree.adapter = mAdapter

        mAdapter.setAnimationWithDefault(AnimationType.SlideInRight)
    }

    override fun initData() {
        mViewModel?.loadTreeList(true)
    }

    override fun observeLiveData() {
        mViewModel?.run {
            listData.observe(this@TreeFragment, { data ->
                mAdapter.setNewInstance(data)
            })

            refreshData.observe(this@TreeFragment) { result ->
                if (result) {
                    mBindingView.smartRefreshLayout.finishRefresh()
                }
            }
        }
    }

    override fun addListeners() {
        mBindingView.smartRefreshLayout.setOnRefreshListener { mViewModel?.loadTreeList(false) }
    }

    override fun onSingleClick(v: View?) {

    }

    override fun onRetryBtnClick(v: View?) {
        mViewModel?.loadTreeList(true)
    }
}