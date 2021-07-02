package com.hjc.learn.wan.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.hjc.learn.wan.model.WanModel
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.entity.WanArticleBean
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Author: HJC
 * @Date: 2021/2/23 9:50
 * @Description: 知识体系标签ViewModel
 */
class TagViewModel(application: Application) : KotlinViewModel(application) {

    private val mModel = WanModel()

    // 知识体系tag下文章列表数据
    val articleData = MutableLiveData<MutableList<WanArticleBean>>()

    // 刷新数据
    val refreshData = MutableLiveData<Boolean>()

    /**
     * 获取知识体系tag下文章列表数据
     *
     * @param isFirst 是否第一次加载
     */
    fun loadArticleList(page: Int, id: Int, isFirst: Boolean) {
        launchWan({ mModel.getWanList(page, id) }, { result ->
            refreshData.value = true

            val data = result?.datas
            data?.let {
                if (it.size > 0) {
                    showContent()
                    articleData.value = it
                } else {
                    if (page == 0) {
                        showEmpty()
                    } else {
                        showContent()
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