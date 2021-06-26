package com.hjc.learn.wan.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.RetrofitClient
import com.hjc.library_net.model.WanArticleBean
import com.hjc.library_net.model.WanBannerBean
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class PlayViewModel(application: Application) : KotlinViewModel(application) {

    // banner数据
    val bannerData = MutableLiveData<MutableList<WanBannerBean>>()

    // 文章列表数据
    val listData = MutableLiveData<MutableList<WanArticleBean>>()

    // 刷新数据
    val refreshData = MutableLiveData<Boolean>()

    // 收藏数据
    val articleLiveData = MutableLiveData<WanArticleBean>()


    /**
     * 获取Banner数据
     */
    fun getBannerData() {
        launchWan({
            RetrofitClient.getApiService1().getWanBannerList()
        }, { result ->
            bannerData.value = result
        })
    }

    /**
     * 获取文章列表数据
     *
     * @param page    页码
     * @param isFirst 是否第一次加载
     */
    fun loadArticleList(page: Int, isFirst: Boolean) {
        launchWan({ RetrofitClient.getApiService1().getWanList(page, null) }, { result ->
            refreshData.value = true

            val data = result?.datas
            data?.let {
                if (it.size > 0) {
                    showContent()
                    listData.value = it
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
     * 收藏文章
     *
     * @param bean 文章bean
     */
    fun collectArticle(bean: WanArticleBean, position: Int) {
        launchWan({ RetrofitClient.getApiService1().collectArticle(bean.id) }, {
            bean.collect = true
            bean.position = position
            articleLiveData.value = bean
            ToastUtils.showShort("收藏成功")
        }, isShowLoading = true)
    }

    /**
     * 取消收藏文章
     *
     * @param bean 文章bean
     */
    fun unCollectArticle(bean: WanArticleBean, position: Int) {
        launchWan({ RetrofitClient.getApiService1().unCollect(bean.id) }, {
            bean.collect = false
            bean.position = position
            articleLiveData.value = bean
            ToastUtils.showShort("取消收藏成功")
        }, isShowLoading = true)
    }
}