package com.hjc.learn.wan.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hjc.learn.wan.R
import com.hjc.learn.wan.databinding.WanActivityBinding
import com.hjc.library_base.activity.BaseActivity
import com.hjc.library_common.router.path.RouteGankPath
import com.hjc.library_common.router.path.RouteWanPath
import com.hjc.library_common.viewmodel.CommonViewModel

/**
 * @Author: HJC
 * @Date: 2021/2/22 14:47
 * @Description: 玩安卓模块主界面
 */
@Route(path = RouteWanPath.Activity.URL_WAN_ACTIVITY)
class WanActivity : BaseActivity<WanActivityBinding, CommonViewModel>() {

    override fun getLayoutId(): Int {
       return R.layout.wan_activity
    }

    override fun createViewModel(): CommonViewModel? {
        return null
    }

    override fun initData(savedInstanceState: Bundle?) {
        val fragment = ARouter.getInstance().build(RouteWanPath.Fragment.URL_WAN_FRAGMENT).navigation() as Fragment
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_wan, fragment)
            .commit()
    }

    override fun addListeners() {

    }

    override fun onSingleClick(v: View?) {

    }
}