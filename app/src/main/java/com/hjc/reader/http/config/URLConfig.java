package com.hjc.reader.http.config;


/**
 * @Author: HJC
 * @Date: 2019/1/7 11:40
 * @Description: URL字符串常量(Retrofit接口对应的URL)
 */
public class URLConfig {

    /**
     * 玩安卓URL
     * http://www.wanandroid.com/
     */
    public static final String URL_WAN_BANNER = "banner/json";
    public static final String URL_WAN_LIST = "article/list/{page}/json";
    public static final String URL_TREE_LIST = "tree/json";
    public static final String URL_NAVIGATION_LIST = "navi/json";
    public static final String URL_WAN_LOGIN = "user/login";
    public static final String URL_WAN_REGISTER = "user/register";
    public static final String URL_WAN_LOGOUT = "user/logout/json";


    /**
     * 干货模块URL
     * https://gank.io/api/
     */
    public static final String URL_GANK_TYPE = "data/{type}/{pre_page}/{page}";
    public static final String URL_GNK_RECOMMEND = "today";


    /**
     * 豆瓣模块URL
     * https://api.douban.com/
     */
    public static final String URL_MOVIE_LIST = "v2/movie/in_theaters";
    public static final String URL_MOVIE_TOP_250 = "v2/movie/top250";
    public static final String URL_MOVIE_DETAIL = "v2/movie/subject/{id}";
    public static final String URL_BOOK_LIST_ = "v2/book/search";
    public static final String URL_BOOK_DETAIL = "v2/book/{id}";


    /**
     * 糗事百科
     * http://m2.qiushibaike.com/
     */
    public static final String URL_JOKE = "";


    //检查版本更新
    public static final String URL_CHECK_VERSION = "ifs/services/bffq/v1/appVersion";

}
