package com.hjc.reader.http.config;

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:40
 * @Description: 网络基本配置
 */
public class HttpConfig {
    //超时时间(s)
    public static int HTTP_TIME_OUT = 20;

    //单个服务器地址
    public static String BASE_URL = "http://47.100.225.52:8888/";

    //多服务器地址
    public final static String URL_WAN_ANDROID = "https://www.wanandroid.com/";
    public final static String URL_GANKIO = "https://gank.io/api/";
    public final static String URL_M_TIME = "https://api-m.mtime.cn/";
    public final static String URL_M_TIME_TICKET = "https://ticket-api-m.mtime.cn/";
    public final static String URL_QSBK = "https://m2.qiushibaike.com/";
}
