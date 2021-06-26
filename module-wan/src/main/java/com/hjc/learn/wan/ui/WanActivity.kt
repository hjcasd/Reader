package com.hjc.learn.wan.ui

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjc.learn.wan.R
import com.hjc.learn.wan.databinding.WanActivityBinding
import com.hjc.library_base.activity.BaseActivity
import com.hjc.library_base.viewmodel.CommonViewModel
import com.hjc.library_common.router.RoutePath

/**
 * @Author: HJC
 * @Date: 2021/2/22 14:47
 * @Description: 玩安卓模块主界面
 */
@Route(path = RoutePath.Wan.WAN_PAGE)
class WanActivity : BaseActivity<WanActivityBinding, CommonViewModel>() {

    override fun getLayoutId(): Int {
       return R.layout.wan_activity
    }

    override fun createViewModel(): CommonViewModel? {
        return null
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun addListeners() {

    }

    override fun onSingleClick(v: View?) {

    }
}