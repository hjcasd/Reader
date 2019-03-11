package com.hjc.reader.http;


import com.hjc.reader.http.Interceptor.AddCookiesInterceptor;
import com.hjc.reader.http.Interceptor.LogInterceptor;
import com.hjc.reader.http.Interceptor.ReceivedCookiesInterceptor;
import com.hjc.reader.http.config.HttpConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:55
 * @Description: OkHttp封装
 */
public class HttpClient {
    private static HttpClient mHttpClient;
    private OkHttpClient.Builder mBuilder;

    private HttpClient() {
        mBuilder = new OkHttpClient.Builder();
        mBuilder.connectTimeout(HttpConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(HttpConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.HTTP_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
//                .addNetworkInterceptor(new TokenInterceptor(null))  //添加Token拦截器
                .addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(new LogInterceptor());
    }

    //双重检验锁单例模式
    public static HttpClient getInstance() {
        if (mHttpClient == null) {
            synchronized (HttpClient.class) {
                if (mHttpClient == null) {
                    mHttpClient = new HttpClient();
                }
            }
        }
        return mHttpClient;
    }

    public OkHttpClient.Builder getBuilder() {
        return mBuilder;
    }
}
