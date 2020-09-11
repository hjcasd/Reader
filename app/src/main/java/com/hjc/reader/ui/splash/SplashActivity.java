package com.hjc.reader.ui.splash;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gyf.immersionbar.ImmersionBar;
import com.hjc.baselib.activity.BaseMvmActivity;
import com.hjc.baselib.http.RxSchedulers;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.R;
import com.hjc.reader.constant.RoutePath;
import com.hjc.reader.databinding.ActivitySplashBinding;
import com.hjc.reader.utils.helper.RouteManager;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @Author: HJC
 * @Date: 2020/8/24 14:40
 * @Description: 启动页面
 */
@Route(path = RoutePath.URL_SPLASH)
public class SplashActivity extends BaseMvmActivity<ActivitySplashBinding, CommonViewModel> {

    private Disposable disposable1;
    private Disposable disposable2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .fullScreen(true)
                .init();
    }

    @Override
    protected CommonViewModel getViewModel() {
        return null;
    }

    @Override
    protected int getBindingVariable() {
        return 0;
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        // 3秒后跳转到主界面
        disposable1 = Observable.timer(3, TimeUnit.SECONDS)
                .compose(RxSchedulers.ioToMain())
                .subscribe(aLong -> {
                    RouteManager.jump(RoutePath.URL_MAIN);
                    finish();
                });

        //倒计时3s
        disposable2 = Observable.intervalRange(0, 3, 0, 1, TimeUnit.SECONDS)
                .compose(RxSchedulers.ioToMain())
                .subscribe(aLong -> {
                    String time = "倒计时" + (3 - aLong) + "s";
                    mBindingView.tvTime.setText(time);
                });
    }

    @Override
    protected void addListeners() {
        mBindingView.setOnClickListener(this);
    }

    @Override
    protected void onSingleClick(View v) {
        if (v.getId() == R.id.tv_time) {
            if (disposable1 != null && disposable2 != null) {
                disposable1.dispose();
                disposable2.dispose();
                RouteManager.jump(RoutePath.URL_MAIN);
                finish();
            }
        }
    }
}
