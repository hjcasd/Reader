package com.hjc.learn.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.KeyboardUtils
import com.hjc.learn.login.R
import com.hjc.learn.login.databinding.LoginActivityRegisterBinding
import com.hjc.learn.login.viewmodel.RegisterViewModel
import com.hjc.library_base.activity.BaseActivity
import com.hjc.library_common.router.RouteManager
import com.hjc.library_common.router.path.RouteLoginPath
import com.hjc.library_widget.bar.OnViewLeftClickListener

/**
 * @Author: HJC
 * @Date: 2020/5/14 15:27
 * @Description: 注册页面
 */
@Route(path = RouteLoginPath.URL_REGISTER)
class RegisterActivity : BaseActivity<LoginActivityRegisterBinding, RegisterViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.login_activity_register
    }

    override fun createViewModel(): RegisterViewModel {
        return ViewModelProvider(this)[RegisterViewModel::class.java]
    }

    override fun initData(savedInstanceState: Bundle?) {
        mBindingView.registerViewModel = mViewModel
    }

     override fun observeLiveData() {
         mViewModel?.run {
             registerData.observe(this@RegisterActivity) { isRegister ->
                 if (isRegister) {
                     KeyboardUtils.hideSoftInput(this@RegisterActivity)
                     val intent = Intent()
                     intent.putExtra("username", mBindingView.etUsername.text)
                     intent.putExtra("password", mBindingView.etPassword.text)
                     setResult(1000, intent)
                     finish()
                 }
             }
         }
    }

    override fun addListeners() {
        mBindingView.onClickListener = this

        mBindingView.titleBar.setOnViewLeftClickListener(object : OnViewLeftClickListener {

            override fun leftClick(view: View?) {
                finish()
            }

        })
    }

    override fun onSingleClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> mViewModel?.register()
            R.id.tv_register -> RouteManager.jump(RouteLoginPath.URL_LOGIN)
        }
    }

}