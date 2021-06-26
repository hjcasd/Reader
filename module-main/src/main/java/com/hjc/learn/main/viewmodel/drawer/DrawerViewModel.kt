package com.hjc.learn.main.viewmodel.drawer

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.utils.AccountHelper

class DrawerViewModel(application: Application) : KotlinViewModel(application) {

    val usernameData = MutableLiveData<String>()

    /**
     * 判断是否已经登录,并显示用户名
     */
    fun setUserName() {
        if (AccountHelper.isLogin) {
            usernameData.value = AccountHelper.username
        } else {
            usernameData.value = "登录/注册"
        }
    }
}