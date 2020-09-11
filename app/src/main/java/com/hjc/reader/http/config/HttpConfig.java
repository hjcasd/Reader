package com.hjc.reader.http.config;


import com.hjc.reader.BuildConfig;

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:40
 * @Description: 网络基本配置
 */
public class HttpConfig {
    /**
     * 超时时间(s)
     */
    public static int HTTP_TIME_OUT = 20;

    /**
     * 服务器地址
     */
    public static String BASE_URL = BuildConfig.BASE_URL;

    public final static String URL_WAN_ANDROID = "https://www.wanandroid.com/";
    public final static String URL_GANKIO = "https://gank.io/api/";
    public final static String URL_M_TIME = "https://api-m.mtime.cn/";
    public final static String URL_M_TIME_TICKET = "https://ticket-api-m.mtime.cn/";
}
