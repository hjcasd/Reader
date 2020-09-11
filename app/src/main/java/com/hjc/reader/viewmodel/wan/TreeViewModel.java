package com.hjc.reader.viewmodel.wan;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.WanTreeBean;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.observer.BasePageObserver;

import java.util.List;

public class TreeViewModel extends CommonViewModel {

    // 知识体系列表数据
    private MutableLiveData<List<WanTreeBean.DataBean>> listLiveData = new MutableLiveData<>();

    public TreeViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取知识体系数据
     *
     * @param isFirst 是否第一次加载
     */
    public void loadTreeList(boolean isFirst) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getTreeList()
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BasePageObserver<WanTreeBean>(this, isFirst) {
                    @Override
                    public void onSuccess(WanTreeBean result) {
                        if (result.getErrorCode() == 0) {
                            List<WanTreeBean.DataBean> dataList = result.getData();
                            if (dataList != null && dataList.size() > 0) {
                                listLiveData.setValue(dataList);
                            }
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }
                });
    }

    public MutableLiveData<List<WanTreeBean.DataBean>> getListLiveData() {
        return listLiveData;
    }

}
