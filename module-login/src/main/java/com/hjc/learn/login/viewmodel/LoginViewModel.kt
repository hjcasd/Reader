package com.hjc.learn.login.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.RetrofitClient
import com.hjc.library_net.utils.AccountHelper

/**
 * @Author: HJC
 * @Date: 2021/2/20 15:51
 * @Description: 登录ViewModel
 */
class LoginViewModel(application: Application) : KotlinViewModel(application) {

    val loginData = MutableLiveData<Boolean>()
    val usernameData = MutableLiveData<String>()
    val passwordData = MutableLiveData<String>()

    /**
     * 登录
     */
    fun login() {
        val username = usernameData.value
        val password = passwordData.value

        if (StringUtils.isEmpty(username)) {
            ToastUtils.showShort("请输入用户名")
            return
        }
        if (username!!.length < 6) {
            ToastUtils.showShort("用户名长度至少为6位")
            return
        }
        if (StringUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入密码")
            return
        }
        if (password!!.length < 6) {
            ToastUtils.showShort("密码长度至少为6位")
            return
        }

        launchWan({
            RetrofitClient.getApiService1().login(username, password)
        }, { result ->
            result?.let {
                ToastUtils.showShort("登录成功")
                AccountHelper.init(true, it.username)
                loginData.value = true
            }
        }, isShowLoading = true)
    }
}