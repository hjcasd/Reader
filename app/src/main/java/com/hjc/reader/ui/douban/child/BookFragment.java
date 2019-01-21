package com.hjc.reader.ui.douban.child;

import android.view.View;

import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseLazyFragment;

/**
 * @Author: HJC
 * @Date: 2019/1/21 11:29
 * @Description: 干货定制页面
 */
public class BookFragment extends BaseLazyFragment {

    public static BookFragment newInstance() {
        BookFragment fragment = new BookFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book;
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
