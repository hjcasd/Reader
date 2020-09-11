package com.hjc.reader.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.KeyboardUtils;
import com.hjc.baselib.activity.BaseMvmActivity;
import com.hjc.reader.R;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.ActivityRegisterBinding;
import com.hjc.reader.utils.helper.RouteManager;
import com.hjc.reader.viewmodel.login.RegisterViewModel;


/**
 * @Author: HJC
 * @Date: 2020/5/14 15:27
 * @Description: 注册页面
 */
@Route(path = RoutePath.URL_REGISTER)
public class RegisterActivity extends BaseMvmActivity<ActivityRegisterBinding, RegisterViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterViewModel getViewModel() {
        return new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        mBindingView.setRegisterViewModel(mViewModel);
    }

    @Override
    protected void observeLiveData() {
        mViewModel.getRegisterData().observe(this, isRegister -> {
            if (isRegister){
                KeyboardUtils.hideSoftInput(RegisterActivity.this);

                Intent intent = new Intent();
                intent.putExtra("username", mBindingView.etUsername.getText());
                intent.putExtra("password", mBindingView.etPassword.getText());
                setResult(1000, intent);
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
            case R.id.btn_register:
                mViewModel.register();
                break;

            case R.id.tv_register:
                RouteManager.jump(RoutePath.URL_LOGIN);
                break;
        }
    }

}
