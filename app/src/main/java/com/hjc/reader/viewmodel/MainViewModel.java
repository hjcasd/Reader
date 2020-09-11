package com.hjc.reader.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.ToastUtils;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.http.observer.BaseProgressObserver;
import com.hjc.baselib.viewmodel.BaseViewModel;
import com.hjc.reader.bean.response.LoginBean;
import com.hjc.reader.http.RetrofitHelper;
import com.hjc.reader.model.MainModel;
import com.hjc.reader.utils.helper.AccountManager;

public class MainViewModel extends BaseViewModel<MainModel> {

    private MutableLiveData<String> usernameData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected MainModel createModel() {
        return new MainModel();
    }

    /**
     * 退出登录
     */
    public void logout() {
        RetrofitHelper.getInstance().getWanAndroidService()
                .logout()
                .compose(RxSchedulers.ioToMain())
                .subscribe(new BaseProgressObserver<LoginBean>(this) {
                    @Override
                    public void onSuccess(LoginBean result) {
                        ToastUtils.showShort("退出账号成功");
                        usernameData.setValue("登录/注册");

                        AccountManager.getInstance().clear();
                    }
                });
    }

    /**
     * 判断是否已经登录,并显示用户名
     */
    public void setUserName() {
        boolean isLogin = AccountManager.getInstance().isLogin();
        if (isLogin) {
            usernameData.setValue(AccountManager.getInstance().getUsername());
        } else {
            usernameData.setValue("登录/注册");
        }
    }

    public MutableLiveData<String> getUsernameData() {
        return usernameData;
    }
}
