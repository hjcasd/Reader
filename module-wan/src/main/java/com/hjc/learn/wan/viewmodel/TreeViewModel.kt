package com.hjc.learn.wan.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.RetrofitClient
import com.hjc.library_net.model.WanSystemBean
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Author: HJC
 * @Date: 2021/2/23 9:41
 * @Description: 知识体系ViewModel
 */
class TreeViewModel(application: Application) : KotlinViewModel(application) {

    // 知识体系列表数据
    val listData = MutableLiveData<MutableList<WanSystemBean>>()

    // 刷新数据
    val refreshData = MutableLiveData<Boolean>()

    /**
     * 获取知识体系数据
     *
     * @param isFirst 是否第一次加载
     */
    fun loadTreeList(isFirst: Boolean) {
        launchWan({ RetrofitClient.getApiService1().getTreeList() }, { result ->
            refreshData.value = true

            result?.let {
                if (it.size > 0) {
                    showContent()
                    listData.value = it
                } else {
                    showEmpty()
                }
            }
        }, { e ->
            refreshData.value = true

            if (e is UnknownHostException || e is SocketTimeoutException) {
                showError()
            } else {
                showTimeout()
            }
        }, isShowProgress = isFirst)
    }
}