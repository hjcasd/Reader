package com.hjc.learn.wan.model

import com.hjc.library_common.model.CommonModel
import com.hjc.library_net.bean.WanBaseResponse
import com.hjc.library_net.bean.WanPageResponse
import com.hjc.library_net.entity.*
import com.hjc.library_net.service.WanApiService

/**
 * @Author: HJC
 * @Date: 2021/7/2 13:40
 * @Description: 玩安卓Model
 */
class WanModel : CommonModel() {

    private val mApi = getApiService(WanApiService::class.java)

    /**
     * 首页轮播图
     */
    suspend fun getWanBannerList(): WanBaseResponse<MutableList<WanBannerBean>> {
        return mApi.getWanBannerList()
    }

    /**
     * 文章列表、知识体系下的文章列表
     */
    suspend fun getWanList(
        page: Int,
        cid: Int?
    ): WanBaseResponse<WanPageResponse<MutableList<WanArticleBean>>> {
        return mApi.getWanList(page, cid)
    }

    /**
     * 知识体系数据
     */
    suspend fun getTreeList(): WanBaseResponse<MutableList<WanSystemBean>> {
        return mApi.getTreeList()
    }

    /**
     * 导航数据
     */
    suspend fun getNavigationList(): WanBaseResponse<MutableList<WanNavigationBean>> {
        return mApi.getNavigationList()
    }

    /**
     * 收藏文章
     */
    suspend fun collectArticle(id: Int): WanBaseResponse<WanCollectArticleBean> {
        return mApi.collectArticle(id)
    }

    /**
     * 取消收藏(首页文章列表)
     */
    suspend fun unCollect(id: Int): WanBaseResponse<WanCollectArticleBean> {
        return mApi.unCollect(id)
    }

}