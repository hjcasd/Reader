package com.hjc.learn.login.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.RetrofitClient

/**
 * @Author: HJC
 * @Date: 2021/2/20 15:50
 * @Description: 注册ViewModel
 */
class RegisterViewModel(application: Application) : KotlinViewModel(application) {

    val registerData = MutableLiveData<Boolean>()
    val usernameData = MutableLiveData<String>()
    val passwordData = MutableLiveData<String>()
    val confirmData = MutableLiveData<String>()

    /**
     * 注册
     */
    fun register() {
        val username = usernameData.value
        val password = passwordData.value
        val confirmPassword = confirmData.value

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
        if (StringUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入确认密码")
            return
        }
        if (password != confirmPassword) {
            ToastUtils.showShort("两次密码输入不一致")
            return
        }

        launchWan({
            RetrofitClient.getApiService1().register(username, password, confirmPassword)
        }, { result ->
            result?.let {
                ToastUtils.showShort("注册成功")
                registerData.setValue(true)
            }
        }, isShowLoading = true)
    }
}