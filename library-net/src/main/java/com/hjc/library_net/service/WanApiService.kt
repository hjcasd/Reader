package com.hjc.library_net.service

import com.hjc.library_net.bean.WanBaseResponse
import com.hjc.library_net.bean.WanPageResponse
import com.hjc.library_net.entity.*
import retrofit2.http.*

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:53
 * @Description: Retrofit接口请求1
 */
interface WanApiService {

    /**
     * 玩安卓登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): WanBaseResponse<WanLoginBean>

    /**
     * 玩安卓注册
     */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("repassword") repassword: String?
    ): WanBaseResponse<WanLoginBean>

    /**
     * 玩安卓退出
     */
    @GET("user/logout/json")
    suspend fun logout(): WanBaseResponse<Any>

    /**
     * 首页轮播图
     */
    @GET("banner/json")
    suspend fun getWanBannerList(): WanBaseResponse<MutableList<WanBannerBean>>

    /**
     * 玩安卓，文章列表、知识体系下的文章列表
     *
     * @param page 页码，从0开始
     * @param cid  体系id
     */
    @GET("article/list/{page}/json")
    suspend fun getWanList(
        @Path("page") page: Int,
        @Query("cid") cid: Int?
    ): WanBaseResponse<WanPageResponse<MutableList<WanArticleBean>>>

    /**
     * 知识体系数据
     */
    @GET("tree/json")
    suspend fun getTreeList(): WanBaseResponse<MutableList<WanSystemBean>>

    /**
     * 导航数据
     */
    @GET("navi/json")
    suspend fun getNavigationList(): WanBaseResponse<MutableList<WanNavigationBean>>


    /**
     * 收藏的文章列表
     *
     * @param page 页码
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): WanBaseResponse<WanPageResponse<MutableList<WanCollectArticleBean>>>

    /**
     * 收藏文章，errorCode返回0为成功
     *
     * @param id 文章id
     */
    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): WanBaseResponse<WanCollectArticleBean>

    /**
     * 取消收藏(首页文章列表)
     *
     * @param id 文章id
     */
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollect(@Path("id") id: Int): WanBaseResponse<WanCollectArticleBean>

    /**
     * 取消收藏(我的收藏页面文章列表)
     *
     * @param id       文章id
     * @param originId 列表页下发，无则为-1
     * (代表的是你收藏之前的那篇文章本身的id；
     * 但是收藏支持主动添加，这种情况下，没有originId则为-1)
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    suspend fun unCollectOrigin(
        @Path("id") id: Int,
        @Field("originId") originId: Int
    ): WanBaseResponse<WanCollectArticleBean>

    /**
     * 收藏的网址列表
     */
    @GET("lg/collect/usertools/json")
    suspend fun getLinkList(): WanBaseResponse<MutableList<WanCollectLinkBean>>

    /**
     * 收藏网址
     *
     * @param name 标题
     * @param link 链接
     */
    @FormUrlEncoded
    @POST("lg/collect/addtool/json")
    suspend fun collectLink(
        @Field("name") name: String?,
        @Field("link") link: String?
    ): WanBaseResponse<WanCollectArticleBean>

    /**
     * 编辑收藏的网站
     *
     * @param name 标题
     * @param link 链接
     */
    @FormUrlEncoded
    @POST("lg/collect/updatetool/json")
    suspend fun editLink(
        @Field("id") id: Int,
        @Field("name") name: String?,
        @Field("link") link: String?
    ): WanBaseResponse<WanCollectArticleBean>

    /**
     * 删除收藏的网站
     *
     * @param id 收藏网址id
     */
    @FormUrlEncoded
    @POST("lg/collect/deletetool/json")
    suspend fun deleteLink(@Field("id") id: Int): WanBaseResponse<WanCollectArticleBean>


    /**
     * 搜索热词
     */
    @GET("hotkey/json")
    suspend fun getHotKey(): WanBaseResponse<MutableList<WanSearchBean>>

    /**
     * 搜索
     *
     * @param page 页码
     * @param k    关键词
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    suspend fun search(
        @Path("page") page: Int,
        @Field("k") k: String?
    ): WanBaseResponse<WanPageResponse<MutableList<WanArticleBean>>>

}
