package com.hjc.reader.ui.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hjc.reader.R;
import com.hjc.reader.base.fragment.BaseFragment;
import com.hjc.reader.adapter.MyViewPagerAdapter;
import com.hjc.reader.ui.gank.child.AndroidFragment;
import com.hjc.reader.ui.gank.child.GankFragment;
import com.hjc.reader.ui.gank.child.WelfareFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Author: HJC
 * @Date: 2019/1/21 16:10
 * @Description: 干货模块
 */
public class Tab2Fragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private String titles[] = new String[]{"福利", "干货定制", "大安卓"};

    public static Tab2Fragment newInstance() {
        Tab2Fragment fragment = new Tab2Fragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab2;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        List<Fragment> fragments = new ArrayList<>();

        WelfareFragment welfareFragment = WelfareFragment.newInstance();
        GankFragment gankFragment = GankFragment.newInstance();
        AndroidFragment androidFragment = AndroidFragment.newInstance();

        fragments.add(welfareFragment);
        fragments.add(gankFragment);
        fragments.add(androidFragment);

        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onSingleClick(View v) {

    }
}
