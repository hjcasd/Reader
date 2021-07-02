package com.hjc.learn.main.model

import com.hjc.library_common.model.CommonModel
import com.hjc.library_net.bean.WanBaseResponse
import com.hjc.library_net.bean.WanPageResponse
import com.hjc.library_net.entity.WanArticleBean
import com.hjc.library_net.entity.WanCollectArticleBean
import com.hjc.library_net.entity.WanCollectLinkBean
import com.hjc.library_net.entity.WanSearchBean
import com.hjc.library_net.service.WanApiService

/**
 * @Author: HJC
 * @Date: 2021/7/2 13:40
 * @Description: 玩安卓Model
 */
class MainModel : CommonModel() {

    private val mApi = getApiService(WanApiService::class.java)

    /**
     * 玩安卓退出
     */
    suspend fun logout(): WanBaseResponse<Any> {
        return mApi.logout()
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
     * 收藏的文章列表
     */
    suspend fun getArticleList(page: Int): WanBaseResponse<WanPageResponse<MutableList<WanCollectArticleBean>>> {
        return mApi.getArticleList(page)
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

    /**
     * 取消收藏(我的收藏页面文章列表)
     */
    suspend fun unCollectOrigin(
        id: Int,
        originId: Int
    ): WanBaseResponse<WanCollectArticleBean> {
        return mApi.unCollectOrigin(id, originId)
    }

    /**
     * 收藏的网址列表
     */
    suspend fun getLinkList(): WanBaseResponse<MutableList<WanCollectLinkBean>> {
        return mApi.getLinkList()
    }

    /**
     * 收藏网址
     */
    suspend fun collectLink(
        name: String?,
        link: String?
    ): WanBaseResponse<WanCollectArticleBean> {
        return mApi.collectLink(name, link)
    }

    /**
     * 编辑收藏的网站
     */
    suspend fun editLink(
        id: Int,
        name: String?,
        link: String?
    ): WanBaseResponse<WanCollectArticleBean> {
        return mApi.editLink(id, name, link)
    }

    /**
     * 删除收藏的网站
     */
    suspend fun deleteLink(id: Int): WanBaseResponse<WanCollectArticleBean> {
        return mApi.deleteLink(id)
    }

    /**
     * 搜索热词
     */
    suspend fun getHotKey(): WanBaseResponse<MutableList<WanSearchBean>> {
        return mApi.getHotKey()
    }

    /**
     * 搜索
     */
    suspend fun search(
        page: Int,
        keyword: String?
    ): WanBaseResponse<WanPageResponse<MutableList<WanArticleBean>>> {
        return mApi.search(page, keyword)
    }
}