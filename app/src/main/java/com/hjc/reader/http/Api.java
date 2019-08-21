package com.hjc.reader.http;


import com.hjc.reader.model.response.CollectArticleBean;
import com.hjc.reader.model.response.CollectLinkBean;
import com.hjc.reader.model.response.GankIOBean;
import com.hjc.reader.model.response.GankRecommendBean;
import com.hjc.reader.model.response.HotKeyBean;
import com.hjc.reader.model.response.JokeBean;
import com.hjc.reader.model.response.LoginBean;
import com.hjc.reader.model.response.MovieComingBean;
import com.hjc.reader.model.response.MovieDetailBean;
import com.hjc.reader.model.response.MovieHotBean;
import com.hjc.reader.model.response.WanBannerBean;
import com.hjc.reader.model.response.WanListBean;
import com.hjc.reader.model.response.WanNavigationBean;
import com.hjc.reader.model.response.WanTreeBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:53
 * @Description: Retrofit接口请求
 */
public interface Api {

    /*     -------------------------------------玩安卓模块------------------------------        **/

    /**
     * 首页轮播图
     */
    @GET("banner/json")
    Observable<WanBannerBean> getWanBannerList();

    /**
     * 玩安卓，文章列表、知识体系下的文章列表
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


    /**
     * 收藏的文章列表
     *
     * @param page 页码
     */
    @GET("lg/collect/list/{page}/json")
    Observable<CollectArticleBean> getArticleList(@Path("page") int page);

    /**
     * 收藏文章，errorCode返回0为成功
     *
     * @param id 文章id
     */
    @POST("lg/collect/{id}/json")
    Observable<CollectArticleBean> collectArticle(@Path("id") int id);

    /**
     * 取消收藏(首页文章列表)
     *
     * @param id 文章id
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<CollectArticleBean> unCollect(@Path("id") int id);

    /**
     * 取消收藏(我的收藏页面文章列表)
     *
     * @param id       文章id
     * @param originId 列表页下发，无则为-1
     *                 (代表的是你收藏之前的那篇文章本身的id；
     *                 但是收藏支持主动添加，这种情况下，没有originId则为-1)
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    Observable<CollectArticleBean> unCollectOrigin(@Path("id") int id, @Field("originId") int originId);

    /**
     * 收藏的网址列表
     */
    @GET("lg/collect/usertools/json")
    Observable<CollectLinkBean> getLinkList();

    /**
     * 收藏网址
     *
     * @param name 标题
     * @param link 链接
     */
    @FormUrlEncoded
    @POST("lg/collect/addtool/json")
    Observable<CollectArticleBean> collectLink(@Field("name") String name, @Field("link") String link);


    /**
     * 编辑收藏的网站
     *
     * @param name 标题
     * @param link 链接
     */
    @FormUrlEncoded
    @POST("lg/collect/updatetool/json")
    Observable<CollectArticleBean> editLink(@Field("id") int id, @Field("name") String name, @Field("link") String link);

    /**
     * 删除收藏的网站
     *
     * @param id 收藏网址id
     */
    @FormUrlEncoded
    @POST("lg/collect/deletetool/json")
    Observable<CollectArticleBean> deleteLink(@Field("id") int id);


    /**
     * 玩安卓登录
     *
     * @param username 用户名
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginBean> login(@Field("username") String username, @Field("password") String password);

    /**
     * 玩安卓注册
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<LoginBean> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);

    /**
     * 玩安卓退出
     */
    @GET("user/logout/json")
    Observable<LoginBean> logout();


    /*     -------------------------------------干货模块------------------------------            **/

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     *
     * @param type:福利  | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * @param prePage: 请求个数，大于0
     * @param page:    页码，从1开始
     *                 eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{pre_page}/{page}")
    Observable<GankIOBean> getGankIoData(@Path("type") String type, @Path("pre_page") int prePage, @Path("page") int page);


    /**
     * 获取最新一天的干货数据(每日推荐)
     */
    @GET("today")
    Observable<GankRecommendBean> getRecommendData();


    /*     -------------------------------------时光网电影模块------------------------------     **/

    /**
     * 热映榜电影
     */
    @GET("Showtime/LocationMovies.api?locationId=561")
    Observable<MovieHotBean> getHotFilm();

    /**
     * 即将上映电影
     */
    @GET("Movie/MovieComingNew.api?locationId=561")
    Observable<MovieComingBean> getComingFilm();

    /**
     * 获取电影详情
     * FilmDetailBasicBean 561为武汉地区
     *
     * @param movieId 电影bean里的id
     */
    @GET("movie/detail.api?locationId=561")
    Observable<MovieDetailBean> getDetailFilm(@Query("movieId") int movieId);


    /*     -------------------------------------糗事百科模块------------------------------       **/

    /**
     * 获取段子列表
     *
     * @param page 页码，从1开始
     */
    @GET("article/list/text")
    Observable<JokeBean> getJokeList(@Query("page") int page);


    /*     -------------------------------------搜索模块------------------------------     **/

    /**
     * 搜索热词
     */
    @GET("hotkey/json")
    Observable<HotKeyBean> getHotKey();

    /**
     * 搜索
     *
     * @param page 页码
     * @param k    关键词
     */
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    Observable<WanListBean> search(@Path("page") int page, @Field("k") String k);
}
