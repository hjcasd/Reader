package com.hjc.reader.ui.login.contract;


import com.hjc.reader.base.mvp.BaseView;
import com.hjc.reader.ui.login.model.LoginRequest;

public interface LoginContract {

    interface View extends BaseView {
        void toMainActivity();

        void showLoading();

        void hideLoading();
    }

    interface Presenter {
        void login(LoginRequest loginRequest);
    }
}
