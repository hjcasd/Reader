package com.hjc.learn.main

import com.hjc.library_base.BaseApplication
import com.hjc.library_common.config.ModuleLifecycleConfig

/**
 * @Author: HJC
 * @Date: 2021/1/9 15:17
 * @Description: Main Application
 */
class MainApp : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        ModuleLifecycleConfig.getInstance().initModuleAhead(this)
    }
}