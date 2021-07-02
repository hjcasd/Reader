package com.hjc.learn.gank.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.hjc.learn.gank.model.GankModel
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.entity.GankDayBean
import com.hjc.library_net.entity.GankRecommendResultBean
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

/**
 * @Author: HJC
 * @Date: 2021/2/23 17:26
 * @Description: 每日推荐ViewModel
 */
class RecommendViewModel(application: Application) : KotlinViewModel(application) {

    private val mModel = GankModel()

    // 每日推荐列表数据
    val recommendLiveData = MutableLiveData<MutableList<GankDayBean>>()

    // 刷新数据
    val refreshData = MutableLiveData<Boolean>()

    /**
     * 获取每日推荐数据
     *
     * @param isFirst 是否第一次加载
     */
    fun getRecommendData(isFirst: Boolean) {
        launchOriginal({
            mModel.getRecommendData("2016", "11", "24")
        }, { result ->
            refreshData.value = true

            result?.let {
                if (!it.error) {
                    val resultsBean = result.results
                    if (resultsBean != null) {
                        showContent()
                        parseRecommendData(resultsBean)
                    } else {
                        showEmpty()
                    }
                } else {
                    ToastUtils.showShort("获取数据失败")
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
     * 解析推荐数据
     *
     * @param resultsBean 推荐数据对应的bean
     */
    private fun parseRecommendData(resultsBean: GankRecommendResultBean) {
        val dataList: MutableList<GankDayBean> = ArrayList<GankDayBean>()

        //福利区
        val welfareList = resultsBean.welfare
        if (welfareList != null && welfareList.isNotEmpty()) {
            val bean = GankDayBean()
            bean.title = "福利"
            dataList.add(bean)
            dataList.addAll(welfareList)
        }

        //Android区
        val androidList = resultsBean.Android
        if (androidList != null && androidList.isNotEmpty()) {
            val bean = GankDayBean()
            bean.title = "Android"
            dataList.add(bean)
            dataList.addAll(androidList)
        }

        //IOS区
        val iosList = resultsBean.iOS
        if (iosList != null && iosList.isNotEmpty()) {
            val bean = GankDayBean()
            bean.title = "IOS"
            dataList.add(bean)
            dataList.addAll(iosList)
        }

        //Web区
        val webList = resultsBean.front
        if (webList != null && webList.isNotEmpty()) {
            val bean = GankDayBean()
            bean.title = "前端"
            dataList.add(bean)
            dataList.addAll(webList)
        }

        //休息视频
        val restList = resultsBean.rest
        if (restList != null && restList.isNotEmpty()) {
            val bean = GankDayBean()
            bean.title = "休息视频"
            dataList.add(bean)
            dataList.addAll(restList)
        }

        //拓展资源
        val extraList = resultsBean.extra
        if (extraList != null && extraList.isNotEmpty()) {
            val bean = GankDayBean()
            bean.title = "拓展资源"
            dataList.add(bean)
            dataList.addAll(extraList)
        }
        val recommendList = resultsBean.recommend
        if (recommendList != null && recommendList.isNotEmpty()) {
            val bean = GankDayBean()
            bean.title = "推荐"
            dataList.add(bean)
            dataList.addAll(recommendList)
        }
        recommendLiveData.value = dataList
    }
}