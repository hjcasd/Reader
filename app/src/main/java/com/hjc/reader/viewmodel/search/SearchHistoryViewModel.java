package com.hjc.reader.viewmodel.search;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.http.observer.BaseProgressObserver;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.db.History;
import com.hjc.reader.bean.db.HistoryDao;
import com.hjc.reader.bean.db.HistoryDataBase;
import com.hjc.reader.bean.response.HotKeyBean;
import com.hjc.reader.http.RetrofitHelper;

import java.util.List;

public class SearchHistoryViewModel extends CommonViewModel {

    // 热门搜索数据
    private MutableLiveData<List<HotKeyBean.DataBean>> hotKeyLiveData = new MutableLiveData<>();

    private MutableLiveData<List<History>> historyLiveData = new MutableLiveData<>();

    public SearchHistoryViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取热门搜索数据
     */
    public void getHotKeyData() {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getHotKey()
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BaseProgressObserver<HotKeyBean>(this) {
                    @Override
                    public void onSuccess(HotKeyBean result) {
                        if (result.getErrorCode() == 0) {
                            List<HotKeyBean.DataBean> list = result.getData();
                            if (list != null && list.size() > 0) {
                                hotKeyLiveData.setValue(list);
                            } else {
                                hotKeyLiveData.setValue(null);
                            }
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 获取历史搜索数据
     */
    public void getHistoryData() {
        List<History> historyList = HistoryDataBase.getInstance(getApplication()).getHistoryDao().getAllHistory();
        if (historyList != null && !historyList.isEmpty()) {
            historyLiveData.setValue(historyList);
        } else {
            historyLiveData.setValue(null);
        }
    }

    /**
     * 清空历史搜索数据
     */
    public void clearHistoryData() {
        HistoryDao historyDao = HistoryDataBase.getInstance(getApplication()).getHistoryDao();
        historyDao.deleteAll();
    }


    public MutableLiveData<List<HotKeyBean.DataBean>> getHotKeyLiveData() {
        return hotKeyLiveData;
    }

    public MutableLiveData<List<History>> getHistoryLiveData() {
        return historyLiveData;
    }

}
