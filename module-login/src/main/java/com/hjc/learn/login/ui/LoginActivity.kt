package com.hjc.learn.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.KeyboardUtils
import com.hjc.learn.login.R
import com.hjc.learn.login.databinding.LoginActivityBinding
import com.hjc.learn.login.viewmodel.LoginViewModel
import com.hjc.library_base.activity.BaseActivity
import com.hjc.library_common.event.EventManager
import com.hjc.library_common.event.MessageEvent
import com.hjc.library_common.global.EventCode
import com.hjc.library_common.global.GlobalKey
import com.hjc.library_common.router.RouteManager
import com.hjc.library_common.router.path.RouteLoginPath
import com.hjc.library_widget.bar.OnViewLeftClickListener

/**
 * @Author: HJC
 * @Date: 2020/5/14 15:27
 * @Description: 登录页面
 */
@Route(path = RouteLoginPath.URL_LOGIN)
class LoginActivity : BaseActivity<LoginActivityBinding, LoginViewModel>() {

    @JvmField
    @Autowired(name = GlobalKey.ROUTER_PARAMS)
    var bundle: Bundle? = null

    override fun getLayoutId(): Int {
        return R.layout.login_activity
    }

    override fun createViewModel(): LoginViewModel {
        return ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun initData(savedInstanceState: Bundle?) {
        mBindingView.loginViewModel = mViewModel
    }

    override fun observeLiveData() {
        mViewModel?.run {
            loginData.observe(this@LoginActivity) { isLogin ->
                if (isLogin) {
                    KeyboardUtils.hideSoftInput(this@LoginActivity)
                    EventManager.sendEvent(MessageEvent(EventCode.CODE_LOGIN, null))
                    bundle?.let {
                        val path = it.getString(GlobalKey.ROUTER_PATH)
                        RouteManager.jump(path)
                    }
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
            R.id.btn_login -> mViewModel?.login()
            R.id.tv_register -> RouteManager.jumpWithCode(this, RouteLoginPath.URL_REGISTER, null, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == 1000 && data != null) {
            val username = data.getStringExtra("username")
            val password = data.getStringExtra("password")
            mBindingView.etUsername.setText(username)
            mBindingView.etPassword.setText(password)
        }
    }

}