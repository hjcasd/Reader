package com.hjc.learn.main.viewmodel.search

import android.app.Application
import com.hjc.learn.main.entity.History
import com.hjc.learn.main.entity.HistoryDao
import com.hjc.learn.main.entity.HistoryDataBase
import com.hjc.library_common.viewmodel.KotlinViewModel

/**
 * @Author: HJC
 * @Date: 2021/3/3 15:24
 * @Description: 搜索ViewModel
 */
class SearchViewModel(application: Application) : KotlinViewModel(application) {

    /**
     * 保存历史搜索数据
     *
     * @param keyword 搜索关键字
     */
    fun saveHistoryData(keyword: String) {
        val historyDao: HistoryDao = HistoryDataBase.getInstance(getApplication()).getHistoryDao()
        val historyList = historyDao.getAllHistory()
        var isExit = false
        historyList?.let {
            for (entity in it) {
                if (keyword == entity.name) {
                    isExit = true
                    break
                }
            }
            if (!isExit) {
                val history = History()
                history.name = keyword
                historyDao.insert(history)
            }
        }
    }
}