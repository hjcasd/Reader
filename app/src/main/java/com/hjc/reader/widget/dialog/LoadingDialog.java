package com.hjc.reader.widget.dialog;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.ybq.android.spinkit.SpinKitView;
import com.hjc.reader.R;
import com.hjc.reader.base.dialog.BaseDialog;
import com.hjc.reader.http.helper.RxSchedulers;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * @Author: HJC
 * @Date: 2019/1/7 11:28
 * @Description: 加载框
 */
public class LoadingDialog extends BaseDialog {
    @BindView(R.id.spin_kit_view)
    SpinKitView spinKitView;

    public static LoadingDialog newInstance() {
        LoadingDialog dialog = new LoadingDialog();
        return dialog;
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
    protected int getWidth() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
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

    public void dismissDialog() {
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .compose(RxSchedulers.ioToMain())
                .subscribe(aLong -> dismiss());
    }

    @Override
    public void onSingleClick(View v) {

    }
}
