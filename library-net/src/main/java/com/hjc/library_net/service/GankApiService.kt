package com.hjc.library_net.service

import com.hjc.library_net.entity.GankIOBean
import com.hjc.library_net.entity.GankRecommendBean
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:53
 * @Description: Retrofit接口请求2
 */
interface GankApiService {

    @Headers("url_name:test1")
    @GET("/api/v2/data/category/{category}/type/{type}/page/{page}/count/{count}")
    suspend fun getGankIoData(
        @Path("category") category: String?,
        @Path("type") type: String?,
        @Path("page") page: Int,
        @Path("count") count: Int
    ): GankIOBean

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * eg:http://gank.io/api/day/2016/11/24
     */
    @Headers("url_name:test1")
    @GET("/api/day/{year}/{month}/{day}")
    suspend fun getRecommendData(
        @Path("year") year: String?,
        @Path("month") month: String?,
        @Path("day") day: String?
    ): GankRecommendBean

}
