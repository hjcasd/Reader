package com.hjc.reader.http;


import com.hjc.reader.http.bean.BaseResponse;
import com.hjc.reader.http.config.URLConfig;
import com.hjc.reader.model.request.UpdateRequest;
import com.hjc.reader.model.response.WanBannerBean;
import com.hjc.reader.model.response.VersionBean;
import com.hjc.reader.model.response.WanListBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:53
 * @Description: Retrofit接口请求
 */
public interface Api {

    /**     -------------------------------------玩安卓模块------------------------------**/

    /**
     * 轮播图
     */
    @GET("banner/json")
    Observable<WanBannerBean> getWanAndroidBanner();


    /**
     * 玩安卓，文章列表、知识体系下的文章
     *
     * @param page 页码，从0开始
     * @param cid  体系id
     */
    @GET("article/list/{page}/json")
    Observable<WanListBean> getWanList(@Path("page") int page, @Query("cid") Integer cid);










    //检查版本更新
    @POST(URLConfig.URL_CHECK_VERSION)
    Observable<BaseResponse<VersionBean>> checkVersion(@Body UpdateRequest req);

    //下载App
    @Streaming
    @GET
    Observable<ResponseBody> downloadApk(@Url String url);
}
