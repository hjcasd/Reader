package com.hjc.library_common.utils

import com.blankj.utilcode.util.SPUtils

/**
 * @Author: HJC
 * @Date: 2019/2/20 15:39
 * @Description: 账户管理类
 */
object AccountHelper  {

    private const val KEY_IS_LOGIN = "isLogin"
    private const val KEY_USERNAME = "username"
    private const val KEY_COOKIE = "cookie"

    fun init(login: Boolean, name: String) {
        isLogin = login
        username = name
    }

    /**
     * 获取用户名
     */
    var username: String
        get() = SPUtils.getInstance().getString(KEY_USERNAME)
        set(value) {
            SPUtils.getInstance().put(KEY_USERNAME, value)
        }

    /**
     * 获取cookie
     */
    var cookie: String
        get() = SPUtils.getInstance().getString(KEY_COOKIE)
        set(value) {
            SPUtils.getInstance().put(KEY_COOKIE, value)
        }

    /**
     * 用户是否登录
     */
    var isLogin: Boolean
        get() = SPUtils.getInstance().getBoolean(KEY_IS_LOGIN)
        set(value) {
            SPUtils.getInstance().put(KEY_IS_LOGIN, value)
        }

    /**
     * 清除账户信息
     */
    fun clear() {
        isLogin = false
        username = ""
        cookie = ""
    }

}