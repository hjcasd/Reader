package com.hjc.learn.main.viewmodel

import android.app.Application
import com.blankj.utilcode.util.ToastUtils
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_common.event.EventManager
import com.hjc.library_common.event.MessageEvent
import com.hjc.library_common.global.EventCode
import com.hjc.library_net.RetrofitClient
import com.hjc.library_net.utils.AccountHelper

class MainViewModel(application: Application) : KotlinViewModel(application) {

    /**
     * 退出登录
     */
    fun logout() {
        launchWan({
            RetrofitClient.getApiService1().logout()
        }, {
            ToastUtils.showShort("退出账号成功")
            EventManager.sendEvent(MessageEvent(EventCode.CODE_LOGIN_OUT, null))
            AccountHelper.clear()
        }, isShowLoading = true)
    }
}