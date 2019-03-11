package com.hjc.reader.ui.collect.child;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseLazyFragment;
import com.hjc.reader.ui.collect.adapter.JokeAdapter;
import com.hjc.reader.ui.collect.adapter.URLAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author: HJC
 * @Date: 2019/3/11 11:00
 * @Description: 网址页面
 */
public class URLFragment extends BaseLazyFragment {

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_url)
    RecyclerView rvUrl;

    private URLAdapter mAdapter;

    public static URLFragment newInstance() {
        URLFragment fragment = new URLFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_url;
    }

    @Override
    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvUrl.setLayoutManager(manager);

        mAdapter = new URLAdapter(null);
        rvUrl.setAdapter(mAdapter);

        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
    }

    @Override
    public void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("URL---" + i);
        }
        mAdapter.setNewData(list);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onSingleClick(View v) {

    }
}
