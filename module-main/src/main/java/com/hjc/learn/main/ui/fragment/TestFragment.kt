package com.hjc.learn.main.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.hjc.learn.main.R
import com.hjc.learn.main.databinding.MainFragmentTestBinding
import com.hjc.library_base.fragment.BaseFragment
import com.hjc.library_base.viewmodel.CommonViewModel

/**
 * @Author: HJC
 * @Date: 2021/2/2 20:39
 * @Description: 测试fragment
 */
class TestFragment : BaseFragment<MainFragmentTestBinding, CommonViewModel>() {

    companion object {

        fun newInstance(): Fragment {
            return TestFragment()
        }
    }

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