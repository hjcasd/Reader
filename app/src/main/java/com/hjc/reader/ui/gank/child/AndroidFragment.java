package com.hjc.reader.ui.gank.child;

import android.view.View;

import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseLazyFragment;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 干货定制页面
 */
public class AndroidFragment extends BaseLazyFragment {

    public static AndroidFragment newInstance() {
        AndroidFragment fragment = new AndroidFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_android;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onSingleClick(View v) {

    }
}
