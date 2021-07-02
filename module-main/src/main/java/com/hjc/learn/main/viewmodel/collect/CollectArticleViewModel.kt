package com.hjc.learn.main.viewmodel.collect

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.hjc.learn.main.model.MainModel
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.entity.WanCollectArticleBean
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @Author: HJC
 * @Date: 2021/3/3 11:00
 * @Description: 收藏文章ViewModel
 */
class CollectArticleViewModel(application: Application) : KotlinViewModel(application) {

    private val mModel = MainModel()

    // 收藏文章列表数据
    val collectArticleLiveData = MutableLiveData<MutableList<WanCollectArticleBean>>()

    // 刷新数据
    val refreshData = MutableLiveData<Boolean>()

    // 取消收藏位置数据
    val positionLiveData = MutableLiveData<Int>()


    /**
     * 获取收藏文章列表数据
     *
     * @param page    页码
     * @param isFirst 是否第一次加载
     */
    fun loadCollectArticleList(page: Int, isFirst: Boolean) {
        launchWan({ mModel.getArticleList(page) }, { result ->
            refreshData.value = true

            val data = result?.datas
            data?.let {
                if (it.size > 0) {
                    collectArticleLiveData.value = it
                    showContent()
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

    /**
     * @param id       文章id
     * @param originId 文章originId
     * @param position 位置
     */
    fun unCollectArticle(id: Int, originId: Int, position: Int) {
        launchWan({ mModel.unCollectOrigin(id, originId) }, {
            positionLiveData.value = position
            ToastUtils.showShort("已取消收藏")
        }, isShowLoading = true)
    }
}