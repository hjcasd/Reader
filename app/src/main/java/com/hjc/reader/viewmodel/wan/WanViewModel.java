package com.hjc.reader.viewmodel.wan;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.http.observer.BaseCommonObserver;
import com.hjc.baselib.http.observer.BaseProgressObserver;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.CollectArticleBean;
import com.hjc.reader.bean.response.WanBannerBean;
import com.hjc.reader.bean.response.WanListBean;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.observer.BasePageObserver;

import java.util.List;

public class WanViewModel extends CommonViewModel {

    // 文章列表数据
    private MutableLiveData<List<WanListBean.DataBean.DatasBean>> listLiveData = new MutableLiveData<>();

    private MutableLiveData<WanBannerBean> bannerLiveData = new MutableLiveData<>();

    private MutableLiveData<WanListBean.DataBean.DatasBean> articleLiveData = new MutableLiveData<>();

    public WanViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取Banner数据
     */
    public void getBannerData() {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getWanBannerList()
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BaseCommonObserver<WanBannerBean>() {
                    @Override
                    public void onSuccess(WanBannerBean result) {
                        if (result.getErrorCode() == 0) {
                            bannerLiveData.setValue(result);
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 获取文章列表数据
     *
     * @param page    页码
     * @param isFirst 是否第一次加载
     */
    public void loadArticleList(int page, boolean isFirst) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .getWanList(page, null)
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BasePageObserver<WanListBean>(this, isFirst) {
                    @Override
                    public void onSuccess(WanListBean result) {
                        if (result.getErrorCode() == 0) {
                            WanListBean.DataBean dataBean = result.getData();
                            if (dataBean != null) {
                                List<WanListBean.DataBean.DatasBean> dataList = dataBean.getDatas();
                                if (dataList != null && dataList.size() > 0) {
                                    listLiveData.setValue(dataList);
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

    public MutableLiveData<List<WanListBean.DataBean.DatasBean>> getListLiveData() {
        return listLiveData;
    }

    public MutableLiveData<WanBannerBean> getBannerLiveData() {
        return bannerLiveData;
    }

    public MutableLiveData<WanListBean.DataBean.DatasBean> getArticleLiveData() {
        return articleLiveData;
    }

}
