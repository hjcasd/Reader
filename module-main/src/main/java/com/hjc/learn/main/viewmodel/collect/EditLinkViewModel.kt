package com.hjc.learn.main.viewmodel.collect

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.RetrofitClient
import com.hjc.library_net.model.WanCollectLinkBean

class EditLinkViewModel(application: Application) : KotlinViewModel(application) {

    // 收藏地址数据
    val editLinkLiveData = MutableLiveData<WanCollectLinkBean>()

    val nameData = MutableLiveData<String>()

    val linkData = MutableLiveData<String>()


    /**
     * 编辑收藏的网址
     *
     * @param id   网址id
     */
    fun editLink(id: Int) {
        val name = nameData.value
        if (StringUtils.isEmpty(name)) {
            ToastUtils.showShort("请输入网址名称")
            return
        }
        val link = linkData.value
        if (StringUtils.isEmpty(link)) {
            ToastUtils.showShort("请输入网址链接")
            return
        }

        launchWan({ RetrofitClient.getApiService1().editLink(id, name, link) }, {
            val bean = WanCollectLinkBean()
            bean.name = name
            bean.link = link
            editLinkLiveData.value = bean
            ToastUtils.showShort("编辑成功")
        }, isShowLoading = true)
    }

}