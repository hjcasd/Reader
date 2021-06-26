package com.hjc.library_net.bean

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:46
 * @Description: 通用返回Json封装
 */
class WanBaseResponse<T> {
    var errorCode: Int? = null
    var errorMsg: String? = null
    var data: T? = null

    override fun toString(): String {
        return "WanBaseResponse{errorCode=$errorCode, errorMsg='$errorMsg', data=$data}"
    }
}
