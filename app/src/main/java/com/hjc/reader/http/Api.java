package com.hjc.reader.http;


import com.hjc.reader.http.bean.BaseResponse;
import com.hjc.reader.http.config.URLConfig;
import com.hjc.reader.model.request.UpdateRequest;
import com.hjc.reader.model.response.GankIOBean;
import com.hjc.reader.model.response.GankRecommendBean;
import com.hjc.reader.model.response.WanBannerBean;
import com.hjc.reader.model.response.VersionBean;
import com.hjc.reader.model.response.WanListBean;
import com.hjc.reader.model.response.WanNavigationBean;
import com.hjc.reader.model.response.WanTreeBean;

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
    Observable<WanBannerBean> getWanBannerList();

    /**
     * 玩安卓，文章列表、知识体系下的文章
     *
     * @param page 页码，从0开始
     * @param cid  体系id
     */
    @GET("article/list/{page}/json")
    Observable<WanListBean> getWanList(@Path("page") int page, @Query("cid") Integer cid);

    /**
     * 知识体系数据
     */
    @GET("tree/json")
    Observable<WanTreeBean> getTreeList();

    /**
     * 导航数据
     */
    @GET("navi/json")
    Observable<WanNavigationBean> getNavigationList();




    /**     -------------------------------------干货模块------------------------------**/

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     *
     * @param type:福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param prePage: 请求个数，大于0
     * @param page: 页码，从1开始
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{pre_page}/{page}")
    Observable<GankIOBean> getGankIoData(@Path("type") String type, @Path("pre_page") int prePage, @Path("page") int page);


    /**
     * 获取最新一天的干货数据
     */
    @GET("today")
    Observable<GankRecommendBean> getRecommendData();




    //检查版本更新
    @POST(URLConfig.URL_CHECK_VERSION)
    Observable<BaseResponse<VersionBean>> checkVersion(@Body UpdateRequest req);

    //下载App
    @Streaming
    @GET
    Observable<ResponseBody> downloadApk(@Url String url);
}
