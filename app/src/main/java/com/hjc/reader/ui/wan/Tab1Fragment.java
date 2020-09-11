package com.hjc.reader.ui.wan;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hjc.baselib.fragment.BaseMvmFragment;
import com.hjc.baselib.viewmodel.CommonViewModel;
import com.hjc.reader.R;
import com.hjc.reader.adapter.MyViewPagerAdapter;
import com.hjc.reader.databinding.FragmentTab1Binding;
import com.hjc.reader.ui.wan.child.NavigationFragment;
import com.hjc.reader.ui.wan.child.TreeFragment;
import com.hjc.reader.ui.wan.child.WanFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HJC
 * @Date: 2019/1/21 16:03
 * @Description: 玩安卓模块
 */
public class Tab1Fragment extends BaseMvmFragment<FragmentTab1Binding, CommonViewModel> {
    private String[] titles = new String[]{"玩安卓", "知识体系", "导航数据"};

    public static Tab1Fragment newInstance() {
        return new Tab1Fragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab1;
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
    public void initData(@Nullable Bundle savedInstanceState) {
        List<Fragment> fragments = new ArrayList<>();

        WanFragment wanFragment = WanFragment.newInstance();
        TreeFragment treeFragment = TreeFragment.newInstance();
        NavigationFragment navigationFragment = NavigationFragment.newInstance();

        fragments.add(wanFragment);
        fragments.add(treeFragment);
        fragments.add(navigationFragment);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        mBindingView.viewPager.setAdapter(adapter);
        mBindingView.viewPager.setOffscreenPageLimit(3);
        mBindingView.tabLayout.setupWithViewPager(mBindingView.viewPager);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onSingleClick(View v) {

    }
}
