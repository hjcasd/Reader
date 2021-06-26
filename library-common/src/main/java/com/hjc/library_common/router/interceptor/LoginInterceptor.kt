package com.hjc.library_common.router.interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.blankj.utilcode.util.LogUtils
import com.hjc.library_common.global.GlobalKey
import com.hjc.library_common.router.RouteManager
import com.hjc.library_common.router.path.RouteLoginPath
import com.hjc.library_common.router.path.RouteMainPath
import com.hjc.library_net.utils.AccountHelper

/**
 * @Author: HJC
 * @Date: 2021/2/1 15:49
 * @Description: 登录状态拦截器
 */
@Interceptor(name = "login", priority = 8)
class LoginInterceptor : IInterceptor {

    private var mContext: Context? = null

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val path = postcard.path
        LogUtils.e("path: $path")
        if (!AccountHelper.isLogin) {
            when (path) {
                // 拦截登录
                RouteMainPath.URL_ACTIVITY_COLLECT -> {
                    val bundle = postcard.extras
                    bundle.putString(GlobalKey.ROUTER_PATH, path)

                    // 跳转到登录页面，把参数跟被登录拦截下来的路径传递给登录页面，登录成功后再进行跳转被拦截的页面
                    RouteManager.jumpWithBundle(RouteLoginPath.URL_LOGIN, bundle)

                    callback.onInterrupt(null)
                }
                // 无需登录
                else -> {
                    callback.onContinue(postcard)
                }
            }
        } else {
            callback.onContinue(postcard)
        }
    }

    override fun init(context: Context) {
        this.mContext = context
        LogUtils.e("登录拦截器被初始化了")
    }
}