package com.hjc.learn.main.viewmodel.web

import android.app.Application
import com.blankj.utilcode.util.ToastUtils
import com.hjc.learn.main.model.MainModel
import com.hjc.library_common.viewmodel.KotlinViewModel

/**
 * @Author: HJC
 * @Date: 2021/3/3 14:10
 * @Description: Web ViewModel
 */
class WebViewModel(application: Application) : KotlinViewModel(application) {

    private val mModel = MainModel()

    /**
     * 收藏网址链接
     *
     * @param title 标题
     * @param url   链接
     */
    fun collectLink(title: String?, url: String?) {
        launchWan({ mModel.collectLink(title, url) }, {
            ToastUtils.showShort("收藏网址成功")
        }, {
            ToastUtils.showShort("收藏网址失败")
        }, isShowLoading = true)
    }
}