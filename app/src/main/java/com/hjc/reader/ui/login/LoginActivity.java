package com.hjc.reader.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.KeyboardUtils;
import com.hjc.baselib.activity.BaseMvmActivity;
import com.hjc.baselib.event.EventManager;
import com.hjc.baselib.event.MessageEvent;
import com.hjc.reader.R;
import com.hjc.reader.constant.EventCode;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.ActivityLoginBinding;
import com.hjc.reader.utils.helper.RouteManager;
import com.hjc.reader.viewmodel.login.LoginViewModel;


/**
 * @Author: HJC
 * @Date: 2020/5/14 15:27
 * @Description: 登录页面
 */
@Route(path = RoutePath.URL_LOGIN)
public class LoginActivity extends BaseMvmActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginViewModel getViewModel() {
        return new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mBindingView.setLoginViewModel(mViewModel);
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getLoginData().observe(this, isLogin -> {
            if (isLogin) {
                KeyboardUtils.hideSoftInput(LoginActivity.this);
                EventManager.sendEvent(new MessageEvent(EventCode.LOGIN_CODE));
                finish();
            }
        });
    }

    @Override
    protected void addListeners() {
        mBindingView.setOnClickListener(this);

        mBindingView.titleBar.setOnViewLeftClickListener(view -> finish());
    }

    @Override
    protected void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                mViewModel.login();
                break;

            case R.id.tv_register:
                RouteManager.jumpWithCode(this, RoutePath.URL_REGISTER, null, 100);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == 1000 && data != null) {
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");

            mBindingView.etUsername.setText(username);
            mBindingView.etPassword.setText(password);
        }
    }
}
