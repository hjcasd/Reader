package com.hjc.reader.viewmodel.search;


import android.app.Application;

import androidx.annotation.NonNull;

import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.db.History;
import com.hjc.reader.bean.db.HistoryDao;
import com.hjc.reader.bean.db.HistoryDataBase;

import java.util.List;

public class SearchViewModel extends CommonViewModel {

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 保存历史搜索数据
     *
     * @param keyword 搜索关键字
     */
    public void saveHistoryData(String keyword) {
        HistoryDao historyDao = HistoryDataBase.getInstance(getApplication()).getHistoryDao();

        List<History> historyList = historyDao.getAllHistory();
        boolean isExit = false;
        for (History entity : historyList) {
            if (keyword.equals(entity.getName())) {
                isExit = true;
                break;
            }
        }

        if (!isExit) {
            History history = new History();
            history.setName(keyword);
            historyDao.insert(history);
        }
    }
}
