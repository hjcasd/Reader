package com.hjc.reader.widget.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.hjc.reader.R;
import com.hjc.reader.base.dialog.BaseDialog;
import com.hjc.reader.http.helper.RxSchedulers;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:28
 * @Description: 加载框
 */
public class LoadingDialog extends BaseDialog {
    public static LoadingDialog newInstance() {
        return new LoadingDialog();
    }

    @Override
    public int getStyleRes() {
        return R.style.Dialog_Base;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //去掉遮盖层
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setDimAmount(0f);
        }
        setCancelable(false);
    }

    @Override
    public void addListeners() {

    }

    @SuppressLint("CheckResult")
    public void dismissDialog() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.ioToMain())
                .subscribe(aLong -> dismiss());
    }

    @Override
    public void onSingleClick(View v) {

    }
}
