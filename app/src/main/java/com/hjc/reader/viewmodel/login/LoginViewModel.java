package com.hjc.reader.viewmodel.login;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.http.observer.BaseProgressObserver;
import com.hjc.baselib.viewmodel.BaseViewModel;
import com.hjc.reader.bean.response.LoginBean;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.model.LoginModel;
import com.hjc.reader.utils.helper.AccountManager;

public class LoginViewModel extends BaseViewModel<LoginModel> {

    private MutableLiveData<Boolean> loginData = new MutableLiveData<>();
    private MutableLiveData<String> usernameData = new MutableLiveData<>();
    private MutableLiveData<String> passwordData = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected LoginModel createModel() {
        return new LoginModel();
    }

    public void login() {
        String username = usernameData.getValue();
        String password = passwordData.getValue();

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
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BaseProgressObserver<LoginBean>(this) {
                    @Override
                    public void onSuccess(LoginBean result) {
                        if (result.getErrorCode() == 0) {
                            ToastUtils.showShort("登录成功");
                            AccountManager.getInstance().init(true, result.getData().getUsername());
                            loginData.setValue(true);
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }
                });
    }

    // getter
    public MutableLiveData<Boolean> getLoginData() {
        return loginData;
    }

    // getter
    public MutableLiveData<String> getUsernameData() {
        return usernameData;
    }

    // getter
    public MutableLiveData<String> getPasswordData() {
        return passwordData;
    }

}
