package com.hjc.learn.gank.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.hjc.learn.gank.R
import com.hjc.learn.gank.databinding.GankActivityBinding
import com.hjc.library_base.activity.BaseActivity
import com.hjc.library_common.router.path.RouteGankPath
import com.hjc.library_common.viewmodel.CommonViewModel

/**
 * @Author: HJC
 * @Date: 2021/2/23 16:18
 * @Description: 干货模块主界面
 */
@Route(path = RouteGankPath.URL_ACTIVITY_GANK)
class GankActivity : BaseActivity<GankActivityBinding, CommonViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.gank_activity
    }

    override fun createViewModel(): CommonViewModel? {
        return null
    }

    override fun getImmersionBar(): ImmersionBar? {
        return ImmersionBar.with(this)
            .statusBarColor(R.color.base_color)
            .fitsSystemWindows(true)
    }

    override fun initData(savedInstanceState: Bundle?) {
        val fragment = ARouter.getInstance().build(RouteGankPath.URL_FRAGMENT_GANK).navigation() as Fragment
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_gank, fragment)
            .commit()
    }

    override fun addListeners() {

    }

    override fun onSingleClick(v: View?) {

    }
}