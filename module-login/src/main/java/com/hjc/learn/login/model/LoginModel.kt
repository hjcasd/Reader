package com.hjc.learn.login.model

import com.hjc.library_common.model.CommonModel
import com.hjc.library_net.bean.WanBaseResponse
import com.hjc.library_net.entity.WanLoginBean
import com.hjc.library_net.service.WanApiService

/**
 * @Author: HJC
 * @Date: 2021/7/2 13:40
 * @Description: 登录注册Model
 */
class LoginModel : CommonModel() {

    private val mApi = getApiService(WanApiService::class.java)

    suspend fun login(
        username: String?,
        password: String?
    ): WanBaseResponse<WanLoginBean> {
        return mApi.login(username, password)
    }

    suspend fun register(
        username: String?,
        password: String?,
        rePassword: String?
    ): WanBaseResponse<WanLoginBean> {
        return mApi.register(username, password, rePassword)
    }

}