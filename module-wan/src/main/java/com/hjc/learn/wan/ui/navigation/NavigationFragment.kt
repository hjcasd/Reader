package com.hjc.learn.wan.ui.navigation

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter.AnimationType
import com.hjc.learn.wan.R
import com.hjc.learn.wan.adapter.NavigationAdapter
import com.hjc.learn.wan.adapter.NavigationContentAdapter
import com.hjc.learn.wan.databinding.WanFragmentNavigationBinding
import com.hjc.learn.wan.viewmodel.NavigationViewModel
import com.hjc.library_base.fragment.BaseLazyFragment
import com.hjc.library_common.router.path.RouteWanPath

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 导航数据页面
 */
@Route(path = RouteWanPath.Fragment.URL_NAVIGATION)
class NavigationFragment : BaseLazyFragment<WanFragmentNavigationBinding, NavigationViewModel>() {

    private lateinit var mNavigationAdapter: NavigationAdapter
    private lateinit var mNavigationContentAdapter: NavigationContentAdapter

    private lateinit var contentManager: LinearLayoutManager

    private var oldPosition = 0

    override fun getLayoutId(): Int {
        return R.layout.wan_fragment_navigation
    }

    override fun createViewModel(): NavigationViewModel {
        return ViewModelProvider(this)[NavigationViewModel::class.java]
    }

    override fun initView() {
        super.initView()

        initLoadSir(mBindingView.clRoot)

        val manager = LinearLayoutManager(mContext)
        mBindingView.rvNavigation.layoutManager = manager
        mNavigationAdapter = NavigationAdapter(null)
        mBindingView.rvNavigation.adapter = mNavigationAdapter
        mNavigationAdapter.setAnimationWithDefault(AnimationType.AlphaIn)

        contentManager = LinearLayoutManager(mContext)
        mBindingView.rvNavigationContent.layoutManager = contentManager
        mNavigationContentAdapter = NavigationContentAdapter(null)
        mBindingView.rvNavigationContent.adapter = mNavigationContentAdapter
        mNavigationContentAdapter.setAnimationWithDefault(AnimationType.AlphaIn)
    }

    override fun initData() {
        mViewModel?.loadNavigationList(true)
    }

    override fun observeLiveData() {
        mViewModel?.run {
            navigationData.observe(this@NavigationFragment, { data ->
                mNavigationAdapter.setNewInstance(data)
                selectItem(0)
            })

            navigationContentData.observe(this@NavigationFragment, { data ->
                mNavigationContentAdapter.setNewInstance(data)
            })
        }
    }

    override fun addListeners() {
        //点击侧边栏菜单,滑动到指定位置
        mNavigationAdapter.setOnSelectListener(object : NavigationAdapter.OnSelectListener {

            override fun onSelected(position: Int) {
                selectItem(position)
                contentManager.scrollToPositionWithOffset(position, 0)
            }

        })

        //侧边栏菜单随右边列表一起滚动
        mBindingView.rvNavigationContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstPosition = contentManager.findFirstVisibleItemPosition()
                if (oldPosition != firstPosition) {
                    mBindingView.rvNavigation.smoothScrollToPosition(firstPosition)
                    selectItem(firstPosition)
                }
            }
        })
    }

    /**
     * 选中左边标题
     * @param position 位置索引
     */
    private fun selectItem(position: Int) {
        if (position < 0 || mNavigationAdapter.data.size <= position) {
            return
        }
        mNavigationAdapter.data[oldPosition].isSelected = false
        mNavigationAdapter.notifyItemChanged(oldPosition)
        oldPosition = position
        mNavigationAdapter.data[position].isSelected = true
        mNavigationAdapter.notifyItemChanged(position)
    }

    override fun onSingleClick(v: View?) {

    }

    override fun onRetryBtnClick(v: View?) {
        mViewModel?.loadNavigationList(true)
    }
}