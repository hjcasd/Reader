package com.hjc.learn.wan.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.RetrofitClient
import com.hjc.library_net.model.WanNavigationBean
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Author: HJC
 * @Date: 2021/2/23 9:36
 * @Description: 导航数据ViewModel
 */
class NavigationViewModel(application: Application) : KotlinViewModel(application) {

    // 左边导航列表数据
    val navigationData = MutableLiveData<MutableList<WanNavigationBean>>()

    //右边内容列表数据
    val navigationContentData = MutableLiveData<MutableList<WanNavigationBean>>()

    /**
     * 获取导航列表数据
     *
     * @param isFirst 是否第一次加载
     */
    fun loadNavigationList(isFirst: Boolean) {
        launchWan({ RetrofitClient.getApiService1().getNavigationList() }, { result ->
            result?.let {
                if (it.size > 0) {
                    showContent()
                    navigationData.value = it
                    navigationContentData.value = it
                } else {
                    showEmpty()
                }
            }
        }, { e ->
            if (e is UnknownHostException || e is SocketTimeoutException) {
                showError()
            } else {
                showTimeout()
            }
        }, isShowProgress = isFirst)
    }
}