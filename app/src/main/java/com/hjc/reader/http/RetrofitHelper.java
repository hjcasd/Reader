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
            case HttpConfig.URL_GANKIO:
                baseUrl = HttpConfig.URL_GANKIO;
                break;

            case HttpConfig.URL_M_TIME:
                baseUrl = HttpConfig.URL_M_TIME;
                break;

            case HttpConfig.URL_M_TIME_TICKET:
                baseUrl = HttpConfig.URL_M_TIME_TICKET;
                break;

            default:
                baseUrl = HttpConfig.URL_WAN_ANDROID;
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

    public Api getMTimeService() {
        return create(HttpConfig.URL_M_TIME);
    }

    public Api getMTimeTicketService() {
        return create(HttpConfig.URL_M_TIME_TICKET);
    }

    public Api getGankIOService() {
        return create(HttpConfig.URL_GANKIO);
    }

    public Api getWanAndroidService() {
        return create(HttpConfig.URL_WAN_ANDROID);
    }

}
