package com.hjc.learn.main.ui.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainFragmentTestBinding
import com.hjc.library_base.fragment.BaseFragment
import com.hjc.library_common.router.path.RouteMainPath
import com.hjc.library_common.viewmodel.CommonViewModel

/**
 * @Author: HJC
 * @Date: 2021/2/2 20:39
 * @Description: 测试fragment
 */
@Route(path = RouteMainPath.URL_FRAGMENT_TEST)
class TestFragment : BaseFragment<MainFragmentTestBinding, CommonViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.main_fragment_test
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