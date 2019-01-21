package com.hjc.reader.ui.login.presenter;


import com.hjc.reader.base.mvp.BasePresenter;
import com.hjc.reader.http.helper.RxSchedulers;
import com.hjc.reader.ui.login.contract.LoginContract;
import com.hjc.reader.ui.login.model.LoginRequest;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;


public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(LoginRequest request) {
        getView().showLoading();

        Observable.timer(2, TimeUnit.SECONDS)
                .compose(RxSchedulers.ioToMain())
                .subscribe(aLong -> {
                    getView().hideLoading();
                    getView().toMainActivity();
                });
    }
}
