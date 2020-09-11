package com.hjc.reader.viewmodel.menu;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.http.observer.BaseProgressObserver;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.CollectArticleBean;
import com.hjc.reader.bean.response.CollectLinkBean;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.observer.BasePageObserver;

import java.util.List;

public class CollectLinkViewModel extends CommonViewModel {

    // 收藏地址列表数据
    private MutableLiveData<List<CollectLinkBean.DataBean>> collectLinkLiveData = new MutableLiveData<>();

    private MutableLiveData<Integer> positionLiveData = new MutableLiveData<>();

    public CollectLinkViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取收藏地址列表数据
     *
     * @param isFirst 是否第一次加载
     */
    public void loadCollectLinkList(boolean isFirst) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getLinkList()
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BasePageObserver<CollectLinkBean>(this, isFirst) {
                    @Override
                    public void onSuccess(CollectLinkBean result) {
                        List<CollectLinkBean.DataBean> dataList = result.getData();
                        if (dataList != null && dataList.size() > 0) {
                            collectLinkLiveData.setValue(dataList);
                        } else {
                            collectLinkLiveData.setValue(null);
                            showEmpty();
                            ToastUtils.showShort("暂无收藏,快去收藏吧");
                        }
                    }
                });
    }

    /**
     * 删除收藏的网址
     *
     * @param id 网址id
     */
    public void deleteLink(int id, int position) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .deleteLink(id)
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BaseProgressObserver<CollectArticleBean>(this) {
                    @Override
                    public void onSuccess(CollectArticleBean result) {
                        if (result.getErrorCode() == 0) {
                            positionLiveData.setValue(position);
                            ToastUtils.showShort("删除网址成功");
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }
                });
    }


    public MutableLiveData<List<CollectLinkBean.DataBean>> getCollectLinkLiveData() {
        return collectLinkLiveData;
    }

    public MutableLiveData<Integer> getPositionLiveData() {
        return positionLiveData;
    }

}
