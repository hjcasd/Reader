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
    public final static String URL_WAN_ANDROID = "http://www.wanandroid.com/";
    public final static String URL_GANKIO = "https://gank.io/api/";
    public final static String URL_DOUBAN = "https://api.douban.com/";
    public final static String URL_QSBK = "http://m2.qiushibaike.com/";
    public final static String URL_FIR = "http://api.fir.im/apps/";
}
