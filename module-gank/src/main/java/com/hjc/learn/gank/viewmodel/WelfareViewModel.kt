package com.hjc.learn.gank.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.hjc.learn.gank.model.GankModel
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.entity.GankDayBean
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class WelfareViewModel(application: Application) : KotlinViewModel(application) {

    private val mModel = GankModel()

    // 福利社区列表数据
    val welfareLiveData = MutableLiveData<MutableList<GankDayBean>>()

    // 刷新数据
    val refreshData = MutableLiveData<Boolean>()

    /**
     * 获取福利社区列表数据
     *
     * @param page    页码
     * @param isFirst 是否第一次加载
     */
    fun loadWelfareList(page: Int, isFirst: Boolean) {
        launchOriginal({
            mModel.getGankIoData("Girl", "Girl", page, 20)
        }, { result ->
            refreshData.value = true

            val data = result?.data
            data?.let {
                if (it.size > 0) {
                    showContent()
                    welfareLiveData.value = it
                } else {
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