package com.hjc.reader.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hjc.reader.R;
import com.hjc.reader.base.activity.BaseActivity;
import com.hjc.reader.http.helper.RxHelper;
import com.hjc.reader.http.helper.RxSchedulers;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.tv_time)
    TextView tvTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Observable.timer(3, TimeUnit.SECONDS)
                .compose(RxSchedulers.ioToMain())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                });

        //倒计时3S
        Observable.intervalRange(0, 3, 0, 1, TimeUnit.SECONDS)
                .compose(RxHelper.bind(this))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        tvTime.setText("倒计时" + (3 - aLong) + "s");
                    }
                });
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onSingleClick(View v) {

    }
}
