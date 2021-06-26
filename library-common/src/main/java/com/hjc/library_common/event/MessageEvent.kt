package com.hjc.library_common.event

/**
 * @Author: HJC
 * @Date: 2019/1/4 14:55
 * @Description: 封装用于EventBus传递消息类
 */
class MessageEvent<T>(private var code: String, private var data: T? = null) {
//    private var data: T? = null

//    constructor(code: String, data: T) : this(code) {
//        this.data = data
//    }

    fun getCode(): String {
        return this.code
    }

    fun getData(): T? {
        return this.data
    }
}