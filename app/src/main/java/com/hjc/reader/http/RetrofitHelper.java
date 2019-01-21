package com.hjc.reader.http;


import com.hjc.reader.http.config.HttpConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:55
 * @Description: Retrofit封装
 */
public class RetrofitHelper {
    private static RetrofitHelper mRetrofitClient;

    private RetrofitHelper() {

    }

    public static RetrofitHelper getInstance() {
        if (mRetrofitClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mRetrofitClient == null) {
                    mRetrofitClient = new RetrofitHelper();
                }
            }
        }
        return mRetrofitClient;
    }

    private Api create(String type) {
        String baseUrl;
        switch (type) {
            case HttpConfig.URL_DOUBAN:
                baseUrl = HttpConfig.URL_DOUBAN;
                break;

            case HttpConfig.URL_GANKIO:
                baseUrl = HttpConfig.URL_GANKIO;
                break;

            case HttpConfig.URL_FIR:
                baseUrl = HttpConfig.URL_FIR;
                break;

            case HttpConfig.URL_QSBK:
                baseUrl = HttpConfig.URL_QSBK;
                break;

            case HttpConfig.URL_WAN_ANDROID:
                baseUrl = HttpConfig.URL_WAN_ANDROID;
                break;

            case HttpConfig.URL_TING:
                baseUrl = HttpConfig.URL_TING;
                break;

            default:
                baseUrl = HttpConfig.URL_DOUBAN;
                break;
        }
        OkHttpClient.Builder builder = HttpClient.getInstance().getBuilder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())          //添加Gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //添加RxJava转换器
                .client(builder.build())
                .build();

        return retrofit.create(Api.class);
    }

    public Api getDouBanService() {
        return create(HttpConfig.URL_DOUBAN);
    }

    public Api getTingService() {
        return create(HttpConfig.URL_TING);
    }

    public Api getGankIOService() {
        return create(HttpConfig.URL_GANKIO);
    }

    public Api getFirService() {
        return create(HttpConfig.URL_FIR);
    }

    public Api getWanAndroidService() {
        return create(HttpConfig.URL_WAN_ANDROID);
    }

    public Api getQSBKService() {
        return create(HttpConfig.URL_QSBK);
    }
}
