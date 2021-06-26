package com.hjc.learn.main.ui.collect

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainActivityCollectBinding
import com.hjc.learn.main.ui.collect.child.CollectArticleFragment
import com.hjc.learn.main.ui.collect.child.CollectLinkFragment
import com.hjc.library_base.activity.BaseActivity
import com.hjc.library_base.viewmodel.CommonViewModel
import com.hjc.library_common.adapter.MyViewPagerAdapter
import com.hjc.library_common.router.RoutePath
import java.util.*

/**
 * @Author: HJC
 * @Date: 2019/3/11 10:58
 * @Description: 我的收藏页面
 */
@Route(path = RoutePath.Main.COLLECT)
class CollectActivity : BaseActivity<MainActivityCollectBinding, CommonViewModel>() {

    private val titles = arrayOf("文章", "网址")

    override fun getLayoutId(): Int {
        return R.layout.main_activity_collect
    }

    override fun createViewModel(): CommonViewModel? {
        return null
    }

    override fun initView() {
        super.initView()

        setSupportActionBar(mBindingView.toolBar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun getImmersionBar(): ImmersionBar? {
        return ImmersionBar.with(this).titleBar(mBindingView.toolBar)
    }

    override fun initData(savedInstanceState: Bundle?) {
        val fragments: MutableList<Fragment> = ArrayList()

        val collectArticleFragment = CollectArticleFragment.newInstance()
        val collectLinkFragment = CollectLinkFragment.newInstance()

        fragments.add(collectArticleFragment)
        fragments.add(collectLinkFragment)

        val adapter = MyViewPagerAdapter(supportFragmentManager, fragments, titles)
        mBindingView.viewPager.adapter = adapter
        mBindingView.viewPager.offscreenPageLimit = 3
        mBindingView.tabLayout.setupWithViewPager(mBindingView.viewPager)
    }

    override fun addListeners() {
        mBindingView.toolBar.setNavigationOnClickListener { finish() }
    }

    override fun onSingleClick(v: View?) {

    }

}