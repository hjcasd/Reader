package com.hjc.baselib.http.observer;

import com.hjc.baselib.http.exception.ExceptionUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:52
 * @Description: 数据返回统一处理Observer基类
 */
public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onNext(T response) {
        onSuccess(response);
    }

    @Override
    public void onError(Throwable e) {
        onFailure(ExceptionUtils.handleException(e));
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    public abstract void onSuccess(T result);

    public abstract void onFailure(String errorMsg);

}
