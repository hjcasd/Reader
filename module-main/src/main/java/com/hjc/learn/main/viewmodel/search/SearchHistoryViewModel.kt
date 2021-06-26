package com.hjc.learn.main.viewmodel.search

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.hjc.learn.main.model.History
import com.hjc.learn.main.model.HistoryDataBase
import com.hjc.library_common.viewmodel.KotlinViewModel
import com.hjc.library_net.RetrofitClient
import com.hjc.library_net.model.WanSearchBean

class SearchHistoryViewModel(application: Application) : KotlinViewModel(application) {

    // 热门搜索数据
    val hotKeyLiveData = MutableLiveData<MutableList<WanSearchBean>>()

    // 历史数据
    val historyLiveData = MutableLiveData<List<History>>()


    /**
     * 获取热门搜索数据
     */
    fun getHotKeyData() {
        launchWan({ RetrofitClient.getApiService1().getHotKey() }, { result ->
            if (result != null && result.size > 0) {
                hotKeyLiveData.value = result
            } else {
                hotKeyLiveData.value = null
            }
        }, isShowLoading = true)
    }

    /**
     * 获取历史搜索数据
     */
    fun getHistoryData() {
        val historyList = HistoryDataBase.getInstance(getApplication()).getHistoryDao().getAllHistory()
        if (historyList != null && historyList.isNotEmpty()) {
            historyLiveData.value = historyList
        } else {
            historyLiveData.value = null
        }
    }

    /**
     * 清空历史搜索数据
     */
    fun clearHistoryData() {
        val historyDao = HistoryDataBase.getInstance(getApplication()).getHistoryDao()
        historyDao.deleteAll()
    }
}