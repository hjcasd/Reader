package com.hjc.reader.viewmodel.search;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.http.observer.BaseProgressObserver;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.CollectArticleBean;
import com.hjc.reader.bean.response.WanListBean;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.observer.BasePageObserver;

import java.util.List;

public class SearchResultViewModel extends CommonViewModel {

    // 收藏文章列表数据
    private MutableLiveData<List<WanListBean.DataBean.DatasBean>> searchLiveData = new MutableLiveData<>();

    private MutableLiveData<WanListBean.DataBean.DatasBean> articleLiveData = new MutableLiveData<>();

    public SearchResultViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 搜索
     *
     * @param isFirst 是否第一次加载
     */
    public void loadSearchData(int page, String keyword, boolean isFirst) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .search(page, keyword)
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BasePageObserver<WanListBean>(this, isFirst) {
                    @Override
                    public void onSuccess(WanListBean result) {
                        if (result.getErrorCode() == 0) {
                            WanListBean.DataBean dataBean = result.getData();
                            List<WanListBean.DataBean.DatasBean> dataList = dataBean.getDatas();
                            if (dataList != null && dataList.size() > 0) {
                                searchLiveData.setValue(dataList);
                            } else {
                                searchLiveData.setValue(null);
                                if (page == 0) {
                                    showEmpty();
                                    ToastUtils.showShort("暂无搜索数据");
                                } else {
                                    ToastUtils.showShort("没有更多数据了");
                                }
                            }
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 收藏文章
     *
     * @param bean 文章bean
     */
    public void collectArticle(WanListBean.DataBean.DatasBean bean) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .collectArticle(bean.getId())
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BaseProgressObserver<CollectArticleBean>(this) {
                    @Override
                    public void onSuccess(CollectArticleBean result) {
                        if (result.getErrorCode() == 0) {
                            bean.setCollect(true);
                            articleLiveData.setValue(bean);
                            ToastUtils.showShort("收藏成功");
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 取消收藏文章
     *
     * @param bean 文章bean
     */
    public void unCollectArticle(WanListBean.DataBean.DatasBean bean) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .unCollect(bean.getId())
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BaseProgressObserver<CollectArticleBean>(this) {
                    @Override
                    public void onSuccess(CollectArticleBean result) {
                        if (result.getErrorCode() == 0) {
                            bean.setCollect(false);
                            articleLiveData.setValue(bean);
                            ToastUtils.showShort("取消收藏成功");
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }
                });
    }

    public MutableLiveData<List<WanListBean.DataBean.DatasBean>> getSearchLiveData() {
        return searchLiveData;
    }

    public MutableLiveData<WanListBean.DataBean.DatasBean> getArticleLiveData() {
        return articleLiveData;
    }

}
