package com.hjc.reader.viewmodel.wan;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.WanListBean;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.observer.BasePageObserver;

import java.util.List;

public class TagViewModel extends CommonViewModel {

    // 知识体系tag下文章列表数据
    private MutableLiveData<List<WanListBean.DataBean.DatasBean>> articleLiveData = new MutableLiveData<>();

    public TagViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 获取知识体系tag下文章列表数据
     *
     * @param isFirst 是否第一次加载
     */
    public void loadArticleList(int page, int id, boolean isFirst) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getWanList(page, id)
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BasePageObserver<WanListBean>(this, isFirst) {
                    @Override
                    public void onSuccess(WanListBean result) {
                        if (result.getErrorCode() == 0) {
                            WanListBean.DataBean dataBean = result.getData();
                            if (dataBean != null) {
                                List<WanListBean.DataBean.DatasBean> dataList = dataBean.getDatas();
                                if (dataList != null && dataList.size() > 0) {
                                    articleLiveData.setValue(dataList);
                                } else {
                                    articleLiveData.setValue(null);
                                    if (page == 0) {
                                        ToastUtils.showShort("暂无数据");
                                    } else {
                                        ToastUtils.showShort("没有更多数据了");
                                    }
                                }
                            }
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }
                });
    }

    public MutableLiveData<List<WanListBean.DataBean.DatasBean>> getArticleLiveData() {
        return articleLiveData;
    }

}
