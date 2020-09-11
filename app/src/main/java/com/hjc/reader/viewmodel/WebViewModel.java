package com.hjc.reader.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.http.observer.BaseProgressObserver;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.bean.response.CollectArticleBean;
import com.hjc.reader.http.RetrofitHelper;

public class WebViewModel extends CommonViewModel {

    public WebViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 收藏网址链接
     *
     * @param title 标题
     * @param url   链接
     */
    public void collectLink(String title, String url) {
        RetrofitHelper.getInstance().getWanAndroidService()
                .collectLink(title, url)
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BaseProgressObserver<CollectArticleBean>(this) {
                    @Override
                    public void onSuccess(CollectArticleBean result) {
                        if (result.getErrorCode() == 0) {
                            ToastUtils.showShort("收藏网址成功");
                        } else {
                            ToastUtils.showShort("收藏网址失败");
                        }
                    }
                });
    }

}
