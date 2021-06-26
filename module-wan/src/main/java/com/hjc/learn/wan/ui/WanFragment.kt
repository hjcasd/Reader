package com.hjc.learn.wan.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hjc.learn.wan.R
import com.hjc.learn.wan.databinding.WanFragmentBinding
import com.hjc.learn.wan.ui.navigation.NavigationFragment
import com.hjc.learn.wan.ui.play.PlayFragment
import com.hjc.learn.wan.ui.tree.TreeFragment
import com.hjc.library_base.fragment.BaseFragment
import com.hjc.library_common.adapter.MyViewPagerAdapter
import com.hjc.library_common.router.path.RouteWanPath
import com.hjc.library_common.viewmodel.CommonViewModel
import java.util.*

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 玩安卓fragment
 */
@Route(path = RouteWanPath.URL_FRAGMENT_WAN)
class WanFragment : BaseFragment<WanFragmentBinding, CommonViewModel>() {

    private val titles = arrayOf("玩安卓", "知识体系", "导航数据")

    override fun getLayoutId(): Int {
        return R.layout.wan_fragment
    }

    override fun createViewModel(): CommonViewModel? {
        return null
    }

    override fun initData(savedInstanceState: Bundle?) {
        val fragments = ArrayList<Fragment>()

        val playFragment =  ARouter.getInstance().build(RouteWanPath.URL_FRAGMENT_PLAY).navigation() as Fragment
        val treeFragment =  ARouter.getInstance().build(RouteWanPath.URL_FRAGMENT_TREE).navigation() as Fragment
        val navigationFragment =  ARouter.getInstance().build(RouteWanPath.URL_FRAGMENT_NAVIGATION).navigation() as Fragment

        fragments.add(playFragment)
        fragments.add(treeFragment)
        fragments.add(navigationFragment)

        val adapter = MyViewPagerAdapter(childFragmentManager, fragments, titles)
        mBindingView.viewPager.adapter = adapter
        mBindingView.viewPager.offscreenPageLimit = 3
        mBindingView.tabLayout.setupWithViewPager(mBindingView.viewPager)
    }

    override fun addListeners() {

    }

    override fun onSingleClick(v: View?) {

    }
}