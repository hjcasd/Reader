package com.hjc.reader.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseActivity;
import com.hjc.reader.base.event.Event;
import com.hjc.reader.base.event.EventManager;
import com.hjc.reader.constant.EventCode;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.model.response.LoginBean;
import com.hjc.reader.utils.helper.AccountManager;
import com.hjc.reader.widget.TitleBar;

import butterknife.BindView;
import io.reactivex.observers.DefaultObserver;

/**
 * @Author: HJC
 * @Date: 2019/2/13 15:24
 * @Description: 登录页面
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_username)
    TextInputEditText etUsername;
    @BindView(R.id.et_password)
    TextInputEditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void addListeners() {
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

        titleBar.setOnViewClickListener(new TitleBar.onViewClick() {
            @Override
            public void leftClick(View view) {
                KeyboardUtils.hideSoftInput(LoginActivity.this);
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
            case R.id.btn_login:
                login();
                break;

            case R.id.tv_register:
                startActivityForResult(new Intent(this, RegisterActivity.class), 100);
                break;
        }
    }

    private void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

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

        RetrofitHelper.getInstance().getWanAndroidService()
                .login(username, password)
                .compose(RxHelper.bind(this))
                .subscribe(new DefaultObserver<LoginBean>() {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean != null) {
                            ToastUtils.showShort("登录成功");
                            KeyboardUtils.hideSoftInput(LoginActivity.this);

                            AccountManager.getInstance().init(true, loginBean.getData().getUsername());

                            EventManager.sendEvent(new Event(EventCode.C));
                            finish();
                        } else {
                            ToastUtils.showShort("登录失败,请稍后重试");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");
            etUsername.setText(username);
            etPassword.setText(password);
        }
    }
}
