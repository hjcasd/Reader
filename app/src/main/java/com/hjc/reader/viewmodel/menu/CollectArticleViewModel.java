package com.hjc.reader.viewmodel.menu;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.http.observer.BaseProgressObserver;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.CollectArticleBean;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.observer.BasePageObserver;

import java.util.List;

public class CollectArticleViewModel extends CommonViewModel {

    // 收藏文章列表数据
    private MutableLiveData<List<CollectArticleBean.DataBean.DatasBean>> collectArticleLiveData = new MutableLiveData<>();

    private MutableLiveData<Integer> positionLiveData = new MutableLiveData<>();

    public CollectArticleViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取收藏文章列表数据
     *
     * @param page    页码
     * @param isFirst 是否第一次加载
     */
    public void loadCollectArticleList(int page, boolean isFirst) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getArticleList(page)
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BasePageObserver<CollectArticleBean>(this, isFirst) {
                    @Override
                    public void onSuccess(CollectArticleBean result) {
                        CollectArticleBean.DataBean dataBean = result.getData();
                        List<CollectArticleBean.DataBean.DatasBean> dataList = dataBean.getDatas();
                        if (dataList != null && dataList.size() > 0) {
                            collectArticleLiveData.setValue(dataList);
                        } else {
                            collectArticleLiveData.setValue(null);
                            if (page == 0) {
                                showEmpty();
                                ToastUtils.showShort("暂无收藏,快去收藏吧");
                            } else {
                                ToastUtils.showShort("没有更多收藏了");
                            }
                        }
                    }
                });
    }

    /**
     * @param id       文章id
     * @param originId 文章originId
     * @param position 位置
     */
    public void unCollectArticle(int id, int originId, int position) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .unCollectOrigin(id, originId)
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BaseProgressObserver<CollectArticleBean>(this) {
                    @Override
                    public void onSuccess(CollectArticleBean result) {
                        if (result.getErrorCode() == 0) {
                            positionLiveData.setValue(position);
                            ToastUtils.showShort("已取消收藏");
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }
                });
    }


    public MutableLiveData<List<CollectArticleBean.DataBean.DatasBean>> getCollectArticleLiveData() {
        return collectArticleLiveData;
    }

    public MutableLiveData<Integer> getPositionLiveData() {
        return positionLiveData;
    }

}
