package com.hjc.reader.http.observer;

import android.support.v4.app.FragmentManager;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.reader.widget.dialog.LoadingDialog;

import io.reactivex.disposables.Disposable;

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:52
 * @Description: 带进度的Observer
 */
public abstract class BaseProgressObserver<T> extends BaseObserver<T> {
    private LoadingDialog loadingDialog;

    private FragmentManager fragmentManager;

    private boolean isShow = true;

    public BaseProgressObserver(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        loadingDialog = LoadingDialog.newInstance();
    }

    public BaseProgressObserver(FragmentManager fragmentManager, boolean isShow) {
        this.fragmentManager = fragmentManager;
        this.isShow = isShow;
        loadingDialog = LoadingDialog.newInstance();
    }

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
        if (isShow) {
            showLoading();
        }
    }


    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (isShow) {
            hideLoading();
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (isShow) {
            hideLoading();
        }
    }

    @Override
    public void onFailure(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    private void showLoading() {
        if (loadingDialog != null) {
            loadingDialog.showDialog(fragmentManager);
        }
    }

    private void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
