package com.hjc.learn.gank.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjc.learn.gank.R
import com.hjc.learn.gank.databinding.GankFragmentBinding
import com.hjc.learn.gank.ui.custom.CustomFragment
import com.hjc.learn.gank.ui.recommend.RecommendFragment
import com.hjc.learn.gank.ui.welfare.WelfareFragment
import com.hjc.library_base.fragment.BaseFragment
import com.hjc.library_base.viewmodel.CommonViewModel
import com.hjc.library_common.adapter.MyViewPagerAdapter
import com.hjc.library_common.router.RoutePath
import java.util.*

/**
 * @Author: HJC
 * @Date: 2019/1/21 16:10
 * @Description: 干货模块fragment
 */
@Route(path = RoutePath.Gank.GANK_FRAGMENT)
class GankFragment : BaseFragment<GankFragmentBinding, CommonViewModel>() {

    private val titles = arrayOf("每日推荐", "福利社区", "干货定制")

    override fun getLayoutId(): Int {
        return R.layout.gank_fragment
    }

    override fun createViewModel(): CommonViewModel? {
        return null
    }

    override fun initData(savedInstanceState: Bundle?) {
        val fragments = ArrayList<Fragment>()

        val recommendFragment: RecommendFragment = RecommendFragment.newInstance()
        val welfareFragment: WelfareFragment = WelfareFragment.newInstance()
        val customFragment: CustomFragment = CustomFragment.newInstance()

        fragments.add(recommendFragment)
        fragments.add(welfareFragment)
        fragments.add(customFragment)

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