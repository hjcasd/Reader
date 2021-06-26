package com.hjc.learn.main.viewmodel.collect

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.RetrofitClient
import com.hjc.library_net.model.WanCollectLinkBean
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Author: HJC
 * @Date: 2021/3/3 11:00
 * @Description: 收藏网址ViewModel
 */
class CollectLinkViewModel(application: Application) : KotlinViewModel(application) {

    // 收藏地址列表数据
    val collectLinkLiveData = MutableLiveData<MutableList<WanCollectLinkBean>>()

    // 刷新数据
    val refreshData = MutableLiveData<Boolean>()

    // 取消收藏位置数据
    val positionLiveData = MutableLiveData<Int>()


    /**
     * 获取收藏地址列表数据
     *
     * @param isFirst 是否第一次加载
     */
    fun loadCollectLinkList(isFirst: Boolean) {
        launchWan({ RetrofitClient.getApiService1().getLinkList() }, { result ->
            refreshData.value = true

            result?.let {
                if (it.size > 0) {
                    collectLinkLiveData.value = it
                    showContent()
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

    /**
     * 删除收藏的网址
     *
     * @param id 网址id
     */
    fun deleteLink(id: Int, position: Int) {
        launchWan({ RetrofitClient.getApiService1().deleteLink(id) }, {
            positionLiveData.value = position
            ToastUtils.showShort("删除网址成功")
        }, isShowLoading = true)
    }

}