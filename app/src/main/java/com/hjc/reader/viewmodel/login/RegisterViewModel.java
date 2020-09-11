package com.hjc.reader.viewmodel.login;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.http.observer.BaseProgressObserver;
import com.hjc.baselib.model.CommonModel;
import com.hjc.baselib.viewmodel.BaseViewModel;
import com.hjc.reader.bean.response.LoginBean;
import com.hjc.reader.http.RetrofitHelper;

public class RegisterViewModel extends BaseViewModel<CommonModel> {

    private MutableLiveData<Boolean> registerData = new MutableLiveData<>();
    private MutableLiveData<String> usernameData = new MutableLiveData<>();
    private MutableLiveData<String> passwordData = new MutableLiveData<>();
    private MutableLiveData<String> confirmData = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected CommonModel createModel() {
        return new CommonModel();
    }

    public void register() {
        String username = usernameData.getValue();
        String password = passwordData.getValue();
        String confirmPassword = confirmData.getValue();

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

        if (!password.equals(confirmPassword)) {
            ToastUtils.showShort("两次密码输入不一致");
            return;
        }

        RetrofitHelper.getInstance().getWanAndroidService()
                .register(username, password, confirmPassword)
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BaseProgressObserver<LoginBean>(this) {
                    @Override
                    public void onSuccess(LoginBean result) {
                        if (result.getErrorCode() == 0) {
                            ToastUtils.showShort("注册成功");
                            registerData.setValue(true);
                        } else {
                            ToastUtils.showShort(result.getErrorMsg());
                        }
                    }
                });
    }

    // getter
    public MutableLiveData<Boolean> getRegisterData() {
        return registerData;
    }

    // getter
    public MutableLiveData<String> getUsernameData() {
        return usernameData;
    }

    // getter
    public MutableLiveData<String> getPasswordData() {
        return passwordData;
    }

    // getter
    public MutableLiveData<String> getConfirmData() {
        return confirmData;
    }

}
