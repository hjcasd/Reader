package com.hjc.learn.gank.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.RetrofitClient
import com.hjc.library_net.model.GankDayBean
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Author: HJC
 * @Date: 2021/2/23 17:25
 * @Description: 干货定制ViewModel
 */
class GankViewModel(application: Application) : KotlinViewModel(application) {

    // 干货定制列表数据
    val gankLiveData = MutableLiveData<MutableList<GankDayBean>>()

    // 刷新数据
    val refreshData = MutableLiveData<Boolean>()

    /**
     * 获取干货定制列表数据
     *
     * @param type    类型
     * @param page    页码
     * @param isFirst 是否第一次加载
     */
    fun loadGankList(type: String?, page: Int, isFirst: Boolean) {
        launchOriginal({
            RetrofitClient.getApiService2().getGankIoData("GanHuo", type, page, 20)
        }, {result->
            refreshData.value = true

            val data = result?.data
            data?.let {
                if (it.size> 0){
                    showContent()
                    gankLiveData.value = it
                }else{
                    if (page == 1) {
                        showEmpty()
                    } else {
                        ToastUtils.showShort("没有更多数据了")
                    }
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