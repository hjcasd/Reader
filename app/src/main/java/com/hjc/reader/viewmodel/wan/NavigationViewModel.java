package com.hjc.reader.viewmodel.wan;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.WanNavigationBean;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.observer.BasePageObserver;

import java.util.List;

public class NavigationViewModel extends CommonViewModel {

    // 左边导航列表数据
    private MutableLiveData<List<WanNavigationBean.DataBean>> navigationLiveData = new MutableLiveData<>();

    //右边内容列表数据
    private MutableLiveData<List<WanNavigationBean.DataBean>> navigationContentLiveData = new MutableLiveData<>();

    public NavigationViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取导航列表数据
     *
     * @param isFirst 是否第一次加载
     */
    public void loadNavigationList(boolean isFirst) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getNavigationList()
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BasePageObserver<WanNavigationBean>(this, isFirst) {
                    @Override
                    public void onSuccess(WanNavigationBean result) {
                        if (result.getErrorCode() == 0) {
                            List<WanNavigationBean.DataBean> dataList = result.getData();
                            if (dataList != null && dataList.size() > 0) {
                                navigationLiveData.setValue(dataList);
                                navigationContentLiveData.setValue(dataList);
                            }
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }
                });
    }

    public MutableLiveData<List<WanNavigationBean.DataBean>> getNavigationLiveData() {
        return navigationLiveData;
    }

    public MutableLiveData<List<WanNavigationBean.DataBean>> getNavigationContentLiveData() {
        return navigationContentLiveData;
    }

}
