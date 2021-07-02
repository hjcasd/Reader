package com.hjc.learn.main.viewmodel.search

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.hjc.learn.main.model.MainModel
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.entity.WanArticleBean
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SearchResultViewModel(application: Application) : KotlinViewModel(application) {

    private val mModel = MainModel()

    // 收藏文章列表数据
    val searchLiveData = MutableLiveData<MutableList<WanArticleBean>>()

    val articleLiveData = MutableLiveData<WanArticleBean>()

    // 刷新数据
    val refreshData = MutableLiveData<Boolean>()

    /**
     * 搜索
     *
     * @param isFirst 是否第一次加载
     */
    fun loadSearchData(page: Int, keyword: String?, isFirst: Boolean) {
        launchWan({ mModel.search(page, keyword) }, { result ->
            refreshData.value = true

            val data = result?.datas
            data?.let {
                if (it.size > 0) {
                    showContent()
                    searchLiveData.value = it
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
        launchWan({ mModel.collectArticle(bean.id) }, {
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
        launchWan({ mModel.unCollect(bean.id) }, {
            bean.collect = false
            bean.position = position
            articleLiveData.value = bean
            ToastUtils.showShort("取消收藏成功")
        }, isShowLoading = true)
    }

}