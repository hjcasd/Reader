package com.hjc.learn.gank.model

import com.hjc.library_common.model.CommonModel
import com.hjc.library_net.entity.GankIOBean
import com.hjc.library_net.entity.GankRecommendBean
import com.hjc.library_net.service.GankApiService

/**
 * @Author: HJC
 * @Date: 2021/7/2 14:01
 * @Description: 干货模块Model
 */
class GankModel : CommonModel() {

    private val mApi = getApiService(GankApiService::class.java)

    suspend fun getGankIoData(
        category: String?,
        type: String?,
        page: Int,
        count: Int
    ): GankIOBean {
        return mApi.getGankIoData(category, type, page, count)
    }

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     */
    suspend fun getRecommendData(
        year: String?,
        month: String?,
        day: String?
    ): GankRecommendBean {
        return mApi.getRecommendData(year, month, day)
    }

}