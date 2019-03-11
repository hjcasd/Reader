package com.hjc.reader.http.Interceptor;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.hjc.reader.constant.AppConstants;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;


/**
 * @Author: HJC
 * @Date: 2019/3/11 17:39
 * @Description: 登录后保存cookie到SharedPreferences中
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        //获取请求返回的cookie
        if (!originalResponse.headers(AppConstants.COOKIE_HEADER).isEmpty()) {
            List<String> cookieList = originalResponse.headers(AppConstants.COOKIE_HEADER);

            LogUtils.e("原始cookie:" + cookieList.toString());

            // 返回cookie
            if (!TextUtils.isEmpty(cookieList.toString())) {
                String oldCookie = SPUtils.getInstance(AppConstants.COOKIE_CONFIG).getString(AppConstants.COOKIE_KEY);
                HashMap<String, String> cookieHashMap = new HashMap<>();

                // 之前存过cookie
                if (!TextUtils.isEmpty(oldCookie)) {
                    String[] cookies = oldCookie.split(";");
                    for (String cookie : cookies) {
                        if (cookie.contains("=")) {
                            String[] split = cookie.split("=");
                            cookieHashMap.put(split[0], split[1]);
                        } else {
                            cookieHashMap.put(cookie, "");
                        }
                    }
                }

                //将cookie存到map中
                for (String cookie : cookieList) {
                    String[] splits = cookie.split(";");
                    // 存到Map里
                    for (String str : splits) {
                        String[] split = str.split("=");
                        if (split.length == 2) {
                            cookieHashMap.put(split[0], split[1]);
                        } else {
                            cookieHashMap.put(split[0], "");
                        }
                    }
                }

                // 取出来
                StringBuilder sb = new StringBuilder();
                if (cookieHashMap.size() > 0) {
                    for (String key : cookieHashMap.keySet()) {
                        sb.append(key);
                        String value = cookieHashMap.get(key);
                        if (!TextUtils.isEmpty(value)) {
                            sb.append("=");
                            sb.append(value);
                        }
                        sb.append(";");
                    }
                }
                SPUtils.getInstance(AppConstants.COOKIE_CONFIG).put(AppConstants.COOKIE_KEY, sb.toString());
                LogUtils.e("处理后的cookie:" + sb.toString());
            }
        }
        return originalResponse;
    }
}
