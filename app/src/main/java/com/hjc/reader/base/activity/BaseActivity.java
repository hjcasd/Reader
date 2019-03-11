package com.hjc.reader.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.hjc.reader.R;
import com.hjc.reader.utils.FastClickUtils;
import com.hjc.reader.utils.helper.ActivityManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author: HJC
 * @Date: 2019/1/4 14:47
 * @Description: Activity基类
 */
public abstract class BaseActivity extends RxAppCompatActivity implements View.OnClickListener {
    private Unbinder mBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());

        mBinder = ButterKnife.bind(this);

        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        initView();
        initData(savedInstanceState);
        addListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityManager.addActivity(this);
    }

    /**
     * 是否使用沉浸式
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        //使用该属性,必须指定状态栏颜色
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.app_color)
                .init();
    }

    /**
     * 获取布局的ID
     */
    public abstract int getLayoutId();

    /**
     * 初始化View
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData(Bundle savedInstanceState);

    /**
     * 设置监听器
     */
    public abstract void addListeners();

    /**
     * 设置点击事件
     */
    public abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        //避免快速点击
        if (FastClickUtils.isFastClick()) {
            ToastUtils.showShort("点的太快了,歇会呗!");
            return;
        }
        onSingleClick(v);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isImmersionBarEnabled()) {
            ImmersionBar.with(this).destroy();
        }
        if (mBinder != null) {
            mBinder.unbind();
        }
    }
}
