package com.hjc.reader.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseActivity;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.response.LoginBean;
import com.hjc.reader.widget.TitleBar;

import butterknife.BindView;
import io.reactivex.observers.DefaultObserver;

/**
 * @Author: HJC
 * @Date: 2019/2/13 15:25
 * @Description: 注册页面
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_username)
    TextInputEditText etUsername;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.et_confirm_password)
    TextInputEditText etConfirmPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void addListeners() {
        btnRegister.setOnClickListener(this);

        titleBar.setOnViewClickListener(new TitleBar.onViewClick() {
            @Override
            public void leftClick(View view) {
                KeyboardUtils.hideSoftInput(RegisterActivity.this);
                finish();
            }

            @Override
            public void rightClick(View view) {

            }
        });
    }

    @Override
    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void register() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (StringUtils.isEmpty(username)) {
            ToastUtils.showShort("请输入用户名");
            return;
        }

        if (username.length() < 6) {
            ToastUtils.showShort("用户名长度至少为6位");
            return;
        }

        if (StringUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入密码");
            return;
        }

        if (password.length() < 6) {
            ToastUtils.showShort("密码长度至少为6位");
            return;
        }

        if (StringUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入确认密码");
            return;
        }

        if (password.length() < 6) {
            ToastUtils.showShort("确认密码长度至少为6位");
            return;
        }

        if (!password.equals(confirmPassword)) {
            ToastUtils.showShort("两次密码输入不一致");
            return;
        }

        RetrofitHelper.getInstance().getWanAndroidService()
                .register(username, password, confirmPassword)
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean != null) {
                            ToastUtils.showShort("注册成功");
                            KeyboardUtils.hideSoftInput(RegisterActivity.this);

                            Intent intent = new Intent();
                            intent.putExtra("username", username);
                            intent.putExtra("password", password);
                            setResult(1000, intent);
                            finish();
                        } else {
                            ToastUtils.showShort("注册失败,请稍后重试");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
