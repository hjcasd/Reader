package com.hjc.reader.viewmodel.gank;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.GankDayBean;
import com.hjc.reader.bean.response.GankIOBean;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.observer.BasePageObserver;

import java.util.List;

public class WelfareViewModel extends CommonViewModel {

    // 福利社区列表数据
    private MutableLiveData<List<GankDayBean>> welfareLiveData = new MutableLiveData<>();

    public WelfareViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取福利社区列表数据
     *
     * @param page    页码
     * @param isFirst 是否第一次加载
     */
    public void loadWelfareList(int page, boolean isFirst) {
        RetrofitHelper.getInstance().getGankIOService()
                .getGankIoData("Girl", "Girl", page, 20)
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BasePageObserver<GankIOBean>(this, isFirst) {
                    @Override
                    public void onSuccess(GankIOBean result) {
                        if (result.getStatus() == 100) {
                            List<GankDayBean> resultList = result.getData();
                            if (resultList != null && resultList.size() > 0) {
                                welfareLiveData.setValue(resultList);
                            } else {
                                welfareLiveData.setValue(null);
                                if (page == 1) {
                                    showEmpty();
                                    ToastUtils.showShort("暂无数据");
                                } else {
                                    ToastUtils.showShort("没有更多数据了");
                                }
                            }
                        } else {
                            ToastUtils.showShort("获取数据失败");
                        }
                    }
                });
    }

    public MutableLiveData<List<GankDayBean>> getWelfareLiveData() {
        return welfareLiveData;
    }

}
